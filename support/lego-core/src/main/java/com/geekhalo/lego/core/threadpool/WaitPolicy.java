package com.geekhalo.lego.core.threadpool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public final class WaitPolicy implements RejectedExecutionHandler {
    private WaitPolicy(){

    }

    @SneakyThrows
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            log.info("ThreadPoolExecutor is Full, waiting for resource");
            e.getQueue().put(r);
            log.info("Success to put task {}", r);
        }
    }

    public static final WaitPolicy getInstance(){
        return new WaitPolicy();
    }
}
