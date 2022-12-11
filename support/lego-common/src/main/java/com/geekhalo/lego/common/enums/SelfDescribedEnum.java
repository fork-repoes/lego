package com.geekhalo.lego.common.enums;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SelfDescribedEnum {
    default String getName(){
        return name();
    }

    String name();

    String getDescription();
}
