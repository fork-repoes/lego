package com.geekhalo.lego.core.msg.support;

import com.geekhalo.lego.core.msg.Message;
import com.geekhalo.lego.core.msg.ReliableMessageSender;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class LocalTableBasedReliableMessageSender implements ReliableMessageSender {
    private final ReliableMessageSendService reliableMessageSendService;


    public LocalTableBasedReliableMessageSender(ReliableMessageSendService reliableMessageSendService) {
        this.reliableMessageSendService = reliableMessageSendService;
    }

    @Override
    public void send(Message message) {
        this.reliableMessageSendService.saveAndSend(message);
    }
}
