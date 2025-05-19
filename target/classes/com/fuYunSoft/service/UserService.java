package com.fuYunSoft.service;

import com.fuYunSoft.pojo.User;

public interface UserService {
    /**
     * 微信用户登录/注册
     * @param openid 微信OpenID
     * @param nickname 用户昵称
     * @param avatarUrl 用户头像URL
     * @return 用户信息
     */
    User loginOrRegister(String openid, String nickname, String avatarUrl);

    /**
     * 获取当前用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserInfo(Long userId);

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param user 包含更新信息的用户对象
     * @return 更新后的用户信息
     */
    User updateUserInfo(Long userId, User user);
}