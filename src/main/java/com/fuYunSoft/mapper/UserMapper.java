package com.fuYunSoft.mapper;

import com.fuYunSoft.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 根据OpenID查询用户
     */
    User selectByOpenId(@Param("openid") String openid);

    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return 用户对象
     */
    User selectById(@Param("userId") Long userId);

    /**
     * 插入新用户
     */
    int insert(User user);

    /**
     * 更新用户信息
     */
    int update(User user);
}