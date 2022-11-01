package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.WideItemType;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public enum WideOrderType implements WideItemType<WideOrderType> {
    ORDER, // 订单主数据
    USER, // 用户数据
    ADDRESS, // 用户地址数据
    PRODUCT // 购买商品数据
}
