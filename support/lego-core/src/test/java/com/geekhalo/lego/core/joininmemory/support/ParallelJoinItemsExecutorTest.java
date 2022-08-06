package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemsExecutor;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.geekhalo.lego.core.TimeUtils.sleepAsMS;
import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class ParallelJoinItemsExecutorTest {
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
                .foundCallback((orderDetail, user) -> orderDetail.setUser(user))
                .joinDataLoader(userIds -> createUser(userIds))
                .runLevel(1)
                .build();

        JoinItemExecutor<OrderDetail> productJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, Product, Product>builder()
                .name("Product")
                .keyFromSourceData(order->order.getOrder().getProductId())
                .keyFromJoinData(product -> product.getId())
                .joinDataConverter(product -> product)
                .foundCallback((orderDetail, product) -> orderDetail.setProduct(product))
                .joinDataLoader(productIds -> createProduct(productIds))
                .runLevel(1)
                .build();

        JoinItemExecutor<OrderDetail> shopJoinItemExecutor = JoinItemExecutorAdapter.<OrderDetail, Long, Shop, Shop>builder()
                .name("Shop")
                .keyFromSourceData(orderDetail -> orderDetail.getProduct().getShopId())
                .keyFromJoinData(shop -> shop.getId())
                .joinDataConverter(shop -> shop)
                .foundCallback((orderDetail, shop) -> orderDetail.setShop(shop))
                .joinDataLoader(shopIds -> createShop(shopIds))
                .runLevel(3)
                .build();

        this.joinItemsExecutor = new ParallelJoinItemsExecutor(OrderDetail.class,
                Arrays.asList(userJoinItemExecutor, productJoinItemExecutor, shopJoinItemExecutor),
                Executors.newFixedThreadPool(2));
    }

    private List<Product> createProduct(List<Long> productIds) {
        sleepAsMS(1000L);
        return this.productRepository.getByIds(productIds);
    }

    private List<User> createUser(List<Long> userIds) {
        sleepAsMS(1000L);
        return this.userRepository.getByIds(userIds);
    }

    private List<Shop> createShop(List<Long> shopIds){
        sleepAsMS(1000L);
        return this.shopRepository.getByIds(shopIds);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    void execute() {
        List<OrderDetail> orderDetails = this.orderRepository.getRandom().stream()
                .map(OrderDetail::new)
                .collect(toList());

        this.joinItemsExecutor.execute(orderDetails);

        orderDetails.forEach(orderDetail -> {
            Assertions.assertNotNull(orderDetail);
            Assertions.assertNotNull(orderDetail.getOrder());
            Assertions.assertNotNull(orderDetail.getUser());
            Assertions.assertNotNull(orderDetail.getProduct());
            Assertions.assertNotNull(orderDetail.getShop());
        });
    }}