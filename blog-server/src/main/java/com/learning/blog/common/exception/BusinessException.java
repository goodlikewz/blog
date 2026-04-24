package com.learning.blog.common.exception;

/**
 * 业务异常。
 *
 * 用于承载可预期的业务错误，例如登录失败、权限不足、参数校验失败等。
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        this(400, message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
