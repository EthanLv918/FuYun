package com.fuYunSoft.exception;

/**
 * 未授权异常类
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(401, message);
    }
}
