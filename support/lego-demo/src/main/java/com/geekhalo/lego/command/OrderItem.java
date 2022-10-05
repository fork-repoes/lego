package com.geekhalo.lego.command;

import com.geekhalo.lego.service.product.Product;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Entity(name = "CommandOrderItem")
@Table(name = "command_order_item")
@Setter(AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    public static OrderItem create(Product product, Integer amount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductName(product.getName());
        orderItem.setPrice(product.getPrice());
        orderItem.setAmount(amount);
        return orderItem;
    }

    public int getRealPrice() {
        return price * amount;
    }
}
