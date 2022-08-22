package com.geekhalo.lego.core.singlequery.mybatis.support;

import com.geekhalo.lego.annotation.singlequery.*;
import com.geekhalo.lego.core.singlequery.mybatis.ExampleConverter;
import com.geekhalo.lego.core.singlequery.mybatis.Pageable;
import com.geekhalo.lego.core.singlequery.mybatis.Sort;
import com.geekhalo.lego.core.singlequery.mybatis.ValueContainer;
import com.geekhalo.lego.core.singlequery.mybatis.support.handler.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
public class ReflectBasedExampleConverter<E> implements ExampleConverter<E> {
    private final Class<E> eClass;
    private final Map<Class, FilterAnnotationHandler> annotationHandlerMap = Maps.newHashMap();

    public ReflectBasedExampleConverter(Class<E> eClass) {
        this.eClass = eClass;
        annotationHandlerMap.put(FieldEqualTo.class, new FieldEqualToHandlerFilter());
        annotationHandlerMap.put(FieldGreaterThan.class, new FieldGreaterThanHandler());
        annotationHandlerMap.put(FieldGreaterThanOrEqualTo.class, new FieldGreaterThanOrEqualToHandler());
        annotationHandlerMap.put(FieldIn.class, new FieldInHandler());
        annotationHandlerMap.put(FieldIsNull.class, new FieldIsNullHandler());
        annotationHandlerMap.put(FieldLessThan.class, new FieldLessThanHandler());
        annotationHandlerMap.put(FieldLessThanOrEqualTo.class, new FieldLessThanOrEqualToHandler());
        annotationHandlerMap.put(FieldNotEqualTo.class, new FieldNotEqualToHandler());
        annotationHandlerMap.put(FieldNotIn.class, new FieldNotInHandler());
    }

    @Override
    public E convert(Object o) {
        try {
            E example = ConstructorUtils.invokeConstructor(this.eClass);

            Object criteria = MethodUtils.invokeExactMethod(example, "createCriteria");

            bindFilter(o, criteria);

            bindPagable(o, example);

            bindSort(o, example);

            return example;
        } catch (NoSuchMethodException e) {
            log.error("failed to run bind", e);
        } catch (IllegalAccessException e) {
            log.error("failed to run bind", e);
        } catch (InvocationTargetException e) {
            log.error("failed to run bind", e);
        } catch (InstantiationException e) {
            log.error("failed to run bind", e);
        }
        return null;
    }

    private void bindSort(Object o, E example) {
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(o.getClass());
        for (Field field : allFieldsList){
            if (field.getType() == Sort.class){
                try {
                    Sort sort = (Sort) FieldUtils.readField(field, o, true);
                    if (sort != null){
                        MethodUtils.invokeMethod(example, "setOrderByClause", sort.toOrderByClause());
                    }
                }catch (Exception e){
                    log.error("failed to bind sort to {} from {}", example, o);
                }
                break;
            }
        }
    }

    private void bindPagable(Object o, E example) {
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(o.getClass());
        for (Field field : allFieldsList){
            if (field.getType() == Pageable.class){
                try {
                    Pageable pageable = (Pageable) FieldUtils.readField(field, o, true);
                    if (pageable != null){
                        MethodUtils.invokeMethod(example, "setLimit", pageable.getLimit());
                        MethodUtils.invokeMethod(example, "setOffset", pageable.getOffset());
                    }
                }catch (Exception e){
                    log.error("failed to bind pageable to {} from {}", example, o);
                }
                break;
            }
        }
    }

    private void bindFilter(Object o, Object criteria){
        Class cls = o.getClass();
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
        for (Field field : allFieldsList){
            try {
                Object value = FieldUtils.readField(field, o, true);
                if (value == null){
                    log.info("field {} on class {} is null", field.getName(), cls.getName());
                    continue;
                }

                if (value instanceof ValueContainer){
                    List values = ((ValueContainer)value).getValues();
                    if (CollectionUtils.isEmpty(values)){
                        continue;
                    }else {
                        value = values;
                    }
                }

                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations){
                    FilterAnnotationHandler filterAnnotationHandler = annotationHandlerMap.get(annotation.annotationType());
                    if (filterAnnotationHandler != null){
                        String fieldName = filterAnnotationHandler.getFieldValue(annotation);
                        String operator = filterAnnotationHandler.getOperator();
                        String methodName = createFilterMethodName(fieldName, operator);
                        boolean hadParam = filterAnnotationHandler.hasParam();
                        if (hadParam) {
                            MethodUtils.invokeMethod(criteria, true, methodName, value);
                        }else {
                            if (value instanceof Boolean && (Boolean) value){
                                MethodUtils.invokeMethod(criteria, true, methodName);
                            }
                        }
                        continue;
                    }

                    if (annotation.annotationType() == ExtFilter.class){
                        bindFilter(value, criteria);
                    }
                }
            }catch (Exception e){
                log.error("field to bind filter", e);
            }
        }
    }

    private String createFilterMethodName(String fieldName, String operator){
        return "and" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1, fieldName.length()) + operator;
    }

    @Override
    public void testInput(Class cls) {
        try {
            E example = ConstructorUtils.invokeConstructor(this.eClass);

            Object criteria = MethodUtils.invokeExactMethod(example, "createCriteria");

            testFilter(cls, criteria);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private void testFilter(Class cls, Object criteria){
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
        for (Field field : allFieldsList){
            Class fieldType = field.getType();
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations){
                FilterAnnotationHandler filterAnnotationHandler = annotationHandlerMap.get(annotation.annotationType());
                if (filterAnnotationHandler != null){
                    String fieldName = filterAnnotationHandler.getFieldValue(annotation);
                    String operator = filterAnnotationHandler.getOperator();
                    String methodName = createFilterMethodName(fieldName, operator);
                    Method matchingMethod = null;

                    boolean hadParam = filterAnnotationHandler.hasParam();
                    if (hadParam) {
                        matchingMethod = MethodUtils.getMatchingMethod(criteria.getClass(), methodName, fieldType);
                    }else {
                        if (fieldType == Boolean.class || fieldType == Boolean.TYPE){
                            matchingMethod = MethodUtils.getMatchingMethod(criteria.getClass(), methodName);
                        }
                    }
                    if (matchingMethod == null){
                        throw new RuntimeException("Can not find method " + methodName + " on class " + criteria.getClass());
                    }
                    continue;
                }

                if (annotation.annotationType() == ExtFilter.class){
                    bindFilter(fieldType, criteria);
                }
            }
        }
    }
}
