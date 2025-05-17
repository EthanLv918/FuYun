package com.fuYunSoft.schedule;

import com.fuYunSoft.service.WxTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 微信Token定时刷新任务
 */
@Component
@Slf4j
public class WxTokenRefreshTask {

    private final WxTokenService wxTokenService;

    @Autowired
    public WxTokenRefreshTask(WxTokenService wxTokenService) {
        this.wxTokenService = wxTokenService;
    }

    /**
     * 定时刷新微信Token（每1小时55分钟执行一次，比有效期提前5分钟）
     */
    @Scheduled(fixedRate = 6900 * 1000) // 6900秒 = 1小时55分钟
    public void autoRefreshToken() {
        log.info("开始执行微信Token定时刷新任务...");
        try {
            String newToken = wxTokenService.refreshAccessToken();
            log.info("微信Token刷新成功，新Token前6位：{}...", newToken.substring(0, 6));
        } catch (Exception e) {
            log.error("微信Token刷新失败", e);
        }
    }
}