package com.geekhalo.lego.core.msg.sender.support;

import com.geekhalo.lego.core.msg.sender.ReliableMessageCompensator;

import java.util.Date;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class LocalTableBasedReliableMessageCompensator implements ReliableMessageCompensator {
    private final ReliableMessageSendService reliableMessageSendService;

    public LocalTableBasedReliableMessageCompensator(ReliableMessageSendService reliableMessageSendService) {
        this.reliableMessageSendService = reliableMessageSendService;
    }

    @Override
    public void compensate(Date startDate, int sizePreTask) {
        this.reliableMessageSendService.loadAndResend(startDate, sizePreTask);
    }
}
