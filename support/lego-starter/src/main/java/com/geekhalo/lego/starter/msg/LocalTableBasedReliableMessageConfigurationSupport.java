package com.geekhalo.lego.starter.msg;

import com.geekhalo.lego.core.msg.MessageSender;
import com.geekhalo.lego.core.msg.ReliableMessageCompensator;
import com.geekhalo.lego.core.msg.ReliableMessageSender;
import com.geekhalo.lego.core.msg.support.*;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class LocalTableBasedReliableMessageConfigurationSupport {

    @Bean
    public ReliableMessageSender reliableMessageSender(ReliableMessageSendService reliableMessageSendService){
        return new LocalTableBasedReliableMessageSender(reliableMessageSendService);
    }

    @Bean
    public ReliableMessageCompensator reliableMessageCompensator(ReliableMessageSendService reliableMessageSendService){
        return new LocalTableBasedReliableMessageCompensator(reliableMessageSendService);
    }

    @Bean
    public ReliableMessageSendService reliableMessageSendService(LocalMessageRepository localMessageRepository,
                                                                 MessageSender messageSender){
        return new ReliableMessageSendService(localMessageRepository, messageSender);
    }

    @Bean
    public LocalMessageRepository localMessageRepository(){
        return new JdbcTemplateBasedLocalMessageRepository(dataSource(), messageTable());
    }

    @Bean
    public MessageSender messageSender(){
        return createMessageSend();
    }

    protected abstract DataSource dataSource();

    protected abstract String messageTable();

    protected abstract MessageSender createMessageSend();
}