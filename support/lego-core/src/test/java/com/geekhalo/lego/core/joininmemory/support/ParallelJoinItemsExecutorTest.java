package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemsExecutor;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.geekhalo.lego.core.TimeUtils.sleepAsMS;
import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class ParallelJoinItemsExecutorTest {
    private JoinItemsExecutor<OrderDetail> joinItemsExecutor;

    @BeforeEach
    void setUp() {
        JoinItemExecutor<OrderDetail> userJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, User, User>builder()
                .name("User")
                .keyGeneratorFromData(order->order.getOrder().getUserId())
                .keyGeneratorFromJoinData(user -> user.getId())
                .dataConverter(user -> user)
                .foundFunction((orderDetail, user) -> orderDetail.setUser(user))
                .dataLoeader(userIds -> createUser(userIds))
                .runLevel(1)
                .build();

        JoinItemExecutor<OrderDetail> productJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, Product, Product>builder()
                .name("Product")
                .keyGeneratorFromData(order->order.getOrder().getProductId())
                .keyGeneratorFromJoinData(product -> product.getId())
                .dataConverter(product -> product)
                .foundFunction((orderDetail, product) -> orderDetail.setProduct(product))
                .dataLoeader(productIds -> createProduct(productIds))
                .runLevel(1)
                .build();

        JoinItemExecutor<OrderDetail> shopJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, Shop, Shop>builder()
                .name("Shop")
                .keyGeneratorFromData(orderDetail -> orderDetail.getProduct().getShopId())
                .keyGeneratorFromJoinData(shop -> shop.getId())
                .dataConverter(shop -> shop)
                .foundFunction((orderDetail, shop) -> orderDetail.setShop(shop))
                .dataLoeader(shopIds -> createShop(shopIds))
                .runLevel(3)
                .build();

        this.joinItemsExecutor = new ParallelJoinItemsExecutor(OrderDetail.class,
                Arrays.asList(userJoinItemExecutor, productJoinItemExecutor, shopJoinItemExecutor),
                Executors.newFixedThreadPool(2));
    }

    private List<Product> createProduct(List<Long> productIds) {
        sleepAsMS(1000L);
        return productIds.stream()
                .map(id-> Product.builder()
                        .id(id)
                        .shopId(id % 3)
                        .name("Product-" + id)
                        .build()
                ).collect(Collectors.toList());
    }

    private List<User> createUser(List<Long> userIds) {
        sleepAsMS(1000L);
        return userIds.stream()
                .map(id-> User.builder()
                        .id(id)
                        .name("Name-" + id)
                        .build()
                ).collect(toList());
    }

    private List<Shop> createShop(List<Long> shopIds){
        sleepAsMS(1000L);
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
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
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
        this.joinItemsExecutor.execute(orderDetails);

        orderDetails.forEach(orderDetail -> {
            Assertions.assertNotNull(orderDetail);
            Assertions.assertNotNull(orderDetail.getOrder());
            Assertions.assertNotNull(orderDetail.getUser());
            Assertions.assertNotNull(orderDetail.getProduct());
            Assertions.assertNotNull(orderDetail.getShop());
        });
    }}