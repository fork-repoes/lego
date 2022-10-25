package com.geekhalo.lego.msg.consumer;

import com.geekhalo.lego.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/10/21.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
class AnnotationBasedUserMessageConsumerTest
    extends UserMessageConsumerTest{
    @Autowired
    private AnnotationBasedUserMessageConsumer userMessageConsumer;

    protected String getTopic(){
        return "consumer-test-topic-2";
    }

    protected UserMessageConsumer getUserMessageConsumer(){
        return userMessageConsumer;
    }

}