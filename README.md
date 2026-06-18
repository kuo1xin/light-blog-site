# 轻博客小站

基于需求文档实现的前后端分离轻博客系统，包含游客、会员、管理员三类角色。代码独立放在本目录，不依赖也不修改其他项目文件夹。

## 技术栈

- 前端：Vue3 + Vite + Bootstrap + Axios + Vue Router + Pinia
- 后端：Spring Boot 2.7 + MyBatis + MySQL 8
- 认证：Bearer Token 会话
- 密码：MD5 存储

## 目录

```text
backend/   Spring Boot 后端
frontend/  Vue3 前端
database/  MySQL 建表与演示数据
docs/      接口与运行说明
```

## 演示账号

导入 `database/schema.sql` 后可直接使用：

- 管理员：`admin` / `123456`
- 会员：`member` / `123456`

## 本地运行

1. 安装 JDK 1.8+、Maven、MySQL 8、Node.js 14+。
2. 在 MySQL 中执行 `database/schema.sql`。
3. 修改 `backend/src/main/resources/application.yml` 中的数据库账号密码。
4. 启动后端：

```bash
cd backend
mvn spring-boot:run
```

5. 启动前端：

```bash
cd frontend
npm install
npm run dev
```

如果 npm 官方源连接不稳定，可以使用：

```bash
npm install --registry=https://registry.npmmirror.com
```

6. 浏览器访问 `http://localhost:5173`。

## 已覆盖功能

- 游客：公开文章列表、文章详情、只读评论、注册、登录
- 会员：资料管理、密码修改、博客空间、文章发布/编辑/删除、分类、评论、关注
- 管理员：用户管理、文章管理、评论管理、在线用户查看
- 权限：前端路由守卫 + 后端接口拦截 + 业务层作者/管理员校验

## 当前本机限制

这台机器当前没有 Java Runtime、Maven 和 `code` 命令，因此后端编译和 VS Code 命令行打开需要安装对应工具后执行。
