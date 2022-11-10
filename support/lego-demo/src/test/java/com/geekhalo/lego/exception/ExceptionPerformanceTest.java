package com.geekhalo.lego.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Created by taoli on 2022/11/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class ExceptionPerformanceTest {

    @Test
    public void normal(){
        for (int i=0; i<10 * 10000; i ++){
            normalTest(i);
        }
        int count = 100 * 10000;
        long start = System.currentTimeMillis();
        for (int i=0; i< count; i ++){
            normalTest(i);
        }
        long end = System.currentTimeMillis();

        log.info("Normal Cost {} ms, {} pre ms", end - start, count / (end - start));
    }

    @Test
    public void exception(){
        for (int i=0; i<10 * 10000; i ++){
            exceptionTest(i);
        }
        int count = 100 * 10000;
        long start = System.currentTimeMillis();
        for (int i=0; i< count; i ++){
            exceptionTest(i);
        }
        long end = System.currentTimeMillis();

        log.info("Exception Cost {} ms, {} pre ms", end - start, count / (end - start));
    }

    private boolean normalTest(int i){
        if (i % 2 == 0) {
            new Object();
            return true;
        }else {
            return false;
        }
    }

    private boolean exceptionTest(int i){
        try {
            throwException(i);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void throwException(int i) {
        if (i % 2 == 0){
            throw new RuntimeException();
        }
    }
}
