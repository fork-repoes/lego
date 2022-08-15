package com.geekhalo.lego.joininmemory.demo.v2;

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
public class OrderDetailServiceV2 implements OrderDetailService {
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

        List<OrderDetailVOV2> orderDetailVOS = orders.stream()
                .map(order -> new OrderDetailVOV2(OrderVO.apply(order)))
                .collect(toList());

        List<Long> userIds = orders.stream()
                .map(Order::getUserId)
                .collect(toList());
        List<User> users = this.userRepository.getByIds(userIds);
        Map<Long, User> userMap = users.stream()
                .collect(toMap(User::getId, Function.identity(), (a, b) -> a));
        for (OrderDetailVOV2 orderDetailVO : orderDetailVOS){
            User user = userMap.get(orderDetailVO.getOrder().getUserId());
            UserVO userVO = UserVO.apply(user);
            orderDetailVO.setUser(userVO);
        }

        List<Long> addressIds = orders.stream()
                .map(Order::getAddressId)
                .collect(toList());
        List<Address> addresses = this.addressRepository.getByIds(addressIds);
        Map<Long, Address> addressMap = addresses.stream()
                .collect(toMap(Address::getId, Function.identity(), (a, b) -> a));
        for (OrderDetailVOV2 orderDetailVO : orderDetailVOS){
            Address address = addressMap.get(orderDetailVO.getOrder().getAddressId());
            AddressVO addressVO = AddressVO.apply(address);
            orderDetailVO.setAddress(addressVO);
        }

        List<Long> productIds = orders.stream()
                .map(Order::getProductId)
                .collect(toList());
        List<Product> products = this.productRepository.getByIds(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(toMap(Product::getId, Function.identity(), (a, b) -> a));
        for (OrderDetailVOV2 orderDetailVO : orderDetailVOS){
            Product product = productMap.get(orderDetailVO.getOrder().getProductId());
            ProductVO productVO = ProductVO.apply(product);
            orderDetailVO.setProduct(productVO);
        }

        return orderDetailVOS;
    }
}
