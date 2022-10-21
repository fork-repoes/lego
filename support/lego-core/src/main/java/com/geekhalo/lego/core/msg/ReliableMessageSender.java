package com.geekhalo.lego.core.msg;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface ReliableMessageSender {
    void send(Message message);
}