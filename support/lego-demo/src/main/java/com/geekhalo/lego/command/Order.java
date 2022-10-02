package com.geekhalo.lego.command;

import com.geekhalo.lego.query.OrderStatus;
import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.product.Product;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.assertj.core.util.Lists;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Entity(name = "CommandOrder")
@Table(name = "command_order")
@Setter(AccessLevel.PRIVATE)
public class Order{
    @Transient
    private final List<Object> events = Lists.newArrayList();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "price")
    private int price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id")
    private OrderAddress address;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = Lists.newArrayList();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<PayRecord> payRecords = Lists.newArrayList();

    public static Order create(CreateOrderContext contextProxy) {
        Order order = new Order();
        order.setUserId(contextProxy.getCommand().getUserId());

        Address address = contextProxy.getAddress();
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setDetail(address.getDetail());
        order.setAddress(orderAddress);

        List<Product> products = contextProxy.getProducts();
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        List<ProductForBuy> productForBuys = contextProxy.getCommand().getProducts();
        productForBuys.stream()
                .map(productForBuy -> {
                    Product product = productMap.get(productForBuy.getProductId());
                    return OrderItem.create(product, productForBuy.getAmount());
                }).forEach(orderItem -> order.addOrderItem(orderItem));

        order.init();

        OrderCreatedEvent event = new OrderCreatedEvent(order);
        order.events.add(event);
        return order;
    }

    private void init() {
        setStatus(OrderStatus.NONE);
    }

    private void addOrderItem(OrderItem orderItem) {
        this.price = this.price + orderItem.getRealPrice();
        this.items.add(orderItem);
    }


    public void consumeAndClean(Consumer<Object> eventListener){
        this.events.forEach(eventListener);
        this.events.clear();
    }

    public void paySuccess(String chanel, Long price) {
        PayRecord payRecord = PayRecord.create(chanel, price);
        this.payRecords.add(payRecord);

        this.setStatus(OrderStatus.PAID);

        OrderPaySuccessEvent event = new OrderPaySuccessEvent(this);
        this.events.add(event);
    }
}
