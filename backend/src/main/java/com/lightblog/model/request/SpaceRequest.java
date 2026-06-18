package com.lightblog.model.request;

import javax.validation.constraints.NotBlank;

public class SpaceRequest {
    @NotBlank(message = "空间名称不能为空")
    public String name;
    public String intro;
}
