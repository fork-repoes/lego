package com.geekhalo.lego.wide;

import com.geekhalo.lego.annotation.delay.DelayBasedRocketMQ;
import com.geekhalo.lego.core.wide.WidePatrolService;
import org.springframework.aop.framework.AopContext;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class WideOrderPatrolService implements WidePatrolService<Long, WideOrderType> {
    private final WidePatrolService<Long, WideOrderType> widePatrolService;

    public WideOrderPatrolService(WidePatrolService<Long, WideOrderType> widePatrolService) {
        this.widePatrolService = widePatrolService;
    }

    @Override
    @DelayBasedRocketMQ(topic = "wide_order_patrol", tag = "SingleIndex", consumerGroup = "order_patrol_group", delayLevel = 2)
    public void index(Long aLong) {
        this.widePatrolService.index(aLong);
    }

    @Override
    @DelayBasedRocketMQ(topic = "wide_order_patrol", tag = "BatchIndex", consumerGroup = "order_patrol_group", delayLevel = 2)
    public void index(List<Long> longs) {
        this.widePatrolService.index(longs);
    }

    @Override
    public <KEY> void updateItem(WideOrderType wideOrderType, KEY key) {
        ((WideOrderPatrolService)AopContext.currentProxy()).updateItem(wideOrderType, (Long) key);
    }

    @DelayBasedRocketMQ(topic = "wide_order_patrol", tag = "UpdateByItem", consumerGroup = "order_patrol_group", delayLevel = 2)
    public void updateItem(WideOrderType wideOrderType, Long id){
        this.widePatrolService.updateItem(wideOrderType, id);
    }

    @Override
    public void setReindexConsumer(Consumer<List<Long>> consumer) {
        this.widePatrolService.setReindexConsumer(consumer);
    }
}
