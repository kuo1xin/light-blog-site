package com.lightblog.model.request;

import javax.validation.constraints.NotBlank;

public class ArticleRequest {
    @NotBlank(message = "文章标题不能为空")
    public String title;

    @NotBlank(message = "文章内容不能为空")
    public String content;

    public Long categoryId;
    public String tags;
    public Integer publicFlag;
    public Integer commentEnable;
}
