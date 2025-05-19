package com.fuYunSoft.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户实体类
 * 对应数据库中的users表
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 自增用户ID
     */
    private Integer userId;

    /**
     * 微信OpenID
     */
    private String openid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 是否参赛者(0-否 1-是)
     */
    private Integer isCompetitor;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    // 示例：添加一个非数据库字段的属性
    public String getDisplayName() {
        return nickname != null ? nickname : "用户" + userId;
    }
}