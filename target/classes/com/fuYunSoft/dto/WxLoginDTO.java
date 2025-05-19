package com.fuYunSoft.dto;

import lombok.Data;

@Data
public class WxLoginDTO {
    private String code;          // 微信临时登录凭证code
    private String nickname;      // 用户昵称
    private String avatarUrl;     // 用户头像URL
}