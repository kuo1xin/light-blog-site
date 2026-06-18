package com.lightblog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FollowRequest {
    @NotBlank(message = "关注类型不能为空")
    public String type;

    @NotNull(message = "关注对象不能为空")
    public Long targetId;
}
