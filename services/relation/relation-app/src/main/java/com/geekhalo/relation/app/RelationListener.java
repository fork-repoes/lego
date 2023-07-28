package com.geekhalo.relation.app;

import com.geekhalo.relation.domain.Relation;
import com.geekhalo.relation.domain.RelationCommandRepository;
import com.geekhalo.relation.domain.RelationKey;
import com.geekhalo.relation.domain.RelationStatus;
import com.geekhalo.relation.domain.acceptRequest.AcceptRequestCommand;
import com.geekhalo.relation.domain.acceptRequest.RequestAcceptedEvent;
import com.geekhalo.relation.domain.cancelRequest.CancelRequestCommand;
import com.geekhalo.relation.domain.cancelRequest.RequestCancelledEvent;
import com.geekhalo.relation.domain.receiveRequest.ReceiveRequestCommand;
import com.geekhalo.relation.domain.sendRequest.RequestSentEvent;
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
