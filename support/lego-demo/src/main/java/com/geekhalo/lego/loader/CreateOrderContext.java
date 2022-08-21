package com.geekhalo.lego.loader;

/**
 * Created by taoli on 2022/8/20.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CreateOrderContext {
    com.geekhalo.lego.service.user.User getUser();

    com.geekhalo.lego.service.product.Product getProduct();

    com.geekhalo.lego.service.address.Address getDefAddress();

    com.geekhalo.lego.service.stock.Stock getStock();

    com.geekhalo.lego.service.price.Price getPrice();

    CreateOrderCmd getCmd();
}
