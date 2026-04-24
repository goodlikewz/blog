package com.learning.blog.common.handler;

import com.learning.blog.common.ApiResponse;
import com.learning.blog.common.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器。
 *
 * 统一把后端异常转换成前端可识别的接口响应结构。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常。
     *
     * @param exception 业务异常
     * @return 统一失败响应
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException exception) {
        return ApiResponse.error(exception.getCode(), exception.getMessage());
    }

    /**
     * 处理请求体参数校验异常。
     *
     * @param exception 参数校验异常
     * @return 统一失败响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // 收集所有字段校验错误，合并成一条中文提示返回给前端。
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return ApiResponse.error(400, message);
    }

    /**
     * 处理普通参数校验异常。
     *
     * @param exception 参数校验异常
     * @return 统一失败响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException exception) {
        return ApiResponse.error(400, exception.getMessage());
    }

    /**
     * 处理数据库访问异常。
     *
     * @param exception 数据库访问异常
     * @return 统一失败响应
     */
    @ExceptionHandler(DataAccessException.class)
    public ApiResponse<Void> handleDataAccessException(DataAccessException exception) {
        return ApiResponse.error(500, "数据库连接或访问异常，请检查 application.yml 中的数据源配置");
    }

    /**
     * 兜底处理其他未显式捕获的异常。
     *
     * @param exception 未知异常
     * @return 统一失败响应
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception exception) {
        // 部分底层异常可能没有 message，这里给出可读的兜底提示。
        String message = exception.getMessage();
        return ApiResponse.error(500, "系统异常：" + (message == null ? "请查看服务日志" : message));
    }
}
