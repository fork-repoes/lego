package com.geekhalo.relation.app;


import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.relation.Application;
import com.geekhalo.relation.app.group.RelationGroupCommandApplication;
import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.create.CreateRelationGroupCommand;
import com.geekhalo.relation.domain.relation.Relation;
import com.geekhalo.relation.domain.relation.RelationCommandRepository;
import com.geekhalo.relation.domain.relation.RelationKey;
import com.geekhalo.relation.domain.relation.RelationStatus;
import com.geekhalo.relation.domain.relation.acceptRequest.AcceptRequestCommand;
import com.geekhalo.relation.domain.relation.addToBlackList.AddToBlackListCommand;
import com.geekhalo.relation.domain.relation.cancelRequest.CancelRequestCommand;
import com.geekhalo.relation.domain.relation.removeFromBlackList.RemoveFromBlackListCommand;
import com.geekhalo.relation.domain.relation.sendRequest.SendRequestCommand;
import com.geekhalo.relation.domain.relation.updateGroup.UpdateGroupCommand;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = Application.class)
public class RelationCommandApplicationTest {
    @Autowired
    private RelationCommandApplication commandApplication;

    @Autowired
    private RelationCommandRepository commandRepository;

    @Autowired
    private RelationQueryApplication queryApplication;

    @Autowired
    private RelationGroupCommandApplication groupCommandApplication;

    private RelationGroup ownerGroup;

    private RelationGroup recipientGroup;

    private RelationKey ownerKey;

    private RelationKey recipientKey;

    @BeforeEach
    public void setUp() throws Exception {
        Assertions.assertNotNull(this.commandApplication);
        Long owner = RandomUtils.nextLong(0, Long.MAX_VALUE);
        Long recipient = RandomUtils.nextLong(0, Long.MAX_VALUE);
        this.ownerKey = new RelationKey();
        this.ownerKey.setOwner(owner);
        this.ownerKey.setRecipient(recipient);

        this.recipientKey = this.ownerKey.reversed();

        {
            CreateRelationGroupCommand command = new CreateRelationGroupCommand();
            command.setUser(this.ownerKey.getOwner());
            command.setName("发送分组");
            command.setDescr("发送分组");
            this.ownerGroup = this.groupCommandApplication.create(command);
        }

        {
            CreateRelationGroupCommand command = new CreateRelationGroupCommand();
            command.setUser(this.recipientKey.getOwner());
            command.setName("接收分组");
            command.setDescr("接收分组");
            this.recipientGroup = this.groupCommandApplication.create(command);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * A 发送请求 <br />
     * 1. owner 存在一条 "请求已发送"(REQUEST_SENT)
     * 2. recipient 存在一条 "请求已接收"(REQUEST_RECEIVED)
     */
    @Test
    public void sendRequest() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        sendRequestCommand.setGroupId(this.ownerGroup.getId());
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_SENT, relation.getStatus());
            Assertions.assertEquals(this.ownerGroup.getId(), relation.getGroupId());


            {
                Page<Relation> relations = this.queryApplication.getByKeyOwner(this.ownerKey.getOwner(), PageRequest.of(0, 10));
                Assertions.assertTrue(relations.hasContent());
            }
            {
                QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
                queryRelationByOwner.setOwner(this.ownerKey.getOwner());
                queryRelationByOwner.setStatuses(Arrays.asList(RelationStatus.REQUEST_SENT, RelationStatus.REQUEST_RECEIVED));
                queryRelationByOwner.setPageable(new Pageable(0, 10));
                com.geekhalo.lego.core.singlequery.Page<Relation> page = this.queryApplication.pageOf(queryRelationByOwner);
                Assertions.assertTrue(page.hasContent());
            }
        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_RECEIVED, relation.getStatus());

            Page<Relation> relations = this.queryApplication.getByKeyOwner(this.ownerKey.getOwner(), PageRequest.of(0, 10));
            Assertions.assertTrue(relations.hasContent());

        }
    }

    /**
     * A 发送请求，B 也发送请求 <br />
     * 1. owner 存在一条 "请求已接受"（REQUEST_ACCEPTED）
     * 2. recipient 存在一条 "请求已接受"（REQUEST_ACCEPTED）
     */
    @Test
    public void sendRequest2() throws InterruptedException {
        SendRequestCommand ownerSendRequestCommand = new SendRequestCommand(this.ownerKey);
        this.commandApplication.sendRequest(ownerSendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        SendRequestCommand recipientSendRequestCommand = new SendRequestCommand(this.recipientKey);
        this.commandApplication.sendRequest(recipientSendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
        }
    }

    /**
     * A 发送请求，B 接受请求 <br />
     * 1. owner 存在一条 "请求已接受"（REQUEST_ACCEPTED）
     * 2. recipient 存在一条 "请求已接受"（REQUEST_ACCEPTED）
     */
    @Test
    public void acceptRequest() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        AcceptRequestCommand acceptRequestCommand = new AcceptRequestCommand(this.recipientKey);
        acceptRequestCommand.setGroupId(this.recipientGroup.getId());
        this.commandApplication.acceptRequest(acceptRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());

        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertEquals(this.recipientGroup.getId(), relation.getGroupId());
        }
    }

    /**
     * A发送请求，然后取消 <br />
     * 1. owner 存在一条 "请求已取消"
     * 2. recipient 存在一条 "请求已取消"
     */
    @Test
    public void cancelRequest() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        CancelRequestCommand cancelRequestCommand = new CancelRequestCommand(this.ownerKey);
        this.commandApplication.cancelRequest(cancelRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_CANCELLED, relation.getStatus());
        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_CANCELLED, relation.getStatus());
        }
    }

    /**
     * A发送请求，然后取消，A 再次发送请求 <br />
     * 1. owner 存在一条 "请求已发送"(REQUEST_SENT)
     * 2. recipient 存在一条 "请求已接收"(REQUEST_RECEIVED)
     */
    @Test
    public void cancelAndSendRequest() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        CancelRequestCommand cancelRequestCommand = new CancelRequestCommand(this.ownerKey);
        this.commandApplication.cancelRequest(cancelRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_SENT, relation.getStatus());
        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_RECEIVED, relation.getStatus());
        }
    }


    /**
     * A发送请求，然后取消，B 发送请求 <br />
     * 1. owner 存在一条 "请求已接收"(REQUEST_RECEIVED)
     * 2. recipient 存在一条 "请求已发送"(REQUEST_SENT)
     */
    @Test
    public void cancelAndBSendRequest() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        CancelRequestCommand cancelRequestCommand = new CancelRequestCommand(this.ownerKey);
        this.commandApplication.cancelRequest(cancelRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        SendRequestCommand sendRequestCommandB = new SendRequestCommand(this.recipientKey);
        this.commandApplication.sendRequest(sendRequestCommandB);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_RECEIVED, relation.getStatus());
        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_SENT, relation.getStatus());
        }
    }

    @Test
    public void updateGroupId() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        sendRequestCommand.setGroupId(this.ownerGroup.getId());
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_SENT, relation.getStatus());
            Assertions.assertEquals(this.ownerGroup.getId(), relation.getGroupId());
        }

        CreateRelationGroupCommand command = new CreateRelationGroupCommand();
        command.setUser(this.ownerKey.getOwner());
        command.setName("新分组");
        command.setDescr("新分组描述");
        RelationGroup group = this.groupCommandApplication.create(command);

        UpdateGroupCommand updateGroupCommand = new UpdateGroupCommand();
        updateGroupCommand.setKey(this.ownerKey);
        updateGroupCommand.setGroupId(group.getId());
        this.commandApplication.updateGroup(updateGroupCommand);


        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_SENT, relation.getStatus());
            Assertions.assertEquals(group.getId(), relation.getGroupId());
        }
    }


    @Test
    public void blackList() throws InterruptedException {
        SendRequestCommand sendRequestCommand = new SendRequestCommand(this.ownerKey);
        this.commandApplication.sendRequest(sendRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        AcceptRequestCommand acceptRequestCommand = new AcceptRequestCommand(this.recipientKey);
        acceptRequestCommand.setGroupId(this.recipientGroup.getId());
        this.commandApplication.acceptRequest(acceptRequestCommand);

        TimeUnit.SECONDS.sleep(1);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertFalse(relation.getInBlackList());

            QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
            queryRelationByOwner.setOwner(this.ownerKey.getOwner());
            queryRelationByOwner.setInBlackList(false);
            queryRelationByOwner.setPageable(new Pageable(0, 10));
            com.geekhalo.lego.core.singlequery.Page<Relation> relationPage = this.queryApplication.pageOf(queryRelationByOwner);
            Assertions.assertTrue(relationPage.hasContent());

        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertEquals(this.recipientGroup.getId(), relation.getGroupId());
            Assertions.assertFalse(relation.getInBlackList());

            QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
            queryRelationByOwner.setOwner(this.recipientKey.getOwner());
            queryRelationByOwner.setInBlackList(false);
            queryRelationByOwner.setPageable(new Pageable(0, 10));
            com.geekhalo.lego.core.singlequery.Page<Relation> relationPage = this.queryApplication.pageOf(queryRelationByOwner);
            Assertions.assertTrue(relationPage.hasContent());
        }

        AddToBlackListCommand ownerAddToBlackListCommand = new AddToBlackListCommand();
        ownerAddToBlackListCommand.setKey(this.ownerKey);
        this.commandApplication.addToBlackList(ownerAddToBlackListCommand);

        AddToBlackListCommand recipientAddToBlackListCommand = new AddToBlackListCommand();
        recipientAddToBlackListCommand.setKey(this.recipientKey);
        this.commandApplication.addToBlackList(recipientAddToBlackListCommand);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertTrue(relation.getInBlackList());

            QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
            queryRelationByOwner.setOwner(this.ownerKey.getOwner());
            queryRelationByOwner.setInBlackList(true);
            queryRelationByOwner.setPageable(new Pageable(0, 10));
            com.geekhalo.lego.core.singlequery.Page<Relation> relationPage = this.queryApplication.pageOf(queryRelationByOwner);
            Assertions.assertTrue(relationPage.hasContent());

        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertEquals(this.recipientGroup.getId(), relation.getGroupId());
            Assertions.assertTrue(relation.getInBlackList());

            QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
            queryRelationByOwner.setOwner(this.recipientKey.getOwner());
            queryRelationByOwner.setInBlackList(true);
            queryRelationByOwner.setPageable(new Pageable(0, 10));
            com.geekhalo.lego.core.singlequery.Page<Relation> relationPage = this.queryApplication.pageOf(queryRelationByOwner);
            Assertions.assertTrue(relationPage.hasContent());
        }

        RemoveFromBlackListCommand ownerRemoveFromBlackListCommand = new RemoveFromBlackListCommand();
        ownerRemoveFromBlackListCommand.setKey(this.ownerKey);
        this.commandApplication.removeFromBlackList(ownerRemoveFromBlackListCommand);

        RemoveFromBlackListCommand recipientRemoveFromBlackListCommand = new RemoveFromBlackListCommand();
        recipientRemoveFromBlackListCommand.setKey(this.recipientKey);
        this.commandApplication.removeFromBlackList(recipientRemoveFromBlackListCommand);

        {
            Optional<Relation> ownerRelation = this.commandRepository.findByKey(this.ownerKey);
            Assertions.assertTrue(ownerRelation.isPresent());
            Relation relation = ownerRelation.get();
            Assertions.assertEquals(this.ownerKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertFalse(relation.getInBlackList());

            QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
            queryRelationByOwner.setOwner(this.ownerKey.getOwner());
            queryRelationByOwner.setInBlackList(false);
            queryRelationByOwner.setPageable(new Pageable(0, 10));
            com.geekhalo.lego.core.singlequery.Page<Relation> relationPage = this.queryApplication.pageOf(queryRelationByOwner);
            Assertions.assertTrue(relationPage.hasContent());

        }

        {
            Optional<Relation> recipientRelation = this.commandRepository.findByKey(this.recipientKey);
            Assertions.assertTrue(recipientRelation.isPresent());
            Relation relation = recipientRelation.get();
            Assertions.assertEquals(this.recipientKey, relation.getKey());
            Assertions.assertEquals(RelationStatus.REQUEST_ACCEPTED, relation.getStatus());
            Assertions.assertEquals(this.recipientGroup.getId(), relation.getGroupId());
            Assertions.assertFalse(relation.getInBlackList());

            QueryRelationByOwner queryRelationByOwner = new QueryRelationByOwner();
            queryRelationByOwner.setOwner(this.recipientKey.getOwner());
            queryRelationByOwner.setInBlackList(false);
            queryRelationByOwner.setPageable(new Pageable(0, 10));
            com.geekhalo.lego.core.singlequery.Page<Relation> relationPage = this.queryApplication.pageOf(queryRelationByOwner);
            Assertions.assertTrue(relationPage.hasContent());
        }

    }

}