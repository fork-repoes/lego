package com.geekhalo.lego.msg.consumer;

import com.geekhalo.lego.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class RocketBasedUserMessageConsumerTest extends UserMessageConsumerTest{
    @Autowired
    private RocketBasedUserMessageConsumer userMessageConsumer;

    @Override
    protected String getTopic() {
        return "consumer-test-topic-1";
    }

    @Override
    protected UserMessageConsumer getUserMessageConsumer() {
        return userMessageConsumer;
    }
}
