package com.geekhalo.lego.async;

import com.geekhalo.lego.annotation.async.AsyncBasedRocketMQ;
import com.geekhalo.lego.annotation.async.AsyncForOrderedBasedRocketMQ;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AsyncService {
    @Getter
    private final List<CallData> callDatas = new ArrayList<>();

    @AsyncBasedRocketMQ(topic = "${async.test.normal.topic}",
            tag = "asyncTest",
            consumerGroup = "${async.test.normal.group}")
    public void asyncTest(Long id, String name, AsyncInputBean bean){
        log.info("receive data id {}, name {}, bean", id, name, bean);

        CallData callData = new CallData(id, name, bean);
        this.callDatas.add(callData);
    }


    @AsyncForOrderedBasedRocketMQ(topic = "${async.test.order.topic}",
            tag = "asyncTest",
            shardingKey = "#id",
            consumerGroup = "${async.test.order.group}")
    public void asyncTestForOrder(Long id, String name, AsyncInputBean bean){
        log.info("receive data id {}, name {}, bean {}", id, name, bean);

        CallData callData = new CallData(id, name, bean);
        this.callDatas.add(callData);
    }


    @Value
    public static class CallData{
        private final Long id;
        private final String name;
        private final AsyncInputBean bean;
    }
}
