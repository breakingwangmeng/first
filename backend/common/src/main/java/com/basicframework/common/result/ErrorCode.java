package com.basicframework.common.result;

/**
 * 业务错误码。约定：0 成功；4xxxx 客户端错误（40010 类为具体业务）；5xxxx 服务端错误。
 * 错误码全局唯一，新增前先查重（见 ErrorCodeTest）。
 */
public enum ErrorCode {

    SUCCESS(0, "成功"),

    BAD_REQUEST(40000, "请求参数错误"),
    BAD_CREDENTIALS(40010, "用户名或密码错误"),
    USERNAME_EXISTS(40011, "用户名已存在"),

    UNAUTHORIZED(40100, "未登录或登录已过期"),
    FORBIDDEN(40300, "无权限访问"),
    NOT_FOUND(40400, "资源不存在"),

    INTERNAL_ERROR(50000, "系统内部错误");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
