package com.Persolute.GraduateManagementSystem.exception;

/**
 * @author Persolute
 * @version 1.0
 * @description 自定义异常类
 * @email 1538520381@qq.com
 * @date 2025/01/15 14:02
 */
public class CustomerException extends RuntimeException {
    public CustomerException(String message) {
        super(message);
    }
}
