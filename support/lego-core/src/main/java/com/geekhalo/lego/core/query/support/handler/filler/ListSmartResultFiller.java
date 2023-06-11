package com.geekhalo.lego.core.query.support.handler.filler;

import com.geekhalo.lego.core.joininmemory.JoinService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order(0)
public class ListSmartResultFiller implements SmartResultFiller<List>{
    @Autowired
    private JoinService joinService;

    @Override
    public List fill(List list) {
        if (CollectionUtils.isNotEmpty(list)){
            this.joinService.joinInMemory(list);
        }
        return list;
    }

    @Override
    public boolean apply(Class resultClass) {
        return List.class.isAssignableFrom(resultClass);
    }
}
