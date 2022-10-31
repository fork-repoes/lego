package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.*;
import com.geekhalo.lego.core.wide.support.BindFromBasedWideWrapperFactory;
import com.geekhalo.lego.core.wide.support.SimpleWideIndexService;
import com.geekhalo.lego.core.wide.support.SimpleWidePatrolService;
import com.geekhalo.lego.starter.wide.WideConfigurationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.List;
import java.util.Optional;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class WideOrderConfiguration extends WideConfigurationSupport<Long, WideOrderType, WideOrder> {

    @Autowired
    private WideOrderRepository wideOrderRepository;

    @Autowired
    private List<WideItemDataProvider<WideOrderType, ?, ? extends WideItemData<WideOrderType, ?>>> wideItemDataProviders;

    @Bean
    public WideIndexService<Long, WideOrderType> createWideIndexService(){
        return super.createWideIndexService();
    }

    @Bean
    public WideOrderPatrolService wideOrderPatrolService(){
        return new WideOrderPatrolService(createWidePatrolService());
    }

    @Bean
    protected WideService<Long, WideOrderType> createWideService(
            WideIndexService<Long, WideOrderType> wideIndexService,
            WideOrderPatrolService wideOrderPatrolService){
        return super.createWideService(wideIndexService, wideOrderPatrolService);
    }


    @Override
    protected WideFactory<Long, WideOrder> getWideFactory() {
        return WideOrder::new;
    }

    @Override
    protected WideCommandRepository<Long, WideOrderType, WideOrder> getWideCommandRepository() {
        return this.wideOrderRepository;
    }

    @Override
    protected List<WideItemDataProvider<WideOrderType, ?, ? extends WideItemData<WideOrderType, ?>>> getWideItemProviders() {
        return this.wideItemDataProviders;
    }
}
