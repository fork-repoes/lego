package com.geekhalo.relation.app;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RelationListener {
    @Autowired
    private RelationCommandApplication commandApplication;
    @Autowired
    private RelationCommandRepository commandRepository;

    @EventListener
    public void handle(RequestSentEvent requestSentEvent){
        RelationKey reversed = requestSentEvent.getSource().getKey().reversed();
        commandApplication.receiveRequest(new ReceiveRequestCommand(reversed));
    }

    @EventListener
    public void handle(RequestAcceptedEvent requestAcceptedEvent){
        RelationKey reversed = requestAcceptedEvent.getSource().getKey().reversed();
        Optional<Relation> relationOptional = commandRepository.findByKey(reversed);
        relationOptional.ifPresent(relation -> {
            if (relation.getStatus() != RelationStatus.REQUEST_ACCEPTED){
                AcceptRequestCommand acceptRequestCommand = new AcceptRequestCommand(reversed);
                this.commandApplication.acceptRequest(acceptRequestCommand);
            }
        });
    }

    @EventListener
    public void handle(RequestCancelledEvent requestCancelledEvent){
        RelationKey reversed = requestCancelledEvent.getSource().getKey().reversed();
        Optional<Relation> relationOptional = commandRepository.findByKey(reversed);
        relationOptional.ifPresent(relation -> {
            if (relation.getStatus() != RelationStatus.REQUEST_CANCELLED){
                CancelRequestCommand cancelRequestCommand = new CancelRequestCommand(reversed);
                this.commandApplication.cancelRequest(cancelRequestCommand);
            }
        });
    }
}
