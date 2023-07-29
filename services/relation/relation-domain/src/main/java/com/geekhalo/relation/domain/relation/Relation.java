package com.geekhalo.relation.domain.relation;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
import com.geekhalo.relation.domain.relation.acceptRequest.AcceptRequestContext;
import com.geekhalo.relation.domain.relation.acceptRequest.RequestAcceptedEvent;
import com.geekhalo.relation.domain.relation.cancelRequest.CancelRequestContext;
import com.geekhalo.relation.domain.relation.cancelRequest.RequestCancelledEvent;
import com.geekhalo.relation.domain.relation.receiveRequest.ReceiveRequestContext;
import com.geekhalo.relation.domain.relation.receiveRequest.RequestReceivedEvent;
import com.geekhalo.relation.domain.relation.sendRequest.RequestSentEvent;
import com.geekhalo.relation.domain.relation.sendRequest.SendRequestContext;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Setter(AccessLevel.PRIVATE)
    private RelationKey key;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.PRIVATE)
    private RelationStatus status;

    public static Relation applySendRequest(SendRequestContext context){
        RelationKey relationKey = context.getCommand().getKey();
        return createRelation(relationKey);
    }

    private static Relation createRelation(RelationKey relationKey) {
        Relation relation = new Relation();
        relation.setKey(relationKey);
        relation.init();
        return relation;
    }

    private void init() {
        setStatus(RelationStatus.NONE);
    }

    public static Relation applyReceiveRequest(ReceiveRequestContext context){
        return createRelation(context.getCommand().getKey());
    }


    public void sendRequest(SendRequestContext context) {
        // 第一次申请，变成 REQUEST_SENT
        if (getStatus() == RelationStatus.NONE) {
            //添加代码
            setStatus(RelationStatus.REQUEST_SENT);
            addEvent(new RequestSentEvent(this));
            return;
        }
        // 取消后再申请，变成 REQUEST_SENT
        if (getStatus() == RelationStatus.REQUEST_CANCELLED){
            //添加代码
            setStatus(RelationStatus.REQUEST_SENT);
            addEvent(new RequestSentEvent(this));
            return;
        }
        // 别人已经申请，变成 REQUEST_ACCEPTED
        if (getStatus() == RelationStatus.REQUEST_RECEIVED){
            setStatus(RelationStatus.REQUEST_ACCEPTED);
            addEvent(new RequestAcceptedEvent(this));
            return;
        }
        throw new RuntimeException("Status is Error");
    }

    public void receiveRequest(ReceiveRequestContext context) {
        // 第一次接到申请
        if (getStatus() == RelationStatus.NONE){
            //添加代码
            setStatus(RelationStatus.REQUEST_RECEIVED);
            addEvent(new RequestReceivedEvent(this));
            return;
        }

        // 取消后再申请
        if (getStatus() == RelationStatus.REQUEST_CANCELLED){
            //添加代码
            setStatus(RelationStatus.REQUEST_RECEIVED);
            addEvent(new RequestReceivedEvent(this));
            return;
        }

        throw new RuntimeException("Status is Error");

    }

    public void acceptRequest(AcceptRequestContext context) {
        // 接受别人的请求
        if (getStatus() == RelationStatus.REQUEST_RECEIVED) {
            //添加代码
            setStatus(RelationStatus.REQUEST_ACCEPTED);
            addEvent(new RequestAcceptedEvent(this));
            return;
        }
        // 别人接受自己的请求
        if (getStatus() == RelationStatus.REQUEST_SENT) {
            //添加代码
            setStatus(RelationStatus.REQUEST_ACCEPTED);
            addEvent(new RequestAcceptedEvent(this));
            return;
        }

        throw new RuntimeException("Status is Error");
    }

    public void cancelRequest(CancelRequestContext context) {
        // 取消
        if (getStatus() == RelationStatus.REQUEST_RECEIVED) {
            //添加代码
            setStatus(RelationStatus.REQUEST_CANCELLED);
            addEvent(new RequestCancelledEvent(this));
            return;
        }
        // 取消
        if (getStatus() == RelationStatus.REQUEST_SENT) {
            //添加代码
            setStatus(RelationStatus.REQUEST_CANCELLED);
            addEvent(new RequestCancelledEvent(this));
            return;
        }

        throw new RuntimeException("Status is Error");

    }
}