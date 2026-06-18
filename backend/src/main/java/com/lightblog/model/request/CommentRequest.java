package com.lightblog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequest {
    @NotNull(message = "文章 ID 不能为空")
    public Long articleId;

    @NotBlank(message = "评论内容不能为空")
    public String content;
}
