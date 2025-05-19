package com.fuYunSoft.controller;

import com.fuYunSoft.pojo.User;
import com.fuYunSoft.service.UserService;
import com.fuYunSoft.service.WxAuthService;
import com.fuYunSoft.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口（无Token验证版）
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WxAuthService wxAuthService;

    /**
     * 微信小程序登录接口
     */
    @PostMapping("/login")
    public JsonResult login(
            @RequestParam String code,
            @RequestParam String nickname,
            @RequestParam String avatarUrl) {
        try {
            String openid = wxAuthService.getWxOpenId(code);
            User user = userService.loginOrRegister(openid, nickname, avatarUrl);
            return JsonResult.ok(user);
        } catch (Exception e) {
            return JsonResult.error("登录失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户信息（通过用户ID）
     */
    @GetMapping("/info")
    public JsonResult getUserInfo(@RequestParam Long userId) {
        try {
            User user = userService.getUserInfo(userId);
            return JsonResult.ok(user);
        } catch (Exception e) {
            return JsonResult.error("获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户信息（通过用户ID）
     */
    @PutMapping("/updateUserInfo")
    public JsonResult updateUserInfo(
            @RequestParam Long userId,
            @RequestBody User user) {
        try {
            User updatedUser = userService.updateUserInfo(userId, user);
            return JsonResult.ok(updatedUser);
        } catch (Exception e) {
            return JsonResult.error("更新用户信息失败: " + e.getMessage());
        }
    }
}