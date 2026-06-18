package com.lightblog.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 24, message = "用户名长度应为 3-24 位")
    public String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度应为 6-32 位")
    public String password;

    @NotBlank(message = "昵称不能为空")
    public String nickname;

    @Email(message = "邮箱格式不正确")
    public String email;
}
