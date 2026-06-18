package com.lightblog.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordRequest {
    @NotBlank(message = "原密码不能为空")
    public String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 32, message = "新密码长度应为 6-32 位")
    public String newPassword;
}
