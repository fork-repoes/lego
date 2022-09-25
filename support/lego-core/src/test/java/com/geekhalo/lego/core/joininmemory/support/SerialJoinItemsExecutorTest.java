package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemsExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SerialJoinItemsExecutorTest {
    private OrderRepository orderRepository = new OrderRepository();
    private ProductRepository productRepository = new ProductRepository();
    private ShopRepository shopRepository = new ShopRepository();
    private UserRepository userRepository = new UserRepository();

    private JoinItemsExecutor<OrderDetail> joinItemsExecutor;

    @BeforeEach
    void setUp() {
        JoinItemExecutor<OrderDetail> userJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, User, User>builder()
                .name("User")
                .keyFromSourceData(order->order.getOrder().getUserId())
                .keyFromJoinData(user -> user.getId())
                .joinDataConverter(user -> user)
                .foundCallback((orderDetail, user) -> orderDetail.setUser(user.get(0)))
                .joinDataLoader(userIds -> this.userRepository.getByIds(userIds))
                .runLevel(1)
                .build();

        JoinItemExecutor<OrderDetail> productJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, Product, Product>builder()
                .name("Product")
                .keyFromSourceData(order->order.getOrder().getProductId())
                .keyFromJoinData(product -> product.getId())
                .joinDataConverter(product -> product)
                .foundCallback((orderDetail, product) -> orderDetail.setProduct(product.get(0)))
                .joinDataLoader(productIds -> this.productRepository.getByIds(productIds))
                .runLevel(1)
                .build();

        JoinItemExecutor<OrderDetail> shopJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, Shop, Shop>builder()
                .name("Shop")
                .keyFromSourceData(orderDetail -> orderDetail.getProduct().getShopId())
                .keyFromJoinData(shop -> shop.getId())
                .joinDataConverter(shop -> shop)
                .foundCallback((orderDetail, shop) -> orderDetail.setShop(shop.get(0)))
                .joinDataLoader(shopIds -> this.shopRepository.getByIds(shopIds))
                .runLevel(3)
                .build();

        this.joinItemsExecutor = new SerialJoinItemsExecutor<>(OrderDetail.class,
                Arrays.asList(userJoinItemExecutor, productJoinItemExecutor, shopJoinItemExecutor));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {
        List<OrderDetail> orderDetails = this.orderRepository.getRandom()
                .stream()
                .map(OrderDetail::new)
                .collect(Collectors.toList());

        this.joinItemsExecutor.execute(orderDetails);

        orderDetails.forEach(orderDetail -> {
            Assertions.assertNotNull(orderDetail);
            Assertions.assertNotNull(orderDetail.getOrder());
            Assertions.assertNotNull(orderDetail.getUser());
            Assertions.assertNotNull(orderDetail.getProduct());
            Assertions.assertNotNull(orderDetail.getShop());
        });
    }
}