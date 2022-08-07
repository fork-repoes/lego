package com.geekhalo.lego.joininmemory.service.address;

import org.springframework.stereotype.Repository;

import java.util.List;

import static com.geekhalo.lego.util.TimeUtils.sleepAsMS;
import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class AddressRepository {
    public List<Address> getByIds(List<Long> ids){
        sleepAsMS(50);
        return ids.stream()
                .distinct()
                .map(id->createAddress(id))
                .collect(toList());
    }

    public Address getById(Long id){
        sleepAsMS(5);
        return createAddress(id);
    }

    private Address createAddress(Long id){
        return Address.builder()
                .id(id)
                .detail("详细地址-" + id)
                .build();
    }
}
