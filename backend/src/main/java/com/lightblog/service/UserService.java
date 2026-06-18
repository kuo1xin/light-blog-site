package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.User;
import com.lightblog.mapper.ArticleMapper;
import com.lightblog.mapper.CommentMapper;
import com.lightblog.mapper.FollowMapper;
import com.lightblog.mapper.UserMapper;
import com.lightblog.model.request.LoginRequest;
import com.lightblog.model.request.PasswordRequest;
import com.lightblog.model.request.ProfileRequest;
import com.lightblog.model.request.RegisterRequest;
import com.lightblog.util.Md5Util;
import com.lightblog.util.TextUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final FollowMapper followMapper;
    private final TokenService tokenService;

    public UserService(UserMapper userMapper, ArticleMapper articleMapper, CommentMapper commentMapper, FollowMapper followMapper, TokenService tokenService) {
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
        this.followMapper = followMapper;
        this.tokenService = tokenService;
    }

    public User register(RegisterRequest request) {
        if (userMapper.selectByUsername(request.username) != null) {
            throw new BusinessException(409, "用户名已存在", 409);
        }
        User user = new User();
        user.username = request.username.trim();
        user.password = Md5Util.md5(request.password);
        user.nickname = TextUtil.defaultIfBlank(request.nickname, request.username);
        user.email = request.email;
        user.avatar = "";
        user.intro = "";
        user.role = "user";
        user.status = 1;
        userMapper.insert(user);
        return publicUser(userMapper.selectById(user.id));
    }

    public Map<String, Object> login(LoginRequest request) {
        User user = userMapper.selectByUsername(request.username);
        if (user == null || !Md5Util.md5(request.password).equals(user.password)) {
            throw new BusinessException(401, "用户名或密码错误", 401);
        }
        if (user.status == null || user.status != 1) {
            throw new BusinessException(403, "账号已被禁用", 403);
        }
        String token = tokenService.createSession(user);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        result.put("user", publicUser(user));
        return result;
    }

    public User currentUser() {
        User current = AuthContext.get();
        if (current == null) {
            throw new BusinessException(401, "请先登录", 401);
        }
        return publicUser(userMapper.selectById(current.id));
    }

    public User updateProfile(ProfileRequest request) {
        User current = currentUser();
        current.nickname = TextUtil.defaultIfBlank(request.nickname, current.nickname);
        current.email = request.email;
        current.avatar = request.avatar;
        current.intro = request.intro;
        userMapper.updateProfile(current);
        return publicUser(userMapper.selectById(current.id));
    }

    public void changePassword(PasswordRequest request) {
        User current = AuthContext.get();
        User dbUser = userMapper.selectById(current.id);
        if (!Md5Util.md5(request.oldPassword).equals(dbUser.password)) {
            throw new BusinessException(400, "原密码不正确");
        }
        userMapper.updatePassword(current.id, Md5Util.md5(request.newPassword));
    }

    public Map<String, Long> stats() {
        User current = AuthContext.get();
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("articleCount", articleMapper.countByUser(current.id));
        result.put("commentCount", commentMapper.countByUser(current.id));
        result.put("followCount", followMapper.countByUser(current.id));
        return result;
    }

    public void logout(String authorization) {
        if (authorization == null) {
            return;
        }
        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : authorization;
        tokenService.logout(token);
    }

    public User publicUser(User user) {
        if (user != null) {
            user.password = null;
        }
        return user;
    }
}
