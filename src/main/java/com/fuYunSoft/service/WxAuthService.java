package com.fuYunSoft.service;

public interface WxAuthService {
    /**
     * 微信小程序登录凭证校验
     * @param code 小程序登录code
     * @return 微信openid
     */
    String getWxOpenId(String code);
}