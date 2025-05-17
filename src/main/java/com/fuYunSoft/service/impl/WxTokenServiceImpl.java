package com.fuYunSoft.service.impl;

import com.fuYunSoft.mapper.AllTokenMapper;
import com.fuYunSoft.pojo.AllToken;
import com.fuYunSoft.service.WxTokenService;
import com.fuYunSoft.utils.HttpUtil;
import com.fuYunSoft.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxTokenServiceImpl implements WxTokenService {

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.secret}")
    private String appSecret;

    @Autowired
    private AllTokenMapper tokenMapper;

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String TOKEN_TYPE = "access_token";
    private static final String APP_NAME = "wechat";

    @Override
    public String getAccessToken() {
        AllToken token = tokenMapper.selectByAppAndType(APP_NAME, TOKEN_TYPE);
        if (token == null || isTokenExpired(token)) {
            return refreshAccessToken();
        }
        return token.getTokenValue();
    }

    @Override
    public String refreshAccessToken() {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", appId);
        params.put("secret", appSecret);

        try {
            String response = HttpUtil.get(ACCESS_TOKEN_URL, params);
            Map<String, Object> result = JsonUtil.parseMap(response);

            if (result.containsKey("access_token")) {
                String accessToken = result.get("access_token").toString();
                int expiresIn = Integer.parseInt(result.get("expires_in").toString());

                saveOrUpdateToken(accessToken, expiresIn);
                log.info("微信AccessToken刷新成功");
                return accessToken;
            } else {
                log.error("获取微信Token失败: {}", response);
                throw new RuntimeException("获取微信Token失败: " + result.get("errmsg"));
            }
        } catch (Exception e) {
            log.error("刷新微信Token异常", e);
            throw new RuntimeException("刷新微信Token异常", e);
        }
    }

    private void saveOrUpdateToken(String tokenValue, int expiresIn) {
        AllToken token = tokenMapper.selectByAppAndType(APP_NAME, TOKEN_TYPE);
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expiresIn * 1000L);

        if (token == null) {
            token = new AllToken();
            token.setAppName(APP_NAME);
            token.setTokenType(TOKEN_TYPE);
            token.setTokenValue(tokenValue);
            token.setExpiresIn(expiresIn);
            token.setExpireTime(expireTime);
            token.setCreatedAt(now);
            tokenMapper.insert(token);
        } else {
            token.setTokenValue(tokenValue);
            token.setExpiresIn(expiresIn);
            token.setExpireTime(expireTime);
            token.setUpdatedAt(now);
            tokenMapper.updateByPrimaryKey(token);
        }
    }

    private boolean isTokenExpired(AllToken token) {
        return token.getExpireTime().before(new Date());
    }
}