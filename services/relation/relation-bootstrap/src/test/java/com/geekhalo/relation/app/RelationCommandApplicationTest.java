package com.geekhalo.relation.app;


import com.geekhalo.relation.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class RelationCommandApplicationTest {
    @Autowired
    private RelationCommandApplication commandApplication;

    @BeforeEach
    public void setUp() throws Exception {
        Assertions.assertNotNull(this.commandApplication);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void sendRequest() {
    }

    @Test
    public void receiveRequest() {
    }

    @Test
    public void acceptRequest() {
    }

    @Test
    public void cancelRequest() {
    }
}