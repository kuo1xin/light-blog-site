package com.lightblog.service;

import com.lightblog.common.BusinessException;
import com.lightblog.common.PageResult;
import com.lightblog.context.AuthContext;
import com.lightblog.entity.Article;
import com.lightblog.entity.User;
import com.lightblog.mapper.ArticleMapper;
import com.lightblog.model.request.ArticleRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public PageResult<Article> listPublic(int page, int size, String keyword, Long categoryId) {
        int safePage = page <= 0 ? 1 : page;
        int safeSize = size <= 0 || size > 50 ? 10 : size;
        int offset = (safePage - 1) * safeSize;
        List<Article> records = articleMapper.listPublic(keyword, categoryId, offset, safeSize);
        long total = articleMapper.countPublic(keyword, categoryId);
        return new PageResult<Article>(records, total, safePage, safeSize);
    }

    public Article detail(Long id) {
        Article article = getExisting(id);
        User user = AuthContext.get();
        if (article.publicFlag != null && article.publicFlag == 0) {
            boolean owner = user != null && article.userId.equals(user.id);
            boolean admin = user != null && "admin".equals(user.role);
            if (!owner && !admin) {
                throw new BusinessException(403, "无权查看该文章", 403);
            }
        }
        return article;
    }

    public List<Article> myArticles() {
        User user = AuthContext.get();
        return articleMapper.listByUser(user.id);
    }

    public List<Article> spaceArticles(Long userId) {
        List<Article> articles = articleMapper.listByUser(userId);
        User user = AuthContext.get();
        boolean canSeePrivate = user != null && (userId.equals(user.id) || "admin".equals(user.role));
        if (canSeePrivate) {
            return articles;
        }
        List<Article> publicArticles = new ArrayList<Article>();
        for (Article article : articles) {
            if (article.publicFlag != null && article.publicFlag == 1) {
                publicArticles.add(article);
            }
        }
        return publicArticles;
    }

    public Article create(ArticleRequest request) {
        User user = AuthContext.get();
        Article article = new Article();
        applyRequest(article, request);
        article.userId = user.id;
        articleMapper.insert(article);
        return articleMapper.selectById(article.id);
    }

    public Article update(Long id, ArticleRequest request) {
        Article article = getExisting(id);
        assertOwnerOrAdmin(article);
        applyRequest(article, request);
        articleMapper.update(article);
        return articleMapper.selectById(id);
    }

    public void delete(Long id) {
        Article article = getExisting(id);
        assertOwnerOrAdmin(article);
        articleMapper.deleteById(id);
    }

    public Article getExisting(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在", 404);
        }
        return article;
    }

    private void assertOwnerOrAdmin(Article article) {
        User user = AuthContext.get();
        if (user == null || (!article.userId.equals(user.id) && !"admin".equals(user.role))) {
            throw new BusinessException(403, "无权操作该文章", 403);
        }
    }

    private void applyRequest(Article article, ArticleRequest request) {
        article.title = request.title.trim();
        article.content = request.content.trim();
        article.categoryId = request.categoryId;
        article.tags = request.tags;
        article.publicFlag = request.publicFlag == null ? 1 : normalizeFlag(request.publicFlag);
        article.commentEnable = request.commentEnable == null ? 1 : normalizeFlag(request.commentEnable);
    }

    private int normalizeFlag(Integer value) {
        return value != null && value == 0 ? 0 : 1;
    }
}
