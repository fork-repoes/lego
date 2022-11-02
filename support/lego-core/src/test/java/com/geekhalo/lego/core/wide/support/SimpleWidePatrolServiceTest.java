package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.*;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SimpleWidePatrolServiceTest {
    private WideOrderItemCommandRepository wideOrderItemCommandRepository;
    private ReindexConsumer reindexConsumer;
    private SimpleWidePatrolService<Long, WideOrderItem, WideOrderItemType> widePatrolService;
    private SimpleWideIndexService<Long, WideOrderItem, WideOrderItemType> simpleWideIndexService;

    @BeforeEach
    void setUp() {
        this.wideOrderItemCommandRepository = new WideOrderItemCommandRepository();
        this.reindexConsumer = new ReindexConsumer();
        WideWrapperFactory<WideOrderItem> wideWrapperFactory = new BindFromBasedWideWrapperFactory<>(new GenericConversionService());
        WideFactory<Long, WideOrderItem> wideFactory = orderId -> new WideOrderItem(orderId);
        List<WideItemDataProvider<WideOrderItemType, ? extends Object, ? extends WideItemData<WideOrderItemType, ?>>> wideItemDataProviders =
                Arrays.asList(new OrderItemDataProvider(), new UserDataProvider(), new ProductDataProvider());
        {
            this.widePatrolService = new SimpleWidePatrolService();
            this.widePatrolService.setWideWrapperFactory(wideWrapperFactory);
            this.widePatrolService.setWideFactory(wideFactory);
            this.widePatrolService.setWideItemDataProviders(wideItemDataProviders);
            this.widePatrolService.setWideCommandRepository(this.wideOrderItemCommandRepository);
            this.widePatrolService.setReindexConsumer(reindexConsumer);
        }

        {
            this.simpleWideIndexService = new SimpleWideIndexService<>();
            this.simpleWideIndexService.setWideWrapperFactory(wideWrapperFactory);
            this.simpleWideIndexService.setWideFactory(wideFactory);
            this.simpleWideIndexService.setWideItemDataProviders(wideItemDataProviders);
            this.simpleWideIndexService.setWideCommandRepository(this.wideOrderItemCommandRepository);
        }
    }

    @Test
    void index() throws Exception{
        {
            this.reindexConsumer.clean();
            this.widePatrolService.index(1L);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(this.reindexConsumer.ids));
        }


        {
            this.reindexConsumer.clean();
            this.simpleWideIndexService.index(1L);
            TimeUnit.SECONDS.sleep(1);
            this.widePatrolService.index(1L);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(this.reindexConsumer.ids));
        }

    }

    @Test
    void testIndex() {
    }

    @Test
    void updateItem() throws Exception{
        {
            this.reindexConsumer.clean();
            this.widePatrolService.updateItem(WideOrderItemType.USER, 2L);
            Assertions.assertTrue(CollectionUtils.isEmpty(this.reindexConsumer.ids));
        }


        {
            this.reindexConsumer.clean();
            this.simpleWideIndexService.index(1L);
            TimeUnit.SECONDS.sleep(1);
            this.widePatrolService.updateItem(WideOrderItemType.USER, 2L);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(this.reindexConsumer.ids));
        }
    }

    private class ReindexConsumer implements Consumer<List<Long>>{
        private final List<Long> ids = Lists.newArrayList();
        @Override
        public void accept(List<Long> longList) {
            this.ids.addAll(longList);
        }

        public void clean(){
            this.ids.clear();
        }
    }
}