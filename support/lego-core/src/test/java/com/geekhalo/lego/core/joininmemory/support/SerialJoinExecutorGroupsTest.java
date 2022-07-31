package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinExecutor;
import com.geekhalo.lego.core.joininmemory.JoinExecutorGroup;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.geekhalo.lego.core.TimeUtils.sleepAsMS;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SerialJoinExecutorGroupsTest {
    private JoinExecutorGroup<OrderDetail> joinExecutorGroup;

    @BeforeEach
    void setUp() {
        JoinExecutor<OrderDetail> userJoinExecutor = JoinExecutorAdapter.<OrderDetail, Long, User, User>builder()
                .name("User")
                .keyGeneratorFromData(order->order.getOrder().getUserId())
                .keyGeneratorFromJoinData(user -> user.getId())
                .dataConverter(user -> user)
                .foundFunction((orderDetail, user) -> orderDetail.setUser(user))
                .dataLoeader(userIds -> createUser(userIds))
                .runLevel(1)
                .build();

        JoinExecutor<OrderDetail> productJoinExecutor = JoinExecutorAdapter.<OrderDetail, Long, Product, Product>builder()
                .name("Product")
                .keyGeneratorFromData(order->order.getOrder().getProductId())
                .keyGeneratorFromJoinData(product -> product.getId())
                .dataConverter(product -> product)
                .foundFunction((orderDetail, product) -> orderDetail.setProduct(product))
                .dataLoeader(productIds -> createProduct(productIds))
                .runLevel(1)
                .build();

        JoinExecutor<OrderDetail> shopJoinExecutor = JoinExecutorAdapter.<OrderDetail, Long, Shop, Shop>builder()
                .name("Shop")
                .keyGeneratorFromData(orderDetail -> orderDetail.getProduct().getShopId())
                .keyGeneratorFromJoinData(shop -> shop.getId())
                .dataConverter(shop -> shop)
                .foundFunction((orderDetail, shop) -> orderDetail.setShop(shop))
                .dataLoeader(shopIds -> createShop(shopIds))
                .runLevel(3)
                .build();

        this.joinExecutorGroup = new SerialJoinExecutorGroups<>(OrderDetail.class,
                Arrays.asList(userJoinExecutor, productJoinExecutor, shopJoinExecutor));
    }

    private List<Product> createProduct(List<Long> productIds) {
        return productIds.stream()
                .map(id-> Product.builder()
                                .id(id)
                                .shopId(id % 3)
                                .name("Product-" + id)
                        .build()
                ).collect(Collectors.toList());
    }

    private List<User> createUser(List<Long> userIds) {
        return userIds.stream()
                .map(id-> User.builder()
                        .id(id)
                        .name("Name-" + id)
                        .build()
                ).collect(toList());
    }

    private List<Shop> createShop(List<Long> shopIds){

        return shopIds.stream()
                .map(id ->
                        Shop.builder()
                                .id(id)
                                .name("Shop-" + id)
                                .build()
                ).collect(Collectors.toList());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {
        List<OrderDetail> orderDetails = Lists.newArrayListWithCapacity(100);
        for(int i = 0; i< 100; i++){
            Order order = Order.builder()
                    .id(i + 1L)
                    .userId((long)(i % 20))
                    .productId((long) i % 5)
                    .build();
            orderDetails.add(new OrderDetail(order));
        }
        this.joinExecutorGroup.execute(orderDetails);

        orderDetails.forEach(orderDetail -> {
            Assertions.assertNotNull(orderDetail);
            Assertions.assertNotNull(orderDetail.getOrder());
            Assertions.assertNotNull(orderDetail.getUser());
            Assertions.assertNotNull(orderDetail.getProduct());
            Assertions.assertNotNull(orderDetail.getShop());
        });
    }
}