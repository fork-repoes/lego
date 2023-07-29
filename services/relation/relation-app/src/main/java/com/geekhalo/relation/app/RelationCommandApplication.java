package com.geekhalo.relation.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.relation.domain.relation.Relation;
import com.geekhalo.relation.domain.relation.RelationCommandRepository;
import com.geekhalo.relation.domain.relation.acceptRequest.AcceptRequestCommand;
import com.geekhalo.relation.domain.relation.cancelRequest.CancelRequestCommand;
import com.geekhalo.relation.domain.relation.receiveRequest.ReceiveRequestCommand;
import com.geekhalo.relation.domain.relation.sendRequest.SendRequestCommand;

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
