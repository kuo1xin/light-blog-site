package com.lightblog.controller;

import com.lightblog.common.ApiResponse;
import com.lightblog.entity.Article;
import com.lightblog.entity.Comment;
import com.lightblog.entity.OnlineLog;
import com.lightblog.entity.User;
import com.lightblog.service.AdminService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ApiResponse<List<User>> users() {
        return ApiResponse.ok(adminService.users());
    }

    @PutMapping("/user/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User request) {
        return ApiResponse.ok(adminService.updateUser(id, request));
    }

    @DeleteMapping("/user/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ApiResponse.ok();
    }

    @GetMapping("/articles")
    public ApiResponse<List<Article>> articles(@RequestParam(required = false) String title,
                                               @RequestParam(required = false) String author) {
        return ApiResponse.ok(adminService.articles(title, author));
    }

    @DeleteMapping("/article/{id}")
    public ApiResponse<Void> deleteArticle(@PathVariable Long id) {
        adminService.deleteArticle(id);
        return ApiResponse.ok();
    }

    @GetMapping("/comments")
    public ApiResponse<List<Comment>> comments() {
        return ApiResponse.ok(adminService.comments());
    }

    @DeleteMapping("/comment/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable Long id) {
        adminService.deleteComment(id);
        return ApiResponse.ok();
    }

    @GetMapping("/online")
    public ApiResponse<List<OnlineLog>> online() {
        return ApiResponse.ok(adminService.onlineUsers());
    }
}
