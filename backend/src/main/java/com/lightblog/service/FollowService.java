package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.Article;
import com.lightblog.entity.Follow;
import com.lightblog.mapper.FollowMapper;
import com.lightblog.mapper.UserMapper;
import com.lightblog.model.request.FollowRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowService {
    private final FollowMapper followMapper;
    private final UserMapper userMapper;
    private final ArticleService articleService;

    public FollowService(FollowMapper followMapper, UserMapper userMapper, ArticleService articleService) {
        this.followMapper = followMapper;
        this.userMapper = userMapper;
        this.articleService = articleService;
    }

    public Map<String, Object> toggle(FollowRequest request) {
        String type = request.type == null ? "" : request.type.trim();
        if (!"user".equals(type) && !"article".equals(type)) {
            throw new BusinessException(400, "关注类型只支持 user 或 article");
        }

        Long fromUid = AuthContext.userId();
        Follow exists = followMapper.find(fromUid, type, request.targetId);
        Map<String, Object> result = new HashMap<String, Object>();
        if (exists != null) {
            followMapper.deleteById(exists.id);
            result.put("followed", false);
            return result;
        }

        Follow follow = new Follow();
        follow.fromUid = fromUid;
        follow.type = type;
        if ("user".equals(type)) {
            if (fromUid.equals(request.targetId)) {
                throw new BusinessException(400, "不能关注自己");
            }
            if (userMapper.selectById(request.targetId) == null) {
                throw new BusinessException(404, "用户不存在", 404);
            }
            follow.toUid = request.targetId;
        } else {
            Article article = articleService.detail(request.targetId);
            follow.articleId = article.id;
        }
        followMapper.insert(follow);
        result.put("followed", true);
        return result;
    }

    public List<Follow> listMine() {
        return followMapper.listByUser(AuthContext.userId());
    }
}
