package com.geekhalo.lego.msg.sender;

import com.geekhalo.lego.core.msg.sender.Message;
import com.geekhalo.lego.core.msg.sender.ReliableMessageSender;
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
        // 业务逻辑
        Message message = buildMessage();
        this.reliableMessageSender.send(message);
    }

    public void testNoTransactionError(){
        // 业务逻辑
        Message message = buildMessage();
        this.reliableMessageSender.send(message);
        throw new RuntimeException();
    }


    @Transactional
    public void testSuccess(){
        // 业务逻辑
        Message message = buildMessage();
        // 业务逻辑
        this.reliableMessageSender.send(message);
    }

    @Transactional
    public void testError(){
        // 业务逻辑
        Message message = buildMessage();
        // 业务逻辑
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
