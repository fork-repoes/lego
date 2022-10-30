package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.*;
import com.geekhalo.lego.core.wide.support.BindFromBasedWideWrapperFactory;
import com.geekhalo.lego.core.wide.support.SimpleWideIndexService;
import com.geekhalo.lego.core.wide.support.SimpleWidePatrolService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.List;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class WideOrderConfiguration {

    @Bean
    public WideService<Long, WideOrderType> wideService(
            WideIndexService<Long, WideOrderType> wideIndexService,
            WideOrderPatrolService wideOrderPatrolService){
        WideService wideService = new WideService<>();
        wideService.setIndexService(wideIndexService);
        wideService.setPatrolService(wideOrderPatrolService);
        wideService.init();
        return wideService;
    }

    @Bean
    public WideIndexService<Long, WideOrderType> wideIndexService(
            WideFactory<Long, WideOrder> wideFactory,
            WideWrapperFactory<WideOrder> wideWrapperFactory,
            List<WideItemDataProvider<WideOrderType, ? extends Object, ? extends WideItemData<WideOrderType, ?>>> wideItemProviders,
            WideOrderRepository wideOrderItemCommandRepository){
        SimpleWideIndexService<Long, WideOrder, WideOrderType> simpleWideIndexService = new SimpleWideIndexService();
        simpleWideIndexService.setWideWrapperFactory(wideWrapperFactory);
        simpleWideIndexService.setWideFactory(wideFactory);
        simpleWideIndexService.setWideItemDataProviders(wideItemProviders);
        simpleWideIndexService.setWideCommandRepository(wideOrderItemCommandRepository);
        return simpleWideIndexService;
    }

    @Bean
    public WideOrderPatrolService wideOrderPatrolService(WidePatrolService<Long, WideOrderType> widePatrolService){
        return new WideOrderPatrolService(widePatrolService);
    }

    @Bean
    public WidePatrolService<Long, WideOrderType> widePatrolService(
            WideFactory<Long, WideOrder> wideFactory,
            WideWrapperFactory<WideOrder> wideWrapperFactory,
            List<WideItemDataProvider<WideOrderType, ? extends Object, ? extends WideItemData<WideOrderType, ?>>> wideItemProviders,
            WideOrderRepository wideOrderItemCommandRepository){
        SimpleWidePatrolService<Long, WideOrder, WideOrderType> widePatrolService = new SimpleWidePatrolService();
        widePatrolService.setWideWrapperFactory(wideWrapperFactory);
        widePatrolService.setWideFactory(wideFactory);
        widePatrolService.setWideItemDataProviders(wideItemProviders);
        widePatrolService.setWideCommandRepository(wideOrderItemCommandRepository);
        return widePatrolService;
    }

    @Bean
    public WideWrapperFactory<WideOrder> wideWrapperFactory(){
        return new BindFromBasedWideWrapperFactory<>(new GenericConversionService());
    }

    @Bean
    public WideFactory<Long, WideOrder> wideFactory(){
        return orderId -> new WideOrder(orderId);
    }
}
