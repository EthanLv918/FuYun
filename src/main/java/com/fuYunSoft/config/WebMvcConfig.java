package com.fuYunSoft.config;

import com.fuYunSoft.interceptor.AuthInterceptor;
import com.fuYunSoft.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Web MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 白名单
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/api/user/login"); // 登录接口
        excludePaths.add("/api/user/wxLogin"); // 微信登录接口
        excludePaths.add("/error"); // 错误页面

        // 日志拦截器 - 拦截所有请求
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");

        // 认证拦截器 - 拦截需要登录的请求
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
}