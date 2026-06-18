package com.lightblog.common;

public class ApiResponse<T> {
    public int code;
    public String msg;
    public T data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(200, "操作成功", data);
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<Void>(200, "操作成功", null);
    }

    public static ApiResponse<Void> fail(int code, String msg) {
        return new ApiResponse<Void>(code, msg, null);
    }
}
