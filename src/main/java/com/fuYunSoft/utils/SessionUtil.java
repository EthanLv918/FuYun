package com.fuYunSoft.utils;

import com.fuYunSoft.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Session工具类
 */
@Component
public class SessionUtil {

    private static final String USER_SESSION_KEY = "currentUser";

    /**
     * 获取当前请求的Session
     */
    public HttpSession getSession() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest().getSession() : null;
    }

    /**
     * 设置当前登录用户
     */
    public void setCurrentUser(User user) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(USER_SESSION_KEY, user);
        }
    }

    /**
     * 获取当前登录用户
     */
    public User getCurrentUser() {
        HttpSession session = getSession();
        return session == null ? null : (User) session.getAttribute(USER_SESSION_KEY);
    }

    /**
     * 移除当前登录用户
     */
    public void removeCurrentUser() {
        HttpSession session = getSession();
        if (session != null) {
            session.removeAttribute(USER_SESSION_KEY);
        }
    }

    /**
     * 检查用户是否登录
     */
    public boolean isLogin() {
        return getCurrentUser() != null;
    }
}