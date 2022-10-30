package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.WideCommandRepository;
import com.geekhalo.lego.core.wide.WideItemData;
import com.geekhalo.lego.core.wide.WideOrderItem;
import com.geekhalo.lego.core.wide.WideOrderItemType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class WideOrderItemCommandRepository implements WideCommandRepository<Long, WideOrderItemType, WideOrderItem> {
    private final List<WideOrderItem> wideOrderItems = Lists.newArrayList();

    @Override
    public void save(List<WideOrderItem> wides) {
        this.wideOrderItems.addAll(wides);
    }

    @Override
    public List<WideOrderItem> findByIds(List<Long> masterIds) {
        return this.wideOrderItems.stream()
                .filter(wideOrderItem -> masterIds.contains(wideOrderItem.getOrderId()))
                .collect(Collectors.toList());
    }

    @Override
    public <KEY> void consumeByItem(WideOrderItemType wideOrderItemType, KEY key, Consumer<WideOrderItem> wideConsumer) {
        updateByItem(wideOrderItemType, key, wideConsumer);
    }

    @Override
    public <KEY> void updateByItem(WideOrderItemType wideOrderItemType,
                                   KEY key,
                                   Consumer<WideOrderItem> wideConsumer) {
        switch (wideOrderItemType){
            case ORDER_ITEM:
                wideOrderItems.stream()
                        .filter(wideOrderItem -> wideOrderItem.getOrderId().equals(key))
                        .forEach(wideConsumer);
            case USER:
                wideOrderItems.stream()
                        .filter(wideOrderItem -> wideOrderItem.getUserId().equals(key))
                        .forEach(wideConsumer);
            case PRODUCT:
                wideOrderItems.stream()
                        .filter(wideOrderItem -> wideOrderItem.getProductId().equals(key))
                        .forEach(wideConsumer);
        }
    }

    @Override
    public boolean supportUpdateFor(WideOrderItemType wideOrderItemType) {
        return false;
    }

    @Override
    public <KEY> void updateByItem(WideOrderItemType wideOrderItemType, KEY key, WideItemData<WideOrderItemType, ?> item) {

    }
}