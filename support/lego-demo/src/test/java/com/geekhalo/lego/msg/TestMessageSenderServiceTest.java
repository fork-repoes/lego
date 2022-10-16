package com.geekhalo.lego.msg;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.core.msg.Message;
import com.geekhalo.lego.core.msg.ReliableMessageCompensator;
import com.geekhalo.lego.core.msg.support.LocalMessage;
import com.geekhalo.lego.core.msg.support.LocalMessageRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
public class TestMessageSenderServiceTest {
    @Autowired
    private TestMessageSenderService testMessageSenderService;
    @Autowired
    private TestMessageSender testMessageSender;

    @Autowired
    private ReliableMessageCompensator reliableMessageCompensator;

    @Autowired
    private LocalMessageRepository localMessageRepository;


    @BeforeEach
    public void setUp() throws Exception {
        this.testMessageSender.clean();
        this.testMessageSender.cleanError();
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.testMessageSender.clean();
        this.testMessageSender.cleanError();
    }

    @Test
    public void testNoTransaction(){
        this.testMessageSenderService.testNoTransaction();

        List<Message> messages = this.testMessageSender.getMessages();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
    }

    @Test
    public void testTestSuccess() {
        this.testMessageSenderService.testSuccess();

        List<Message> messages = this.testMessageSender.getMessages();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
    }

    @Test
    public void testTestError() {
        boolean error = false;
        try {
            this.testMessageSenderService.testError();
        }catch (Exception e){
            error = true;
        }

        Assertions.assertTrue(error);

        List<Message> messages = this.testMessageSender.getMessages();
        Assertions.assertTrue(CollectionUtils.isEmpty(messages));
    }

    @Test
    public void loadAndSend() throws InterruptedException {
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -120), 1000);

        this.testMessageSender.markError();
        for (int i = 0; i<10;i++){
            this.testMessageSenderService.testSuccess();
        }
        this.testMessageSender.cleanError();

        {
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(10, localMessages.size());
        }

        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -60), 5);

        {
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(10, localMessages.size());
        }

        TimeUnit.SECONDS.sleep(15);

        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -60), 50);

        {
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(0, localMessages.size());
        }

    }
}