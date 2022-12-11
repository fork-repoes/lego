package com.geekhalo.lego.core.enums.repository.jpa;

import com.geekhalo.lego.common.enums.CommonEnum;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class CommonEnumAttributeConverter<E extends Enum<E> & CommonEnum>
        implements AttributeConverter<E, Integer> {
    private final List<E> commonEnums;

    public CommonEnumAttributeConverter(E[] commonEnums){
        this(Arrays.asList(commonEnums));
    }

    public CommonEnumAttributeConverter(List<E> commonEnums) {
        this.commonEnums = commonEnums;
    }

    @Override
    public Integer convertToDatabaseColumn(E e) {
        return e.getCode();
    }

    @Override
    public E convertToEntityAttribute(Integer code) {
        return (E) commonEnums.stream()
                .filter(commonEnum -> commonEnum.match(String.valueOf(code)))
                .findFirst()
                .orElse(null);
    }
}
