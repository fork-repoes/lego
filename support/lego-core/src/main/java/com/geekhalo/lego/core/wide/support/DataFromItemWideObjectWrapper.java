package com.geekhalo.lego.core.wide.support;

import com.geekhalo.lego.annotation.wide.BindFrom;
import com.geekhalo.lego.core.wide.WideObjectWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.util.*;
/**
 * Created by taoli on 2022/10/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class DataFromItemWideObjectWrapper
    implements WideObjectWrapper {
    private final Object wideObject;
    private final ConversionService conversionService;
    private final Map<Class, List<FieldMapper>> fieldMappers;

    public DataFromItemWideObjectWrapper(Object wideObject, ConversionService conversionService) {
        this.wideObject = wideObject;
        this.conversionService = conversionService;
        this.fieldMappers = parse(this.wideObject.getClass());
    }

    private Map<Class, List<FieldMapper>> parse(Class<?> wideClass) {
        Map<Class, List<FieldMapper>> result = new HashMap<>();
        Field[] fields = FieldUtils.getFieldsWithAnnotation(wideClass, BindFrom.class);
        for (Field wideField : fields){
            BindFrom bindFrom = AnnotatedElementUtils.findMergedAnnotation(wideField, BindFrom.class);
            if (bindFrom != null){
                Field itemField = FieldUtils.getField(bindFrom.sourceClass(), bindFrom.field(), true);
                if (itemField != null){
                    FieldMapper fieldMapper = new FieldMapper(itemField, wideField);
                    result.computeIfAbsent(bindFrom.sourceClass(), cls -> new ArrayList<>())
                            .add(fieldMapper);
                }else {
                    log.warn("Field Config Error, Field Not Found on {} {}", wideField, bindFrom);
                }
            }
        }
        return result;
    }

    @Override
    public <I> boolean isSameWithItem(I item) {
        if (item == null){
            return false;
        }
        List<FieldMapper> fieldMappers = this.fieldMappers.get(item.getClass());
        if (!CollectionUtils.isEmpty(fieldMappers)){
            for (FieldMapper fieldMapper : fieldMappers){
                try {
                    Object valueOnWide = FieldUtils.readField(fieldMapper.wideField, this.wideObject, true);
                    Object valueOnItem = FieldUtils.readField(fieldMapper.itemField, item, true);
                    Object valueOnItemAfterConvert = this.conversionService.convert(valueOnItem, fieldMapper.wideField.getType());
                    if (!Objects.equals(valueOnItemAfterConvert, valueOnWide)){
                        return false;
                    }
                }catch (Exception e){
                    log.error("failed to check {}", e);
                }
            }
        }else {
            return false;
        }
        return true;
    }

    @Override
    public <I> void updateItem(I item) {
        if (item == null){
            return;
        }
        List<FieldMapper> fieldMappers = this.fieldMappers.get(item.getClass());
        if (!CollectionUtils.isEmpty(fieldMappers)){
            for (FieldMapper fieldMapper : fieldMappers){
                try {
                    Object valueOnItem = FieldUtils.readField(fieldMapper.itemField, item, true);
                    Object valueOnItemAfterConvert = this.conversionService.convert(valueOnItem, fieldMapper.wideField.getType());
                    FieldUtils.writeField(fieldMapper.wideField, this.wideObject, valueOnItemAfterConvert, true);
                }catch (Exception e){
                    log.error("failed to check {}", e);
                }
            }
        }
    }

    @Validated
    class FieldMapper{
        private final Field itemField;
        private final Field wideField;

        FieldMapper(Field itemField, Field wideField) {
            this.itemField = itemField;
            this.wideField = wideField;
        }
    }
}
