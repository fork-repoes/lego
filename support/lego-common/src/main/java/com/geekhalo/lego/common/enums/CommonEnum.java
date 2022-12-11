package com.geekhalo.lego.common.enums;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CommonEnum extends CodeBasedEnum, SelfDescribedEnum{
    default boolean match(String value){
        if (value == null){
            return false;
        }
        return value.equals(String.valueOf(getCode()))
                || value.equals(getName());
    }
}
