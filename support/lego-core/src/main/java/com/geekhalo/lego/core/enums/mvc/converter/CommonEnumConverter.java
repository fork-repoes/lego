package com.geekhalo.lego.core.enums.mvc.converter;

import com.geekhalo.lego.common.enums.CommonEnum;
import com.geekhalo.lego.core.enums.mvc.CommonEnumRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(1)
@Component
public class CommonEnumConverter implements ConditionalGenericConverter {
    @Autowired
    private CommonEnumRegistry enumRegistry;

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<?> type = targetType.getType();
        return enumRegistry.getClassDict().containsKey(type);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return enumRegistry.getClassDict().keySet().stream()
                .map(cls -> new ConvertiblePair(String.class, cls))
                .collect(Collectors.toSet());
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String value = (String) source;
        List<CommonEnum> commonEnums = this.enumRegistry.getClassDict().get(targetType.getType());
        return commonEnums.stream()
                .filter(commonEnum -> commonEnum.match(value))
                .findFirst()
                .orElse(null);
    }
}
