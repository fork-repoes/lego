package com.geekhalo.relation.app;

import com.geekhalo.relation.Application;
import com.geekhalo.relation.app.group.QueryGroupByOwner;
import com.geekhalo.relation.app.group.RelationGroupCommandApplication;
import com.geekhalo.relation.app.group.RelationGroupQueryApplication;
import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.RelationGroupStatus;
import com.geekhalo.relation.domain.group.create.CreateRelationGroupCommand;
import com.geekhalo.relation.domain.group.disable.DisableRelationGroupCommand;
import com.geekhalo.relation.domain.group.enable.EnableRelationGroupCommand;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
public class RelationGroupCommandApplicationTest {
    @Autowired
    private RelationGroupCommandApplication commandApplication;

    @Autowired
    private RelationGroupQueryApplication queryApplication;

    private Long user;
    private RelationGroup relationGroup;

    @BeforeEach
    public void setUp() throws Exception {
        this.user = RandomUtils.nextLong(0, Long.MAX_VALUE);
    }

    @Test
    public void test(){
        String name = "测试分组";
        String decrs = "测试分组描述";
        {
            CreateRelationGroupCommand createRelationGroupCommand = new CreateRelationGroupCommand();
            createRelationGroupCommand.setUser(this.user);
            createRelationGroupCommand.setName(name);
            createRelationGroupCommand.setDescr(decrs);
            this.relationGroup = this.commandApplication.create(createRelationGroupCommand);
            Assertions.assertNotNull(relationGroup);
            Assertions.assertNotNull(relationGroup.getId());
        }
        {
            RelationGroup group = this.queryApplication.getById(this.relationGroup.getId());
            Assertions.assertNotNull(group);
            Assertions.assertEquals(this.relationGroup.getId(), group.getId());
            Assertions.assertEquals(this.user, group.getOwner());
            Assertions.assertEquals(RelationGroupStatus.ENABLE, group.getStatus());
            Assertions.assertEquals(name, group.getName());
            Assertions.assertEquals(decrs, group.getDescr());
        }
        {
            QueryGroupByOwner queryGroupByOwner = new QueryGroupByOwner();
            queryGroupByOwner.setOwner(this.user);
            List<RelationGroup> relationGroups = this.queryApplication.listOf(queryGroupByOwner);
            Assertions.assertFalse(relationGroups.isEmpty());
        }
    }

    @Test
    public void status(){
        String name = "测试分组";
        String decrs = "测试分组描述";
        {
            CreateRelationGroupCommand createRelationGroupCommand = new CreateRelationGroupCommand();
            createRelationGroupCommand.setUser(this.user);
            createRelationGroupCommand.setName(name);
            createRelationGroupCommand.setDescr(decrs);
            this.relationGroup = this.commandApplication.create(createRelationGroupCommand);
            Assertions.assertNotNull(relationGroup);
            Assertions.assertNotNull(relationGroup.getId());
        }
        {
            RelationGroup group = this.queryApplication.getById(this.relationGroup.getId());
            Assertions.assertNotNull(group);
            Assertions.assertEquals(this.relationGroup.getId(), group.getId());
            Assertions.assertEquals(this.user, group.getOwner());
            Assertions.assertEquals(RelationGroupStatus.ENABLE, group.getStatus());
            Assertions.assertEquals(name, group.getName());
            Assertions.assertEquals(decrs, group.getDescr());
        }

        DisableRelationGroupCommand disableRelationGroupCommand = new DisableRelationGroupCommand();
        disableRelationGroupCommand.setId(this.relationGroup.getId());
        this.commandApplication.disable(disableRelationGroupCommand);

        {
            RelationGroup group = this.queryApplication.getById(this.relationGroup.getId());
            Assertions.assertNotNull(group);
            Assertions.assertEquals(this.relationGroup.getId(), group.getId());
            Assertions.assertEquals(this.user, group.getOwner());
            Assertions.assertEquals(RelationGroupStatus.DISABLE, group.getStatus());
            Assertions.assertEquals(name, group.getName());
            Assertions.assertEquals(decrs, group.getDescr());
        }

        EnableRelationGroupCommand enableRelationGroupCommand = new EnableRelationGroupCommand();
        enableRelationGroupCommand.setId(this.relationGroup.getId());
        this.commandApplication.enable(enableRelationGroupCommand);

        {
            RelationGroup group = this.queryApplication.getById(this.relationGroup.getId());
            Assertions.assertNotNull(group);
            Assertions.assertEquals(this.relationGroup.getId(), group.getId());
            Assertions.assertEquals(this.user, group.getOwner());
            Assertions.assertEquals(RelationGroupStatus.ENABLE, group.getStatus());
            Assertions.assertEquals(name, group.getName());
            Assertions.assertEquals(decrs, group.getDescr());
        }
    }
}
