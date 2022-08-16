package com.geekhalo.lego.loader;

import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.price.Price;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.stock.LazyLoadStockByProductId;
import com.geekhalo.lego.service.stock.Stock;
import com.geekhalo.lego.service.user.User;
import lombok.Data;

@Data
public class CreateOrderContext {

    private CreateOrderCmd cmd;

    @LazyLoadUserById(userId = "cmd.userId")
    private User user;

    @LazyLoadProduceById(productId = "cmd.productId")
    private Product product;

    @LazyLoadDefaultAddressByUserId(userId = "user.id")
    private Address defAddress;

    @LazyLoadStockByProductId(productId = "product.id")
    private Stock stock;

    @LazyLoadPriceByUserAndProduct(userId = "user.id",
            productId = "product.id")
    private Price price;
}
