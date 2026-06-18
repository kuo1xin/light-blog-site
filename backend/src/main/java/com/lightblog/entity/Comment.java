package com.lightblog.entity;

import java.time.LocalDateTime;

public class Comment {
    public Long id;
    public Long articleId;
    public String articleTitle;
    public Long userId;
    public String nickname;
    public String content;
    public LocalDateTime createTime;
}
