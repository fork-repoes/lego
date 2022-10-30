package com.geekhalo.lego.wide;

import com.geekhalo.lego.annotation.wide.BindFrom;
import com.geekhalo.lego.core.wide.WideItemKey;
import com.geekhalo.lego.core.wide.support.BindFromBasedWide;
import com.geekhalo.lego.service.address.Address;
import com.geekhalo.lego.service.order.Order;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Entity
@Table(name = "t_wide_order")
@Data
public class WideOrder extends BindFromBasedWide<Long, WideOrderType> {
    @Id
    private Long id;

    @BindFrom(sourceClass = Order.class, field = "userId")
    private Long userId;
    @BindFrom(sourceClass = Order.class, field = "addressId")
    private Long addressId;
    @BindFrom(sourceClass = Order.class, field = "productId")
    private Long productId;

    @BindFrom(sourceClass = User.class, field = "name")
    private String userName;

    @BindFrom(sourceClass = Address.class, field = "detail")
    private String addressDetail;

    @BindFrom(sourceClass = Product.class, field = "name")
    private String productName;
    @BindFrom(sourceClass = Product.class, field = "price")
    private Integer productPrice;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isValidate() {
        return userId != null && addressId != null && productId != null;
    }

    @Override
    public List<WideItemKey> getItemsKeyByType(WideOrderType wideOrderType) {
        switch (wideOrderType){
            case ORDER:
                return Collections.singletonList(new WideItemKey(wideOrderType, getId()));
            case USER:
                return Collections.singletonList(new WideItemKey(wideOrderType, getUserId()));
            case ADDRESS:
                return Collections.singletonList(new WideItemKey(wideOrderType, getAddressId()));
            case PRODUCT:
                return Collections.singletonList(new WideItemKey(wideOrderType, getProductId()));
        }

        return Collections.emptyList();
    }
}
