package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.WideCommandRepository;
import com.geekhalo.lego.core.wide.WideItemData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class WideOrderRepository implements WideCommandRepository<Long, WideOrderType, WideOrder> {
    @Autowired
    private WideOrderDao wideOrderDao;

    @Override
    public void save(List<WideOrder> wides) {
        wideOrderDao.saveAll(wides);
    }

    @Override
    public List<WideOrder> findByIds(List<Long> masterIds) {
        return wideOrderDao.findAllById(masterIds);
    }

    @Override
    public <KEY> void consumeByItem(WideOrderType wideOrderType, KEY key, Consumer<WideOrder> wideConsumer) {
        switch (wideOrderType){
            case PRODUCT:
                this.wideOrderDao.findByProductId((Long) key).forEach(wideConsumer);
            case ADDRESS:
                this.wideOrderDao.findByAddressId((Long) key).forEach(wideConsumer);
            case ORDER:
                this.wideOrderDao.findById((Long) key).ifPresent(wideConsumer);
            case USER:
                this.wideOrderDao.findByUserId((Long) key).forEach(wideConsumer);
        }
    }

    @Override
    public boolean supportUpdateFor(WideOrderType wideOrderType) {
        return false;
    }

    @Override
    public <KEY> void updateByItem(WideOrderType wideOrderType, KEY key, Consumer<WideOrder> wideConsumer) {
        switch (wideOrderType){
            case PRODUCT:
                this.wideOrderDao.findByProductId((Long) key).forEach(wideConsumer);
            case ADDRESS:
                this.wideOrderDao.findByAddressId((Long) key).forEach(wideConsumer);
            case ORDER:
                this.wideOrderDao.findById((Long) key).ifPresent(wideConsumer);
            case USER:
                this.wideOrderDao.findByUserId((Long) key).forEach(wideConsumer);
        }
    }

    @Override
    public <KEY> void updateByItem(WideOrderType wideOrderType, KEY key, WideItemData<WideOrderType, ?> item) {

    }
}
