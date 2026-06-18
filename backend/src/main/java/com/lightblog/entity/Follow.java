package com.lightblog.entity;

import java.time.LocalDateTime;

public class Follow {
    public Long id;
    public Long fromUid;
    public Long toUid;
    public Long articleId;
    public String type;
    public String targetName;
    public LocalDateTime createTime;
}
