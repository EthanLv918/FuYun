package com.fuYunSoft.pojo;

import lombok.*;

import java.util.Date;

/**
 * 全局凭证存储实体类
 * 对应数据库表：all_token
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AllToken {
    /**
     * 主键ID
     */
    private Integer tokenId;

    /**
     * 应用名称(如wechat/mini_program)
     */
    private String appName;

    /**
     * 凭证类型(access_token/jsapi_ticket等)
     */
    private String tokenType;

    /**
     * 凭证内容
     */
    private String tokenValue;

    /**
     * 有效期(秒)
     */
    private Integer expiresIn;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 预留字段1
     */
    private String reserved1;

    /**
     * 预留字段2
     */
    private String reserved2;

    /**
     * 预留字段3
     */
    private String reserved3;

    /**
     * 预留字段4
     */
    private String reserved4;

    /**
     * 预留字段5
     */
    private String reserved5;
}