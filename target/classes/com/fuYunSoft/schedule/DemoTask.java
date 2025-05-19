package com.fuYunSoft.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoTask {

    // 每5秒执行一次
    @Scheduled(fixedRate = 60000)
    public void task1() {
        System.out.println("定时任务执行：" + System.currentTimeMillis());
    }

    // 每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailyTask() {
        System.out.println("每日任务执行");
    }
}