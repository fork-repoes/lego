package com.geekhalo.lego.msg.sender;

import com.geekhalo.lego.core.msg.sender.MessageSender;
import com.geekhalo.lego.starter.msg.sender.LocalTableBasedReliableMessageConfigurationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@Slf4j
public class LocalTableBasedReliableMessageConfiguration
        extends LocalTableBasedReliableMessageConfigurationSupport {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MessageSender messageSender;

    @Override
    protected DataSource dataSource() {
        return this.dataSource;
    }

    @Override
    protected String messageTable() {
        return "test_message";
    }

    @Override
    protected MessageSender createMessageSend() {
        return this.messageSender;
    }

}
