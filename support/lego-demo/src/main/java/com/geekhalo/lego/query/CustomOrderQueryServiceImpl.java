package com.geekhalo.lego.query;

import com.geekhalo.lego.core.joininmemory.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class CustomOrderQueryServiceImpl implements CustomOrderQueryService{
    @Autowired
    private JoinService joinService;

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Override
    public List<OrderDetail> getPaidByUserId(Long id) {
        List<Order> orders = orderQueryRepository.getByUserIdAndStatus(id, OrderStatus.PAID);
        List<OrderDetail> orderDetails = orders.stream()
                .map(OrderDetail::new)
                .collect(Collectors.toList());
        this.joinService.joinInMemory(orderDetails);
        return orderDetails;
    }
}
