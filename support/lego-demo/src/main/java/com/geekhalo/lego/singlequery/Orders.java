package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.core.singlequery.OrderField;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public enum Orders implements OrderField {
    ID("id");

    Orders(String name) {
        this.name = name;
    }

    private final String name;
    @Override
    public String getColumnName() {
        return name;
    }
}
