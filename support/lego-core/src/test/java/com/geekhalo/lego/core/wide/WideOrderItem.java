package com.geekhalo.lego.core.wide;

import com.geekhalo.lego.annotation.wide.BindFrom;
import com.geekhalo.lego.core.wide.support.BindFromBasedWide;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Data
@NoArgsConstructor
public class WideOrderItem
        extends BindFromBasedWide<Long, WideOrderItemType>
        implements Wide<Long, WideOrderItemType>{

    @BindFrom(sourceClass = OrderItem.class, field = "id")
    private Long orderId;

    @BindFrom(sourceClass = OrderItem.class, field = "status")
    private Integer orderStatus;

    @BindFrom(sourceClass = OrderItem.class, field = "updateDate")
    private Date itemUpdateDate;

    @BindFrom(sourceClass = OrderItem.class, field = "userId")
    private Long userId;

    @BindFrom(sourceClass = User.class, field = "name")
    private String userName;

    @BindFrom(sourceClass = User.class, field = "updateDate")
    private Date userUpdateDate;

    @BindFrom(sourceClass = OrderItem.class, field = "productId")
    private Long productId;

    @BindFrom(sourceClass = Product.class, field = "name")
    private String productName;

    @BindFrom(sourceClass = Product.class, field = "updateDate")
    private Date productUpdateDate;

    public WideOrderItem(Long orderId){
        this.orderId = orderId;
    }

    @Override
    public Long getId() {
        return this.orderId;
    }

    @Override
    public boolean isValidate() {
        return orderId != null && orderStatus != null && userId != null && productId != null;
    }

    @Override
    public List<WideItemKey> getItemsKeyByType(WideOrderItemType wideOrderItemType) {
        switch (wideOrderItemType) {
            case ORDER_ITEM:
                return Collections.singletonList(new WideItemKey(wideOrderItemType, getOrderId()));
            case USER:
                return Collections.singletonList(new WideItemKey(wideOrderItemType, getUserId()));
            case PRODUCT:
                return Collections.singletonList(new WideItemKey(wideOrderItemType, getProductId()));
        }
        return Collections.emptyList();
    }

}
