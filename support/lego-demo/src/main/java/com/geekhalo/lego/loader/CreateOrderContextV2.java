package com.geekhalo.lego.loader;

import com.geekhalo.lego.annotation.loader.LazyLoadBy;
import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.price.Price;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.stock.Stock;
import com.geekhalo.lego.service.user.User;
import lombok.Data;

@Data
public class CreateOrderContextV2 implements CreateOrderContext{

    private CreateOrderCmd cmd;

    @LazyLoadBy("#{@userRepository.getById(cmd.userId)}")
    private User user;

    @LazyLoadBy("#{@productRepository.getById(cmd.productId)}")
    private Product product;

    @LazyLoadBy("#{@addressRepository.getDefaultAddressByUserId(user.id)}")
    private Address defAddress;

    @LazyLoadBy("#{@stockRepository.getByProductId(product.id)}")
    private Stock stock;

    @LazyLoadBy("#{@priceService.getByUserAndProduct(user.id, product.id)}")
    private Price price;
}
