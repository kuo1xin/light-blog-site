package com.lightblog.controller;

import com.lightblog.common.ApiResponse;
import com.lightblog.entity.Comment;
import com.lightblog.model.request.CommentRequest;
import com.lightblog.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/list/{articleId}")
    public ApiResponse<List<Comment>> list(@PathVariable Long articleId) {
        return ApiResponse.ok(commentService.listByArticle(articleId));
    }

    @PostMapping
    public ApiResponse<Comment> create(@Valid @RequestBody CommentRequest request) {
        return ApiResponse.ok(commentService.create(request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ApiResponse.ok();
    }
}
