package com.fuYunSoft.service.impl;

import com.fuYunSoft.mapper.UserMapper;
import com.fuYunSoft.pojo.User;
import com.fuYunSoft.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginOrRegister(String openid, String nickname, String avatarUrl) {
        // 1. 查询是否已存在用户
        User user = userMapper.selectByOpenId(openid);
        Date now = new Date();

        if (user == null) {
            // 2. 新用户注册
            user = new User();
            user.setOpenid(openid);
            user.setNickname(nickname);
            user.setAvatarUrl(avatarUrl);
            user.setIsCompetitor(0); // 默认非参赛者
            user.setCreatedAt(now);
            user.setUpdatedAt(now);
            userMapper.insert(user);
            log.info("新用户注册成功，openid: {}", openid);
        } else {
            // 3. 老用户登录，更新信息
            user.setNickname(nickname);
            user.setAvatarUrl(avatarUrl);
            user.setUpdatedAt(now);
            userMapper.update(user);
            log.info("用户登录成功，userId: {}", user.getUserId());
        }

        return user;
    }

    @Override
    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    @Override
    public User updateUserInfo(Long userId, User user) {
        // 1. 验证用户是否存在
        User existingUser = userMapper.selectById(userId);
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 只更新允许修改的字段
        existingUser.setNickname(user.getNickname());
        existingUser.setAvatarUrl(user.getAvatarUrl());
        existingUser.setUpdatedAt(new Date());

        // 3. 执行更新
        userMapper.update(existingUser);
        log.info("用户信息更新成功，userId: {}", userId);

        return existingUser;
    }
}