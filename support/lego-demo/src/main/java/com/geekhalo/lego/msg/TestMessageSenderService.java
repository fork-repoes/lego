package com.geekhalo.lego.msg;

import com.geekhalo.lego.core.msg.Message;
import com.geekhalo.lego.core.msg.ReliableMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class TestMessageSenderService {

    @Autowired
    private ReliableMessageSender reliableMessageSender;

    public void testNoTransaction(){
        Message message = buildMessage();
        this.reliableMessageSender.send(message);
    }


    @Transactional
    public void testSuccess(){
        Message message = buildMessage();

        this.reliableMessageSender.send(message);
    }

    @Transactional
    public void testError(){
        Message message = buildMessage();
        this.reliableMessageSender.send(message);
        throw new RuntimeException();
    }

    private Message buildMessage() {
        Message message = Message.builder()
                .msg("msg")
                .orderly(true)
                .shardingKey("123")
                .topic("test_topic")
                .tag("tag")
                .build();
        return message;
    }
}
