package com.fuYunSoft.service;

public interface WxTokenService {

    /**
     * 获取微信AccessToken（带自动刷新机制）
     */
    String getAccessToken();

    /**
     * 强制刷新微信Token（供外部调用）
     */
    String refreshAccessToken();
}