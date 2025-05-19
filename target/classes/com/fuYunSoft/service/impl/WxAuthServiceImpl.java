package com.fuYunSoft.service.impl;

import com.fuYunSoft.service.WxAuthService;
import com.fuYunSoft.utils.HttpUtil;
import com.fuYunSoft.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxAuthServiceImpl implements WxAuthService {

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.secret}")
    private String appSecret;

    private static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public String getWxOpenId(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        try {
            String response = HttpUtil.get(AUTH_URL, params);
            Map<String, Object> result = JsonUtil.parseMap(response);

            if (result.containsKey("openid")) {
                return result.get("openid").toString();
            } else {
                log.error("获取openid失败: {}", response);
                throw new RuntimeException("微信登录失败: " + result.get("errmsg"));
            }
        } catch (Exception e) {
            log.error("微信登录异常", e);
            throw new RuntimeException("微信登录异常", e);
        }
    }
}