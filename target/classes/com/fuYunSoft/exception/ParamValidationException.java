package com.fuYunSoft.exception;

/**
 * 参数校验异常类
 */
public class ParamValidationException extends BusinessException {

    public ParamValidationException(String message) {
        super(400, message);
    }
}
