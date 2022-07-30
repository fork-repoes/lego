package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.joininmemory.web.vo.AddressVO;
import com.geekhalo.lego.joininmemory.web.vo.OrderVO;
import com.geekhalo.lego.joininmemory.web.vo.ProductVO;
import com.geekhalo.lego.joininmemory.web.vo.UserVO;
import com.geekhalo.lego.joininmemory.service.address.Address;
import com.geekhalo.lego.joininmemory.service.address.AddressRepository;
import com.geekhalo.lego.joininmemory.service.order.Order;
import com.geekhalo.lego.joininmemory.service.order.OrderRepository;
import com.geekhalo.lego.joininmemory.service.product.Product;
import com.geekhalo.lego.joininmemory.service.product.ProductRepository;
import com.geekhalo.lego.joininmemory.service.user.User;
import com.geekhalo.lego.joininmemory.service.user.UserRepository;
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
public class OrderDetailServiceV1 implements OrderDetailService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderDetailVO> getByUserId(Long userId) {
        List<Order> orders = this.orderRepository.getByUserId(userId);
        return orders.stream()
                .map(order -> convertToOrderDetailVO(order))
                .collect(toList());
    }

    private OrderDetailVO convertToOrderDetailVO(Order order) {
        OrderVO orderVO = OrderVO.apply(order);

        OrderDetailVO orderDetailVO = new OrderDetailVO(orderVO);

        Address address = this.addressRepository.getById(order.getAddressId());
        AddressVO addressVO = AddressVO.apply(address);
        orderDetailVO.setAddress(addressVO);

        User user = this.userRepository.getById(order.getUserId());
        UserVO userVO = UserVO.apply(user);
        orderDetailVO.setUser(userVO);

        Product product = this.productRepository.getById(order.getProductId());
        ProductVO productVO = ProductVO.apply(product);
        orderDetailVO.setProduct(productVO);

        return orderDetailVO;
    }
}
