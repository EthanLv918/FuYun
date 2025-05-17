package com.fuYunSoft.exception;

/**
 * 资源不存在异常类
 */
public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(404, message);
    }
}
