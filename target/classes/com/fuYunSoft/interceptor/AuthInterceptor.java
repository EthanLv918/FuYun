package com.fuYunSoft.interceptor;

import com.fuYunSoft.exception.UnauthorizedException;
import com.fuYunSoft.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 * 检查用户是否登录
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查用户是否登录
        if (!sessionUtil.isLogin()) {
            throw new UnauthorizedException("请先登录");
        }
        return true;
    }
}