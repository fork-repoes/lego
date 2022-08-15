package com.geekhalo.lego.joininmemory.demo.v1;

import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.address.AddressRepository;
import com.geekhalo.lego.service.order.Order;
import com.geekhalo.lego.service.order.OrderRepository;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.product.ProductRepository;
import com.geekhalo.lego.service.user.User;
import com.geekhalo.lego.service.user.UserRepository;
import com.geekhalo.lego.joininmemory.demo.OrderDetailService;
import com.geekhalo.lego.joininmemory.demo.OrderDetailVO;
import com.geekhalo.lego.joininmemory.demo.AddressVO;
import com.geekhalo.lego.joininmemory.demo.OrderVO;
import com.geekhalo.lego.joininmemory.demo.ProductVO;
import com.geekhalo.lego.joininmemory.demo.UserVO;
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
public class OrderDetailServiceV1 implements OrderDetailService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<? extends OrderDetailVO> getByUserId(Long userId) {
        List<Order> orders = this.orderRepository.getByUserId(userId);
        return orders.stream()
                .map(order -> convertToOrderDetailVO(order))
                .collect(toList());
    }

    private OrderDetailVOV1 convertToOrderDetailVO(Order order) {
        OrderVO orderVO = OrderVO.apply(order);

        OrderDetailVOV1 orderDetailVO = new OrderDetailVOV1(orderVO);

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
