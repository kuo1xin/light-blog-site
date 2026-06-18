package com.lightblog.controller;

import com.lightblog.common.ApiResponse;
import com.lightblog.entity.User;
import com.lightblog.model.request.LoginRequest;
import com.lightblog.model.request.PasswordRequest;
import com.lightblog.model.request.ProfileRequest;
import com.lightblog.model.request.RegisterRequest;
import com.lightblog.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(userService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        userService.logout(authorization);
        return ApiResponse.ok();
    }

    @GetMapping("/info")
    public ApiResponse<User> info() {
        return ApiResponse.ok(userService.currentUser());
    }

    @PutMapping("/profile")
    public ApiResponse<User> updateProfile(@Valid @RequestBody ProfileRequest request) {
        return ApiResponse.ok(userService.updateProfile(request));
    }

    @PutMapping("/password")
    public ApiResponse<Void> changePassword(@Valid @RequestBody PasswordRequest request) {
        userService.changePassword(request);
        return ApiResponse.ok();
    }

    @GetMapping("/stats")
    public ApiResponse<Map<String, Long>> stats() {
        return ApiResponse.ok(userService.stats());
    }
}
