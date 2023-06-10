package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.support.handler.aggloader.SmartAggLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaySuccessCommandAggLoader implements SmartAggLoader<PaySuccessCommand, Order> {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Optional<Order> load(PaySuccessCommand paySuccessCommand) {
        return orderRepository.findById(paySuccessCommand.getOrderId());
    }

    @Override
    public boolean apply(Class cmdClass, Class aggClass) {
        return cmdClass.equals(PaySuccessCommand.class) && aggClass.equals(Order.class);
    }
}
