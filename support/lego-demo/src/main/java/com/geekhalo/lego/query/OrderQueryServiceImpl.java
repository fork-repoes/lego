package com.geekhalo.lego.query;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.core.singlequery.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
@Validated
public class OrderQueryServiceImpl implements OrderQueryService{
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

    @Override
    public OrderDetail getById(Long id) {
        Order order = this.orderQueryRepository.findById(id)
                .orElse(null);
        if (order == null){
            return null;
        }
        OrderDetail orderDetail = new OrderDetail(order);
        this.joinService.joinInMemory(orderDetail);
        return orderDetail;
    }

    @Override
    public Page<OrderDetail> pageByUserId(PageByUserId query) {
        Page<OrderDetail> orderDetailPage = this.orderQueryRepository.pageOf(query, OrderDetail::new);
        if (orderDetailPage.hasContent()){
            this.joinService.joinInMemory(orderDetailPage.getContent());
        }
        return orderDetailPage;
    }

    @Override
    public List<OrderDetail> getByUserId(GetByUserId getByUserId) {
        List<OrderDetail> orderDetails = this.orderQueryRepository.listOf(getByUserId, OrderDetail::new);
        if (CollectionUtils.isEmpty(orderDetails)){
            return Collections.emptyList();
        }
        this.joinService.joinInMemory(orderDetails);
        return orderDetails;
    }

    @Override
    public Long countByUser(CountByUserId countByUserId) {
        return this.orderQueryRepository.countOf(countByUserId);
    }
}
