CREATE DATABASE IF NOT EXISTS light_blog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE light_blog;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS online_log;
DROP TABLE IF EXISTS follow;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS blog_space;
DROP TABLE IF EXISTS `user`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `user` (
  user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(32) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  nickname VARCHAR(64) NOT NULL,
  email VARCHAR(128),
  avatar VARCHAR(255),
  intro TEXT,
  role VARCHAR(16) NOT NULL DEFAULT 'user',
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_user_role (role),
  INDEX idx_user_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE blog_space (
  user_id BIGINT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  intro TEXT,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_space_user FOREIGN KEY (user_id) REFERENCES `user` (user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE category (
  category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  category_name VARCHAR(64) NOT NULL,
  user_id BIGINT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_category_user (user_id),
  CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES `user` (user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE article (
  article_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(160) NOT NULL,
  content TEXT NOT NULL,
  category_id BIGINT,
  tags VARCHAR(255),
  user_id BIGINT NOT NULL,
  is_public TINYINT NOT NULL DEFAULT 1,
  comment_enable TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_article_user (user_id),
  INDEX idx_article_category (category_id),
  INDEX idx_article_public_time (is_public, create_time),
  FULLTEXT INDEX ft_article_title_tags (title, tags),
  CONSTRAINT fk_article_user FOREIGN KEY (user_id) REFERENCES `user` (user_id) ON DELETE CASCADE,
  CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `comment` (
  comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  article_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_comment_article (article_id),
  INDEX idx_comment_user (user_id),
  CONSTRAINT fk_comment_article FOREIGN KEY (article_id) REFERENCES article (article_id) ON DELETE CASCADE,
  CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES `user` (user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE follow (
  follow_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  from_uid BIGINT NOT NULL,
  to_uid BIGINT,
  article_id BIGINT,
  type VARCHAR(16) NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_follow_user (from_uid, type, to_uid),
  UNIQUE KEY uk_follow_article (from_uid, type, article_id),
  INDEX idx_follow_from (from_uid),
  CONSTRAINT fk_follow_from FOREIGN KEY (from_uid) REFERENCES `user` (user_id) ON DELETE CASCADE,
  CONSTRAINT fk_follow_to FOREIGN KEY (to_uid) REFERENCES `user` (user_id) ON DELETE CASCADE,
  CONSTRAINT fk_follow_article FOREIGN KEY (article_id) REFERENCES article (article_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE online_log (
  log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  login_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_active DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status TINYINT NOT NULL DEFAULT 1,
  INDEX idx_online_status (status, last_active),
  INDEX idx_online_user (user_id),
  CONSTRAINT fk_online_user FOREIGN KEY (user_id) REFERENCES `user` (user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (username, password, nickname, email, avatar, intro, role, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', 'admin@example.com', '', '负责平台内容审核与用户管理。', 'admin', 1),
('member', 'e10adc3949ba59abbe56e057f20f883e', '示例会员', 'member@example.com', '', '记录学习、生活与 Web 开发实践。', 'user', 1);

INSERT INTO blog_space (user_id, name, intro) VALUES
(2, '示例会员的轻博客', '一个用于课程验收演示的个人博客空间。');

INSERT INTO category (category_name, user_id) VALUES
('学习笔记', 2),
('生活随笔', 2);

INSERT INTO article (title, content, category_id, tags, user_id, is_public, comment_enable) VALUES
('Vue3 组件化开发记录', '使用 Vue3 编写轻博客前端时，可以按页面、组件、接口三个层次拆分代码。路由负责页面切换，Pinia 保存登录状态，Axios 统一处理请求。', 1, 'Vue3,Vite,前端', 2, 1, 1),
('Spring Boot 接口开发小结', '后端按 Controller、Service、Mapper 分层实现。Controller 处理请求参数，Service 编写业务规则，Mapper 负责 SQL。权限校验需要在接口和业务层同时处理。', 1, 'Spring Boot,MyBatis,后端', 2, 1, 1),
('不公开草稿示例', '这篇文章只允许作者和管理员查看，用于验证私密文章权限。', 2, '草稿', 2, 0, 0);

INSERT INTO `comment` (article_id, user_id, content) VALUES
(1, 1, '示例评论：文章结构清晰，适合验收演示。');
