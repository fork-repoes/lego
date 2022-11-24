package com.geekhalo.lego.faultrecovery.smart;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
@Slf4j
@Getter
public class RetryService2 {
    private int count = 0;

    private int retryCount = 0;
    private int fallbackCount = 0;
    private int recoverCount = 0;

    public void clean(){
        this.retryCount = 0;
        this.fallbackCount = 0;
        this.recoverCount = 0;
    }


    public Long retry(Long input) throws Throwable{
        this.retryCount ++;
        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate.execute(new RetryCallback<Long, Throwable>() {
            @Override
            public Long doWithRetry(RetryContext context) throws Throwable {
                return doSomething(input);
            }
        });
    }


    public Long fallback(Long input) throws Throwable{
        this.fallbackCount ++;
        RetryTemplate retryTemplate = new RetryTemplate();

        retryTemplate.setRetryPolicy(new NeverRetryPolicy());

        return retryTemplate.execute(new RetryCallback<Long, Throwable>() {
            @Override
            public Long doWithRetry(RetryContext context) throws Throwable {
                return doSomething(input);
            }
        }, new RecoveryCallback<Long>() {
            @Override
            public Long recover(RetryContext context) throws Exception {
                return RetryService2.this.recover(context.getLastThrowable(), input);
            }
        });
    }


    public Long recover(Throwable e, Long input){
        this.recoverCount ++;
        log.info("recover-{}", input);
        return input;
    }



    private Long doSomething(Long input) {
        if (count ++ % 2 == 0){
            log.info("Error-{}", input);
            throw new RuntimeException();
        }
        log.info("Success-{}", input);
        return input;
    }
}
