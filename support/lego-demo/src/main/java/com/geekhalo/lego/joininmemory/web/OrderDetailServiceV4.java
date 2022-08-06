package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.joininmemory.service.order.Order;
import com.geekhalo.lego.joininmemory.service.order.OrderRepository;
import com.geekhalo.lego.joininmemory.web.vo.OrderVO;
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
public class OrderDetailServiceV4 implements OrderDetailService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JoinService joinService;

    @Override
    public List<? extends BaseOrderDetailVO> getByUserId(Long userId) {
        List<Order> orders = this.orderRepository.getByUserId(userId);

        List<CustomAnnOrderDetailVO> orderDetailVOS = orders.stream()
                .map(order -> new CustomAnnOrderDetailVO(OrderVO.apply(order)))
                .collect(toList());

        this.joinService.joinInMemory(CustomAnnOrderDetailVO.class, orderDetailVOS);
        return orderDetailVOS;
    }
}
