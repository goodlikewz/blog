package com.learning.blog.common;

import lombok.Data;

/**
 * 统一接口返回结构。
 *
 * @param <T> 实际返回的数据类型
 */
@Data
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 构建成功响应。
     *
     * @param data 响应数据
     * @return 统一成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        // 创建统一响应对象。
        ApiResponse<T> response = new ApiResponse<>();
        // 设置成功状态码和默认提示信息。
        response.setCode(200);
        response.setMessage("操作成功");
        response.setData(data);
        return response;
    }

    /**
     * 构建带自定义提示语的成功响应。
     *
     * @param message 成功提示信息
     * @param data 响应数据
     * @return 统一成功响应
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        // 创建统一响应对象。
        ApiResponse<T> response = new ApiResponse<>();
        // 设置成功状态码和业务提示信息。
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 构建失败响应。
     *
     * @param code 错误状态码
     * @param message 错误提示信息
     * @return 统一失败响应
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        // 创建统一响应对象。
        ApiResponse<T> response = new ApiResponse<>();
        // 设置错误状态码和错误提示信息。
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
