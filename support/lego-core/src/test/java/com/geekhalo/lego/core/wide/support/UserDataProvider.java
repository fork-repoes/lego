package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.User;
import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.core.wide.WideOrderItemType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataProvider implements WideItemDataProvider<WideOrderItemType, Long, User> {

    @Override
    public List<User> apply(List<Long> aLong) {
        return  aLong.stream()
                .map(id -> {
                    User user = new User();
                    user.setId(id);
                    user.setName("Name-" + id);
                    user.setUpdateDate(new Date());
                    return user;
                })
                .collect(Collectors.toList());
    }

    @Override
    public WideOrderItemType getSupportType() {
        return WideOrderItemType.USER;
    }
}