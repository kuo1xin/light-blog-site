package com.lightblog.model.request;

import javax.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank(message = "分类名称不能为空")
    public String name;
}
