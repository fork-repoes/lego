package com.geekhalo.relation.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.relation.domain.Relation;
import com.geekhalo.relation.domain.RelationCommandRepository;
import com.geekhalo.relation.domain.acceptRequest.AcceptRequestCommand;
import com.geekhalo.relation.domain.cancelRequest.CancelRequestCommand;
import com.geekhalo.relation.domain.receiveRequest.ReceiveRequestCommand;
import com.geekhalo.relation.domain.sendRequest.SendRequestCommand;

@CommandServiceDefinition(
        domainClass = Relation.class,
        repositoryClass = RelationCommandRepository.class
)
public interface RelationCommandApplication {


    void sendRequest(SendRequestCommand command);

    void receiveRequest(ReceiveRequestCommand command);

    void acceptRequest(AcceptRequestCommand command);

    void cancelRequest(CancelRequestCommand command);
}
