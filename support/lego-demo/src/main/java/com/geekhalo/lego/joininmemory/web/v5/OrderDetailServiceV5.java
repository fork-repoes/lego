package com.geekhalo.lego.joininmemory.web.v5;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.joininmemory.service.order.Order;
import com.geekhalo.lego.joininmemory.service.order.OrderRepository;
import com.geekhalo.lego.joininmemory.web.OrderDetailService;
import com.geekhalo.lego.joininmemory.web.OrderDetailVO;
import com.geekhalo.lego.joininmemory.web.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class OrderDetailServiceV5 implements OrderDetailService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JoinService joinService;

    @Override
    public List<? extends OrderDetailVO> getByUserId(Long userId) {
        List<Order> orders = this.orderRepository.getByUserId(userId);

        List<OrderDetailVOV5> orderDetailVOS = orders.stream()
                .map(order -> new OrderDetailVOV5(OrderVO.apply(order)))
                .collect(toList());

        this.joinService.joinInMemory(OrderDetailVOV5.class, orderDetailVOS);
        return orderDetailVOS;
    }
}
