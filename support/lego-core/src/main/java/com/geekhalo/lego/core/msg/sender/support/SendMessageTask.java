package com.geekhalo.lego.core.msg.sender.support;

import com.geekhalo.lego.core.msg.sender.Message;
import com.geekhalo.lego.core.msg.sender.MessageSender;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class SendMessageTask implements Runnable{
    private final LocalMessageRepository localMessageRepository;
    private final MessageSender messageSender;
    @Getter
    private final LocalMessage localMessage;

    public SendMessageTask(LocalMessageRepository localMessageRepository,
                           MessageSender messageSender,
                           LocalMessage localMessage) {
        this.localMessageRepository = localMessageRepository;
        this.messageSender = messageSender;
        this.localMessage = localMessage;
    }

    @Override
    public void run() {
        log.debug("begin to run send task for {}", this.localMessage);
        Message message = this.localMessage.toMessage();
        try {
            String msgId = this.messageSender.send(message);
            this.localMessage.sendSuccess(msgId);
            log.info("success to send to mq, message is {}", message);
        }catch (Exception e){
            this.localMessage.sendError();
            log.error("failed to send message {}", message, e);
        }
        this.localMessageRepository.update(localMessage);
        log.debug("success to run send task for {}", this.localMessage);
    }
}
