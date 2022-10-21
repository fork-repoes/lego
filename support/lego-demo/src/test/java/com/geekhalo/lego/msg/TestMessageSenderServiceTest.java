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

        {
            List<Message> messages = this.testMessageSender.getMessages();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
        }

        this.testMessageSender.clean();
        boolean error = false;
        try {
            this.testMessageSenderService.testNoTransactionError();
        }catch (Exception e){
            error = true;
        }

        Assertions.assertTrue(error);

        {
            List<Message> messages = this.testMessageSender.getMessages();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
        }
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
        // 处理消费表中待发送数据
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -120), 1000);

        // 进行 error 标记， MessageSender 发送请求直接失败
        this.testMessageSender.markError();
        for (int i = 0; i<10;i++){
            // 执行业务逻辑，业务逻辑不受影响
            this.testMessageSenderService.testSuccess();
        }
        // 清理 error 标记，MessageSender 正常发送
        this.testMessageSender.cleanError();


        {
            // 检测消息表中存在待处理的任务
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(10, localMessages.size());
        }

        // 对消息进行补充处理
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -60), 5);

        {
            //  由于时间限制，未处理消息表的任务
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(10, localMessages.size());
        }

        // 等待时间超时
        TimeUnit.SECONDS.sleep(15);

        this.testMessageSender.clean();
        // 对消息进行补充处理
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -60), 50);

        {
            //  成功处理消息表的待处理任务
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(0, localMessages.size());

            List<Message> messages = this.testMessageSender.getMessages();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
        }

    }
}