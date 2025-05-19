package com.fuYunSoft.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一API响应结果封装
 */
public class JsonResult<T> {
    private int code;    // 状态码
    private String msg;  // 消息
    private T data;      // 数据

    // 构造函数
    private JsonResult() {
    }

    // 成功静态方法
    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    // 错误静态方法
    public static <T> JsonResult<T> error(String msg) {
        return error(500, msg);
    }

    public static <T> JsonResult<T> error(int code, String msg) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    // 链式调用方法
    public JsonResult<T> put(String key, Object value) {
        if (this.data == null) {
            this.data = (T) new HashMap<String, Object>();
        }
        if (this.data instanceof Map) {
            ((Map) this.data).put(key, value);
        }
        return this;
    }

    // getter和setter方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}