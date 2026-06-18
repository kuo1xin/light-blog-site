package com.lightblog.entity;

import java.time.LocalDateTime;

public class OnlineLog {
    public Long id;
    public Long userId;
    public String username;
    public LocalDateTime loginTime;
    public LocalDateTime lastActive;
    public Integer status;
}
