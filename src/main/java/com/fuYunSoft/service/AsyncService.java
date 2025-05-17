package com.fuYunSoft.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void asyncMethod() {
        System.out.println("异步方法执行，线程：" + Thread.currentThread().getName());
    }
}