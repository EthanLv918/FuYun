package com.fuYunSoft.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 */
public class LogUtil {

    /**
     * 获取业务日志logger
     */
    public static Logger getBusinessLogger() {
        return LoggerFactory.getLogger("business");
    }

    /**
     * 获取异常日志logger
     */
    public static Logger getExceptionLogger() {
        return LoggerFactory.getLogger("exception");
    }

    /**
     * 记录业务日志
     * @param message 日志信息
     * @param args 参数
     */
    public static void logBusiness(String message, Object... args) {
        getBusinessLogger().info(message, args);
    }

    /**
     * 记录异常日志
     * @param message 异常信息
     * @param e 异常对象
     */
    public static void logException(String message, Throwable e) {
        getExceptionLogger().error(message, e);
    }
}