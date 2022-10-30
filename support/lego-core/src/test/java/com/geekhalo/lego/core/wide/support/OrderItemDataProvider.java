package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.OrderItem;
import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.core.wide.WideOrderItemType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDataProvider implements WideItemDataProvider<WideOrderItemType, Long, OrderItem> {
    @Override
    public List<OrderItem> apply(List<Long> aLong) {
        return aLong.stream()
                .map(id -> {
                    long i = id -1;
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(1L + i);
                    orderItem.setUserId(2L + i);
                    orderItem.setProductId(3L + i);
                    orderItem.setStatus(1);
                    orderItem.setUpdateDate(new Date());
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    @Override
    public WideOrderItemType getSupportType() {
        return WideOrderItemType.ORDER_ITEM;
    }
}