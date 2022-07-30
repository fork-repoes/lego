package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.joininmemory.service.address.Address;
import com.geekhalo.lego.joininmemory.service.address.AddressRepository;
import com.geekhalo.lego.joininmemory.service.order.Order;
import com.geekhalo.lego.joininmemory.service.order.OrderRepository;
import com.geekhalo.lego.joininmemory.service.product.Product;
import com.geekhalo.lego.joininmemory.service.product.ProductRepository;
import com.geekhalo.lego.joininmemory.service.user.User;
import com.geekhalo.lego.joininmemory.service.user.UserRepository;
import com.geekhalo.lego.joininmemory.web.vo.AddressVO;
import com.geekhalo.lego.joininmemory.web.vo.OrderVO;
import com.geekhalo.lego.joininmemory.web.vo.ProductVO;
import com.geekhalo.lego.joininmemory.web.vo.UserVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class OrderDetailServiceV2 implements OrderDetailService{
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

        List<Long> userIds = orders.stream()
                .map(Order::getUserId)
                .collect(toList());
        List<User> users = this.userRepository.getByIds(userIds);
        Map<Long, User> userMap = users.stream()
                .collect(toMap(User::getId, Function.identity(), (a, b) -> a));

        List<Long> addressIds = orders.stream()
                .map(Order::getAddressId)
                .collect(toList());
        List<Address> addresses = this.addressRepository.getByIds(addressIds);
        Map<Long, Address> addressMap = addresses.stream()
                .collect(toMap(Address::getId, Function.identity(), (a, b) -> a));

        List<Long> productIds = orders.stream()
                .map(Order::getProductId)
                .collect(toList());
        List<Product> products = this.productRepository.getByIds(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(toMap(Product::getId, Function.identity(), (a, b) -> a));

        List<OrderDetailVO> orderDetailVOS = Lists.newArrayListWithCapacity(orders.size());

        for (Order order : orders){
            OrderVO orderVO = OrderVO.apply(order);
            OrderDetailVO orderDetailVO = new OrderDetailVO(orderVO);

            User user = userMap.get(order.getUserId());
            UserVO userVO = UserVO.apply(user);
            orderDetailVO.setUser(userVO);

            Address address = addressMap.get(order.getAddressId());
            AddressVO addressVO = AddressVO.apply(address);
            orderDetailVO.setAddress(addressVO);

            Product product = productMap.get(order.getProductId());
            ProductVO productVO = ProductVO.apply(product);
            orderDetailVO.setProduct(productVO);

            orderDetailVOS.add(orderDetailVO);
        }
        return orderDetailVOS;
    }
}
