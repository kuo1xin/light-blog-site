package com.lightblog.controller;

import com.lightblog.common.ApiResponse;
import com.lightblog.common.PageResult;
import com.lightblog.entity.Article;
import com.lightblog.model.request.ArticleRequest;
import com.lightblog.service.ArticleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public ApiResponse<PageResult<Article>> list(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) Long categoryId) {
        return ApiResponse.ok(articleService.listPublic(page, size, keyword, categoryId));
    }

    @GetMapping("/{id}")
    public ApiResponse<Article> detail(@PathVariable Long id) {
        return ApiResponse.ok(articleService.detail(id));
    }

    @GetMapping("/mine")
    public ApiResponse<List<Article>> mine() {
        return ApiResponse.ok(articleService.myArticles());
    }

    @PostMapping
    public ApiResponse<Article> create(@Valid @RequestBody ArticleRequest request) {
        return ApiResponse.ok(articleService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Article> update(@PathVariable Long id, @Valid @RequestBody ArticleRequest request) {
        return ApiResponse.ok(articleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ApiResponse.ok();
    }
}
