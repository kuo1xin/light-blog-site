package com.lightblog.controller;

import com.lightblog.common.ApiResponse;
import com.lightblog.entity.Article;
import com.lightblog.entity.BlogSpace;
import com.lightblog.model.request.SpaceRequest;
import com.lightblog.service.ArticleService;
import com.lightblog.service.SpaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/space")
public class SpaceController {
    private final SpaceService spaceService;
    private final ArticleService articleService;

    public SpaceController(SpaceService spaceService, ArticleService articleService) {
        this.spaceService = spaceService;
        this.articleService = articleService;
    }

    @GetMapping("/{userId}")
    public ApiResponse<BlogSpace> get(@PathVariable Long userId) {
        return ApiResponse.ok(spaceService.get(userId));
    }

    @PutMapping
    public ApiResponse<BlogSpace> save(@Valid @RequestBody SpaceRequest request) {
        return ApiResponse.ok(spaceService.save(request));
    }

    @GetMapping("/{userId}/articles")
    public ApiResponse<List<Article>> articles(@PathVariable Long userId) {
        return ApiResponse.ok(articleService.spaceArticles(userId));
    }
}
