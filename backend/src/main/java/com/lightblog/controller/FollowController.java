package com.lightblog.controller;

import com.lightblog.common.ApiResponse;
import com.lightblog.entity.Follow;
import com.lightblog.model.request.FollowRequest;
import com.lightblog.service.FollowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> toggle(@Valid @RequestBody FollowRequest request) {
        return ApiResponse.ok(followService.toggle(request));
    }

    @GetMapping("/list")
    public ApiResponse<List<Follow>> list() {
        return ApiResponse.ok(followService.listMine());
    }
}
