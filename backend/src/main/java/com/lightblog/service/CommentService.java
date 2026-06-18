package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.Article;
import com.lightblog.entity.Comment;
import com.lightblog.entity.User;
import com.lightblog.mapper.CommentMapper;
import com.lightblog.model.request.CommentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final ArticleService articleService;

    public CommentService(CommentMapper commentMapper, ArticleService articleService) {
        this.commentMapper = commentMapper;
        this.articleService = articleService;
    }

    public List<Comment> listByArticle(Long articleId) {
        articleService.detail(articleId);
        return commentMapper.listByArticle(articleId);
    }

    public Comment create(CommentRequest request) {
        Article article = articleService.detail(request.articleId);
        if (article.commentEnable == null || article.commentEnable == 0) {
            throw new BusinessException(400, "该文章已关闭评论");
        }
        Comment comment = new Comment();
        comment.articleId = request.articleId;
        comment.userId = AuthContext.userId();
        comment.content = request.content.trim();
        commentMapper.insert(comment);
        return commentMapper.selectById(comment.id);
    }

    public void delete(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException(404, "评论不存在", 404);
        }
        Article article = articleService.getExisting(comment.articleId);
        User user = AuthContext.get();
        boolean canDelete = comment.userId.equals(user.id) || article.userId.equals(user.id) || "admin".equals(user.role);
        if (!canDelete) {
            throw new BusinessException(403, "无权删除该评论", 403);
        }
        commentMapper.deleteById(id);
    }
}
