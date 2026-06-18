package com.lightblog.entity;

import java.time.LocalDateTime;

public class Article {
    public Long id;
    public String title;
    public String content;
    public Long categoryId;
    public String categoryName;
    public String tags;
    public Long userId;
    public String authorName;
    public Integer publicFlag;
    public Integer commentEnable;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
}
