package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class ProductProvider implements WideItemDataProvider<WideOrderType, Long, Product> {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> apply(List<Long> key) {
        return productRepository.getByIds(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.PRODUCT;
    }
}
