package com.geekhalo.relation.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.geekhalo.relation.domain.acceptRequest.AcceptRequestContext;
import com.geekhalo.relation.domain.acceptRequest.RequestAcceptedEvent;
import com.geekhalo.relation.domain.cancelRequest.CancelRequestContext;
import com.geekhalo.relation.domain.cancelRequest.RequestCancelledEvent;
import com.geekhalo.relation.domain.receiveRequest.ReceiveRequestContext;
import com.geekhalo.relation.domain.receiveRequest.RequestReceivedEvent;
import com.geekhalo.relation.domain.sendRequest.RequestSentEvent;
import com.geekhalo.relation.domain.sendRequest.SendRequestContext;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Relation extends AbstractAggRoot { 
    @Id
    private Long id;

    @Embedded
    @Setter(AccessLevel.PRIVATE)
    private RelationKey key;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.PRIVATE)
    private RelationStatus status;

    public static Relation applySendRequest(SendRequestContext context){
        return new Relation();
    }

    public static Relation applyReceiveRequest(ReceiveRequestContext context){
        return new Relation();
    }

    public void sendRequest(SendRequestContext context) {
        //添加代码
        addEvent(new RequestSentEvent(this));
    }

    public void receiveRequest(ReceiveRequestContext context) {
        //添加代码
        addEvent(new RequestReceivedEvent(this));
    }

    public void acceptRequest(AcceptRequestContext context) {
        //添加代码
        addEvent(new RequestAcceptedEvent(this));
    }

    public void cancelRequest(CancelRequestContext context) {
        //添加代码
        addEvent(new RequestCancelledEvent(this));
    }
}