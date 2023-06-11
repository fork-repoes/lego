package com.geekhalo.lego.command;

import com.geekhalo.lego.loader.LazyLoadAddressById;
import com.geekhalo.lego.loader.LazyLoadProductsByIds;
import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.product.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class CreateOrderContext{
    private final CreateOrderCommand command;

    @LazyLoadAddressById(id = "command.userAddress")
    private Address address;

    @LazyLoadProductsByIds(productIds = "productIds")
    private List<Product> products;

    public List<Long> getProductIds(){
        return command.getProducts().stream()
                .map(ProductForBuy::getProductId)
                .collect(Collectors.toList());
    }

    public CreateOrderContext(CreateOrderCommand command) {
        this.command = command;
    }
}
