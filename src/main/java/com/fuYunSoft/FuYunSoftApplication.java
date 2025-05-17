package com.fuYunSoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用启动类
 *
 * 注解说明：
 * @SpringBootApplication 核心启动注解（包含@Configuration、@EnableAutoConfiguration、@ComponentScan）
 * @MapperScan 指定MyBatis Mapper接口的扫描路径
 * @EnableScheduling 启用定时任务支持
 * @EnableAsync 启用异步方法调用支持
 */
@SpringBootApplication
@MapperScan("com.fuYunSoft.mapper")
@EnableScheduling
@EnableAsync
public class FuYunSoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuYunSoftApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  FuYun启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}