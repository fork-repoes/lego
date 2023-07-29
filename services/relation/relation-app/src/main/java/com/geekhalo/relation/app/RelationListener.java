package com.geekhalo.relation.app;

import com.geekhalo.lego.annotation.async.AsyncForOrderedBasedRocketMQ;
import com.geekhalo.relation.domain.relation.Relation;
import com.geekhalo.relation.domain.relation.RelationCommandRepository;
import com.geekhalo.relation.domain.relation.RelationKey;
import com.geekhalo.relation.domain.relation.RelationStatus;
import com.geekhalo.relation.domain.relation.acceptRequest.AcceptRequestCommand;
import com.geekhalo.relation.domain.relation.acceptRequest.RequestAcceptedEvent;
import com.geekhalo.relation.domain.relation.cancelRequest.CancelRequestCommand;
import com.geekhalo.relation.domain.relation.cancelRequest.RequestCancelledEvent;
import com.geekhalo.relation.domain.relation.receiveRequest.ReceiveRequestCommand;
import com.geekhalo.relation.domain.relation.sendRequest.RequestSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@Slf4j
public class RelationListener {
    @Autowired
    private RelationCommandApplication commandApplication;
    @Autowired
    private RelationCommandRepository commandRepository;

    @Autowired
    @Lazy
    private RelationListener relationListener;

    @TransactionalEventListener
    public void handle(RequestSentEvent requestSentEvent){
        RelationKey reversed = requestSentEvent.getSource().getKey().reversed();
        relationListener.receiveRequest(reversed);
    }

    @AsyncForOrderedBasedRocketMQ(
            enable = "${relation.async.enable:true}",
            topic = "${relation.async.topic}",
            tag = "ReceiveRequest",
            shardingKey = "#key.owner",
            consumerGroup = "${relation.async.consumerGroup.receive}")
    @Async
    public void receiveRequest(RelationKey key){
        try{
            commandApplication.receiveRequest(new ReceiveRequestCommand(key));
        }catch (Exception e){
            log.error("failed to run receiveRequest use key {}", key, e);
        }

    }

    @TransactionalEventListener
    public void handle(RequestAcceptedEvent requestAcceptedEvent){
        RelationKey reversed = requestAcceptedEvent.getSource().getKey().reversed();
        Optional<Relation> relationOptional = commandRepository.findByKey(reversed);
        relationOptional.ifPresent(relation -> {
            if (relation.getStatus() != RelationStatus.REQUEST_ACCEPTED){
                this.relationListener.acceptRequest(reversed);
            }
        });
    }

    @AsyncForOrderedBasedRocketMQ(
            enable = "${relation.async.enable:true}",
            topic = "${relation.async.topic}",
            tag = "AcceptRequest",
            shardingKey = "#key.owner",
            consumerGroup = "${relation.async.consumerGroup.accept}")
    @Async
    public void acceptRequest(RelationKey key){
        try {
            this.commandApplication.acceptRequest(new AcceptRequestCommand(key));
        }catch (Exception e){
            log.error("failed to run acceptRequest use key {}", key, e);
        }

    }

    @TransactionalEventListener
    public void handle(RequestCancelledEvent requestCancelledEvent){
        RelationKey reversed = requestCancelledEvent.getSource().getKey().reversed();
        Optional<Relation> relationOptional = commandRepository.findByKey(reversed);
        relationOptional.ifPresent(relation -> {
            if (relation.getStatus() != RelationStatus.REQUEST_CANCELLED){
                this.relationListener.cancelRequest(reversed);
            }
        });
    }

    @AsyncForOrderedBasedRocketMQ(
            enable = "${relation.async.enable:true}",
            topic = "${relation.async.topic}",
            tag = "CancelRequest",
            shardingKey = "#key.owner",
            consumerGroup = "${relation.async.consumerGroup.cancel}")
    @Async
    public void cancelRequest(RelationKey key){
        try {
            CancelRequestCommand cancelRequestCommand = new CancelRequestCommand(key);
            this.commandApplication.cancelRequest(cancelRequestCommand);
        }catch (Exception e){
            log.error("failed to run cancelRequest use key {}", key, e);
        }

    }
}
