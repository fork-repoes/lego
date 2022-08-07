package com.geekhalo.lego.joininmemory.web.v3;

import com.geekhalo.lego.joininmemory.service.address.Address;
import com.geekhalo.lego.joininmemory.service.address.AddressRepository;
import com.geekhalo.lego.joininmemory.service.order.Order;
import com.geekhalo.lego.joininmemory.service.order.OrderRepository;
import com.geekhalo.lego.joininmemory.service.product.Product;
import com.geekhalo.lego.joininmemory.service.product.ProductRepository;
import com.geekhalo.lego.joininmemory.service.user.User;
import com.geekhalo.lego.joininmemory.service.user.UserRepository;
import com.geekhalo.lego.joininmemory.web.*;
import com.geekhalo.lego.joininmemory.web.v2.OrderDetailVOV2;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class OrderDetailServiceV3 implements OrderDetailService {
    private ExecutorService executorService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        this.executorService = Executors.newFixedThreadPool(20);
    }

    @SneakyThrows
    @Override
    public List<? extends OrderDetailVO> getByUserId(Long userId) {
        List<Order> orders = this.orderRepository.getByUserId(userId);

        List<OrderDetailVOV2> orderDetailVOS = orders.stream()
                .map(order -> new OrderDetailVOV2(OrderVO.apply(order)))
                .collect(toList());

        List<Callable<Void>> callables = Lists.newArrayListWithCapacity(3);
        callables.add(() -> {
            bindUser(orders, orderDetailVOS);
            return null;
        });

        callables.add(() ->{
            bindAddress(orders, orderDetailVOS);
            return null;
        });

        callables.add(() -> {
            bindProduct(orders, orderDetailVOS);
            return null;
        });
        this.executorService.invokeAll(callables);

        return orderDetailVOS;
    }

    private void bindProduct(List<Order> orders, List<OrderDetailVOV2> orderDetailVOS) {
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
    }

    private void bindAddress(List<Order> orders, List<OrderDetailVOV2> orderDetailVOS) {
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
    }

    private void bindUser(List<Order> orders, List<OrderDetailVOV2> orderDetailVOS) {
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
    }
}
