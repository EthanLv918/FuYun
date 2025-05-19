package com.fuYunSoft.exception;

/**
 * 禁止访问异常类
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message) {
        super(403, message);
    }
}
