package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.BlogSpace;
import com.lightblog.entity.User;
import com.lightblog.mapper.BlogSpaceMapper;
import com.lightblog.mapper.UserMapper;
import com.lightblog.model.request.SpaceRequest;
import org.springframework.stereotype.Service;

@Service
public class SpaceService {
    private final BlogSpaceMapper blogSpaceMapper;
    private final UserMapper userMapper;

    public SpaceService(BlogSpaceMapper blogSpaceMapper, UserMapper userMapper) {
        this.blogSpaceMapper = blogSpaceMapper;
        this.userMapper = userMapper;
    }

    public BlogSpace get(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在", 404);
        }
        BlogSpace space = blogSpaceMapper.selectByUserId(userId);
        if (space != null) {
            return space;
        }
        BlogSpace fallback = new BlogSpace();
        fallback.userId = userId;
        fallback.name = user.nickname + "的博客空间";
        fallback.intro = user.intro;
        return fallback;
    }

    public BlogSpace save(SpaceRequest request) {
        Long userId = AuthContext.userId();
        BlogSpace space = new BlogSpace();
        space.userId = userId;
        space.name = request.name.trim();
        space.intro = request.intro;
        if (blogSpaceMapper.selectByUserId(userId) == null) {
            blogSpaceMapper.insert(space);
        } else {
            blogSpaceMapper.update(space);
        }
        return blogSpaceMapper.selectByUserId(userId);
    }
}
