package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.*;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/10/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
@Setter(AccessLevel.PUBLIC)
public class SimpleWideIndexService<MASTER_ID,
        MASTER_DATA extends WideMasterData,
        WIDE extends Wide,
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemTypes<ITEM_TYPE>>
        implements WideIndexService<MASTER_ID, MASTER_DATA, WIDE, ITEM_TYPE> {
    private WideMasterDataProvider<MASTER_ID, MASTER_DATA> wideMasterDataProvider;

    private WideFactory<MASTER_DATA, WIDE> wideFactory;

    private WideWrapperFactory<WIDE> wideWrapperFactory;

    private List<WideItemBinder<WIDE>> itemBinders;

    private List<WideItemDataProvider<ITEM_TYPE, ? extends Object, ? extends WideItemData<ITEM_TYPE>>> wideItemDataProviders;

    private List<WideItemUpdater<ITEM_TYPE,? super WideItemData<ITEM_TYPE>, WIDE>> itemUpdaters;

    private WideCommandRepository<WIDE> wideCommandRepository;

    @Override
    public void index(MASTER_ID id) {
        if (id == null){
            return;
        }
        index(Collections.singletonList(id));
    }

    @Override
    public void index(List<MASTER_ID> ids) {
        List<MASTER_DATA> masterDatas = this.wideMasterDataProvider.apply(ids);
        indexMasterData(masterDatas);
    }

    @Override
    public void indexMasterData(MASTER_DATA data) {
        if (data == null){
            log.warn("index data is null");
            return;
        }
        indexMasterData(Collections.singletonList(data));
    }

    @Override
    public void indexMasterData(List<MASTER_DATA> datas) {
        List<WIDE> wides = createWides(datas);
        List<WideWrapper<WIDE>> wideWrappers = createWideWrappers(wides);
        bindData(datas, wideWrappers);
        bindItems(wideWrappers);
        batchSave(wides);
    }

    @Override
    public <KEY> void updateItem(ITEM_TYPE itemType, KEY key) {
        WideItemDataProvider<ITEM_TYPE, Object, ? extends WideItemData<ITEM_TYPE>> wideItemProvider = findWideItemDataProvider(itemType);
        if (wideItemProvider != null){
            log.warn("failed to find data provider for {}", itemType);
            return;
        }
        WideItemData<ITEM_TYPE> wideItem = wideItemProvider.apply(key);
        if (wideItem == null){
            log.warn("failed to create item for {} use {}", key, wideItemProvider);
            return;
        }
        updateItemData(wideItem);
    }

    private WideItemDataProvider<ITEM_TYPE, Object, ? extends WideItemData<ITEM_TYPE>> findWideItemDataProvider(ITEM_TYPE itemType) {
        WideItemDataProvider<ITEM_TYPE, Object, ? extends WideItemData<ITEM_TYPE>> wideItemProvider =
                (WideItemDataProvider<ITEM_TYPE, Object, ? extends WideItemData<ITEM_TYPE>>)this.wideItemDataProviders.stream()
                .filter(wideItemDataProvider -> wideItemDataProvider.support(itemType))
                .findFirst()
                .orElse(null);
        return wideItemProvider;
    }

    private void bindData(List<MASTER_DATA> datas, List<WideWrapper<WIDE>> wideWrappers) {
        if (datas.size() == wideWrappers.size()){
            for (int i = 0; i< datas.size(); i++){
                WideWrapper<WIDE> wideWrapper = wideWrappers.get(i);
                MASTER_DATA m = datas.get(i);
                wideWrapper.bindItem(m);
            }
        }
    }

    private List<WIDE> createWides(List<MASTER_DATA> datas) {
        return datas.stream()
                .filter(Objects::nonNull)
                .map(data -> this.wideFactory.create(data))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void batchSave(List<WIDE> wides) {
        List<WIDE> collect = wides.stream()
                .filter(wide -> wide.isValidate())
                .collect(Collectors.toList());
        this.wideCommandRepository.save(collect);
    }

    private void bindItems(List<WideWrapper<WIDE>> wides) {
        this.itemBinders.forEach(binder -> binder.bindFor(wides));
    }

    private List<WideWrapper<WIDE>> createWideWrappers(List<WIDE> datas) {
        return datas.stream()
                .filter(Objects::nonNull)
                .map(wide -> this.wideWrapperFactory.createForWide(wide))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public <I extends WideItemData> void updateItemData(WideItemData<ITEM_TYPE> item) {
        WideItemUpdater<ITEM_TYPE, ? super WideItemData<ITEM_TYPE>, WIDE> updater = findWideUpdaterFor(item);
        if (updater == null){
            log.warn("failed to find updater for {}", item);
            return;
        }
        updater.updateItem(item, this.wideCommandRepository);
    }

    private WideItemUpdater<ITEM_TYPE, ? super WideItemData<ITEM_TYPE>, WIDE> findWideUpdaterFor(WideItemData<ITEM_TYPE> item) {
        return this.itemUpdaters.stream()
                .filter(updater -> updater.support(item.getItemType()))
                .findFirst()
                .orElse(null);
    }
}
