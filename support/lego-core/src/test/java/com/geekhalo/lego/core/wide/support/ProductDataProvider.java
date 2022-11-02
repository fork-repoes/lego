package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.core.wide.Product;
import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.core.wide.WideOrderItemType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDataProvider implements WideItemDataProvider<WideOrderItemType, Long, Product> {

    @Override
    public List<Product> apply(List<Long> aLong) {
        return aLong.stream()
                .map(id -> {
                    Product product = new Product();
                    product.setId(id);
                    product.setName("Product-" + id);
                    product.setUpdateDate(new Date());
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Override
    public WideOrderItemType getSupportType() {
        return WideOrderItemType.PRODUCT;
    }
}