package com.lightblog.entity;

import java.time.LocalDateTime;

public class User {
    public Long id;
    public String username;
    public String password;
    public String nickname;
    public String email;
    public String avatar;
    public String intro;
    public String role;
    public Integer status;
    public LocalDateTime createTime;
}
