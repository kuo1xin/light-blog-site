package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.Article;
import com.lightblog.entity.Comment;
import com.lightblog.entity.OnlineLog;
import com.lightblog.entity.User;
import com.lightblog.mapper.ArticleMapper;
import com.lightblog.mapper.CommentMapper;
import com.lightblog.mapper.OnlineLogMapper;
import com.lightblog.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final OnlineLogMapper onlineLogMapper;

    public AdminService(UserMapper userMapper, ArticleMapper articleMapper, CommentMapper commentMapper, OnlineLogMapper onlineLogMapper) {
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
        this.onlineLogMapper = onlineLogMapper;
    }

    public List<User> users() {
        requireAdmin();
        List<User> users = userMapper.listAll();
        for (User user : users) {
            user.password = null;
        }
        return users;
    }

    public User updateUser(Long id, User request) {
        requireAdmin();
        User dbUser = userMapper.selectById(id);
        if (dbUser == null) {
            throw new BusinessException(404, "用户不存在", 404);
        }
        dbUser.nickname = request.nickname;
        dbUser.email = request.email;
        dbUser.intro = request.intro;
        dbUser.role = request.role == null ? dbUser.role : request.role;
        dbUser.status = request.status == null ? dbUser.status : request.status;
        userMapper.updateAdmin(dbUser);
        dbUser.password = null;
        return dbUser;
    }

    public void deleteUser(Long id) {
        requireAdmin();
        if (AuthContext.userId().equals(id)) {
            throw new BusinessException(400, "不能删除当前管理员账号");
        }
        if (userMapper.deleteNonAdmin(id) == 0) {
            throw new BusinessException(404, "用户不存在或不能删除管理员", 404);
        }
    }

    public List<Article> articles(String title, String author) {
        requireAdmin();
        return articleMapper.adminList(title, author);
    }

    public void deleteArticle(Long id) {
        requireAdmin();
        articleMapper.deleteById(id);
    }

    public List<Comment> comments() {
        requireAdmin();
        return commentMapper.listAll();
    }

    public void deleteComment(Long id) {
        requireAdmin();
        commentMapper.deleteById(id);
    }

    public List<OnlineLog> onlineUsers() {
        requireAdmin();
        return onlineLogMapper.listOnline();
    }

    private void requireAdmin() {
        if (!AuthContext.isAdmin()) {
            throw new BusinessException(403, "需要管理员权限", 403);
        }
    }
}
