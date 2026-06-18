package com.lightblog.model.request;

import javax.validation.constraints.Email;

public class ProfileRequest {
    public String nickname;

    @Email(message = "邮箱格式不正确")
    public String email;

    public String avatar;
    public String intro;
}
