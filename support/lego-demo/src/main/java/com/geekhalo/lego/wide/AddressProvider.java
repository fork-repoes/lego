package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.wide.jpa.AddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class AddressProvider implements WideItemDataProvider<WideOrderType, Long, Address> {
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> apply(List<Long> key) {
        return addressDao.findAllById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.ADDRESS;
    }
}
