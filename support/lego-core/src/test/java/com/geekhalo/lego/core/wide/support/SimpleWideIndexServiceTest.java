package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.*;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/10/28.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SimpleWideIndexServiceTest {
    private WideOrderItemCommandRepository wideOrderItemCommandRepository;
    private SimpleWideIndexService<Long, WideOrderItem, WideOrderItemType> simpleWideIndexService;

    @BeforeEach
    void setUp() {
        this.simpleWideIndexService = new SimpleWideIndexService();
        this.simpleWideIndexService.setWideWrapperFactory(new BindFromBasedWideWrapperFactory<>(new GenericConversionService()));
        this.simpleWideIndexService.setWideFactory(orderId -> new WideOrderItem(orderId));
        this.simpleWideIndexService.setWideItemDataProviders(Arrays.asList(new OrderItemDataProvider(), new UserDataProvider(), new ProductDataProvider()));
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
    void updateItem() throws Exception{
        this.simpleWideIndexService.index(1L);

        List<WideOrderItem> wideOrderItems = this.wideOrderItemCommandRepository.getWideOrderItems();
        Assertions.assertEquals(1, wideOrderItems.size());
        WideOrderItem wideOrderItem = wideOrderItems.get(0);
        Date itemUpdate = wideOrderItem.getItemUpdateDate();
        Date userUpdate = wideOrderItem.getUserUpdateDate();
        Date productUpdate = wideOrderItem.getProductUpdateDate();

        {
            TimeUnit.SECONDS.sleep(1);
            this.simpleWideIndexService.updateItem(WideOrderItemType.ORDER_ITEM, 1L);

            List<WideOrderItem> wideOrderItemsAfterUpdateItem = this.wideOrderItemCommandRepository.getWideOrderItems();
            Assertions.assertEquals(1, wideOrderItemsAfterUpdateItem.size());
            WideOrderItem wideOrderItem1 = wideOrderItemsAfterUpdateItem.get(0);
            Assertions.assertNotEquals(itemUpdate, wideOrderItem1.getItemUpdateDate());
        }

        {
            TimeUnit.SECONDS.sleep(1);
            this.simpleWideIndexService.updateItem(WideOrderItemType.USER, 2L);

            List<WideOrderItem> wideOrderItemsAfterUpdateUser = this.wideOrderItemCommandRepository.getWideOrderItems();
            Assertions.assertEquals(1, wideOrderItemsAfterUpdateUser.size());

            WideOrderItem wideOrderItem1 = wideOrderItemsAfterUpdateUser.get(0);
            Assertions.assertNotEquals(userUpdate, wideOrderItem1.getUserUpdateDate());
        }

        {
            TimeUnit.SECONDS.sleep(1);
            this.simpleWideIndexService.updateItem(WideOrderItemType.PRODUCT, 3L);

            List<WideOrderItem> wideOrderItemsAfterUpdateProduct = this.wideOrderItemCommandRepository.getWideOrderItems();
            Assertions.assertEquals(1, wideOrderItemsAfterUpdateProduct.size());

            WideOrderItem wideOrderItem1 = wideOrderItemsAfterUpdateProduct.get(0);
            Assertions.assertNotEquals(productUpdate, wideOrderItem1.getProductUpdateDate());
        }

    }
}