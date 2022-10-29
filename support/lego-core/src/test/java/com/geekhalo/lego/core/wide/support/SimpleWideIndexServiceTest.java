package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.*;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/10/28.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SimpleWideIndexServiceTest {
    private WideOrderItemCommandRepository wideOrderItemCommandRepository;
    private SimpleWideIndexService<Long, OrderItem, WideOrderItem, WideOrderItemType> simpleWideIndexService;

    @BeforeEach
    void setUp() {
        BindFromBasedWideWrapperFactory factory = new BindFromBasedWideWrapperFactory<>(new GenericConversionService());
        this.simpleWideIndexService = new SimpleWideIndexService();
        this.simpleWideIndexService.setWideWrapperFactory(factory);
        this.simpleWideIndexService.setWideFactory(orderItem -> new WideOrderItem());
        this.simpleWideIndexService.setItemBinders(Arrays.asList(new UserBinder(), new ProductBinder()));
        this.simpleWideIndexService.setItemUpdaters(Arrays.asList());
        this.simpleWideIndexService.setWideItemDataProviders(Arrays.asList(new OrderItemDataProvider(), new UserDataProvider(), new ProductDataProvider()));
        this.simpleWideIndexService.setWideMasterDataProvider(new OrderItemProvider());
        this.wideOrderItemCommandRepository = new WideOrderItemCommandRepository();
        this.simpleWideIndexService.setWideCommandRepository(this.wideOrderItemCommandRepository);
    }

    @Test
    void index() {


        this.simpleWideIndexService.index(1L);

        List<WideOrderItem> wideOrderItems = this.wideOrderItemCommandRepository.getWideOrderItems();
        Assertions.assertEquals(1, wideOrderItems.size());

        WideOrderItem wideOrderItem = wideOrderItems.get(0);
        Assertions.assertEquals(1L, wideOrderItem.getOrderId());
        Assertions.assertEquals(1, wideOrderItem.getOrderStatus());
        Assertions.assertEquals(2L, wideOrderItem.getUserId());
        Assertions.assertEquals("Name-" + 2L, wideOrderItem.getUserName());
        Assertions.assertEquals(3L, wideOrderItem.getProductId());
        Assertions.assertEquals("Product-" + 3L, wideOrderItem.getProductName());
    }

    @Test
    void testIndex() {
        for (int i=0; i< 10; i++) {
            this.simpleWideIndexService.index(i + 1L);
        }

        List<WideOrderItem> wideOrderItems = this.wideOrderItemCommandRepository.getWideOrderItems();
        Assertions.assertEquals(10, wideOrderItems.size());

        for (int i=0; i< 10; i++) {
            WideOrderItem wideOrderItem = wideOrderItems.get(i);
            Assertions.assertEquals(1L + i, wideOrderItem.getOrderId());
            Assertions.assertEquals(1, wideOrderItem.getOrderStatus());
            Assertions.assertEquals(2L + i, wideOrderItem.getUserId());
            Assertions.assertEquals("Name-" + (i + 2L), wideOrderItem.getUserName());
            Assertions.assertEquals(3L + i, wideOrderItem.getProductId());
            Assertions.assertEquals("Product-" + (i + 3L), wideOrderItem.getProductName());
        }
    }

    @Test
    void updateItem() {
        this.simpleWideIndexService.index(1L);

        List<WideOrderItem> wideOrderItems = this.wideOrderItemCommandRepository.getWideOrderItems();
        Assertions.assertEquals(1, wideOrderItems.size());

        this.simpleWideIndexService.updateItem(WideOrderItemType.USER, 1L);
    }


    class UserBinder implements WideItemBinder<WideOrderItem>{
        @Override
        public void bindFor(List<WideWrapper<WideOrderItem>> wideWrappers) {
            for (WideWrapper<WideOrderItem> wideWrapper : wideWrappers){
                WideOrderItem target = wideWrapper.getTarget();
                Long userId = target.getUserId();
                User user = createUser(userId);
                wideWrapper.bindItem(user);
            }
        }
    }

    private User createUser(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("Name-" + userId);
        return user;
    }

    class ProductBinder implements WideItemBinder<WideOrderItem>{

        @Override
        public void bindFor(List<WideWrapper<WideOrderItem>> wideWrappers) {
            for (WideWrapper<WideOrderItem> wideWrapper : wideWrappers){
                WideOrderItem target = wideWrapper.getTarget();
                Long productId = target.getProductId();
                Product product = createProduct(productId);
                wideWrapper.bindItem(product);
            }
        }
    }

    private Product createProduct(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setName("Product-" + productId);
        return product;
    }

    @Getter
    class WideOrderItemCommandRepository implements WideCommandRepository<WideOrderItem>{
        private final List<WideOrderItem> wideOrderItems = Lists.newArrayList();

        @Override
        public void save(List<WideOrderItem> wides) {
            this.wideOrderItems.addAll(wides);
        }
    }

    class OrderItemProvider implements WideMasterDataProvider<Long, OrderItem>{

        @Override
        public List<OrderItem> apply(List<Long> ids) {
            return ids.stream()
                    .map(id->{
                        OrderItem orderItem = createOrderItem(id);
                        return orderItem;
                    }).collect(Collectors.toList());
        }
    }

    private OrderItem createOrderItem(Long id) {
        long i = id -1;
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L + i);
        orderItem.setUserId(2L + i);
        orderItem.setProductId(3L + i);
        orderItem.setStatus(1);
        return orderItem;
    }

    class UserDataProvider implements WideItemDataProvider<WideOrderItemType, Long, User>{

        @Override
        public boolean support(WideOrderItemType wideOrderItemType) {
            return wideOrderItemType == WideOrderItemType.USER;
        }

        @Override
        public User apply(Long aLong) {
            return createUser(aLong);
        }
    }

    class ProductDataProvider implements WideItemDataProvider<WideOrderItemType, Long, Product>{

        @Override
        public boolean support(WideOrderItemType wideOrderItemType) {
            return wideOrderItemType == WideOrderItemType.PRODUCT;
        }

        @Override
        public Product apply(Long aLong) {
            return createProduct(aLong);
        }
    }

    class OrderItemDataProvider implements WideItemDataProvider<WideOrderItemType, Long, OrderItem>{

        @Override
        public boolean support(WideOrderItemType wideOrderItemType) {
            return wideOrderItemType == WideOrderItemType.ORDER_ITEM;
        }

        @Override
        public OrderItem apply(Long aLong) {
            return createOrderItem(aLong);
        }
    }
}