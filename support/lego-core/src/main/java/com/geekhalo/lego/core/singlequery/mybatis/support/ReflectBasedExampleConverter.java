package com.geekhalo.lego.core.singlequery.mybatis.support;

import com.geekhalo.lego.annotation.singlequery.*;
import com.geekhalo.lego.core.singlequery.mybatis.ExampleConverter;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.Sort;
import com.geekhalo.lego.core.singlequery.ValueContainer;
import com.geekhalo.lego.core.singlequery.mybatis.support.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReflectBasedExampleConverter<E> implements ExampleConverter<E> {
    private final Class<E> eClass;
    private final List<FieldAnnotationHandler> fieldAnnotationHandlers = new ArrayList<>();

    public ReflectBasedExampleConverter(Class<E> eClass) {
        this.eClass = eClass;
        fieldAnnotationHandlers.add(new FieldEqualToHandlerFilter());
        fieldAnnotationHandlers.add(new FieldGreaterThanHandler());
        fieldAnnotationHandlers.add(new FieldGreaterThanOrEqualToHandler());
        fieldAnnotationHandlers.add(new FieldInHandler());
        fieldAnnotationHandlers.add(new FieldIsNullHandler());
        fieldAnnotationHandlers.add(new FieldLessThanHandler());
        fieldAnnotationHandlers.add(new FieldLessThanOrEqualToHandler());
        fieldAnnotationHandlers.add(new FieldNotEqualToHandler());
        fieldAnnotationHandlers.add(new FieldNotInHandler());
    }

    @Override
    public E convertForQuery(Object query) {
        try {
            E example = instanceExample();

            Object criteria = instanceCriteria(example);

            bindWhere(query, criteria);

            bindSort(query, example);

            bindPageable(query, example);

            return example;
        } catch (Exception e) {
            log.error("failed to run convertForQuery", e);
        }
        return null;
    }

    @Override
    public E convertForCount(Object query) {
        try {
            E example = instanceExample();
            Object criteria = instanceCriteria(example);

            bindWhere(query, criteria);

            return example;
        } catch (Exception e) {
            log.error("failed to run convertForCount", e);
        }
        return null;
    }

    private Object instanceCriteria(E example) throws Exception {
        return MethodUtils.invokeExactMethod(example, "createCriteria");
    }

    private E instanceExample() throws Exception {
        return ConstructorUtils.invokeConstructor(this.eClass);
    }


    private void bindSort(Object o, E example) throws Exception {
        Sort sort = findSort(o);

        bindSort(example, sort);
    }

    private void bindSort(E example, Sort sort) throws Exception {
        if (sort != null){
            MethodUtils.invokeMethod(example, "setOrderByClause", sort.toOrderByClause());
        }
    }

    private Sort findSort(Object o) {
        Sort sort = null;
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(o.getClass());
        for (Field field : allFieldsList){
            if (field.getType() == Sort.class){
                try {
                    sort = (Sort) FieldUtils.readField(field, o, true);
                }catch (Exception e){
                    log.error("failed to bind sort from {}", o);
                }
                break;
            }
        }
        return sort;
    }

    private void bindPageable(Object o, E example) throws Exception{
        Pageable pageable = findPageable(o);
        bindPageable(example, pageable);
    }

    public Pageable findPageable(Object query) {
        Pageable pageable = null;
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(query.getClass());
        for (Field field : allFieldsList){
            if (field.getType() == Pageable.class){
                try {
                    pageable = (Pageable) FieldUtils.readField(field, query, true);
                }catch (Exception e){
                    log.error("failed to find pageable  from {}", query);
                }
                break;
            }
        }
        return pageable;
    }

    private void bindPageable(E example, Pageable pageable) throws Exception{
        if (pageable != null){
            MethodUtils.invokeMethod(example, "setRows", pageable.getLimit());
            MethodUtils.invokeMethod(example, "setOffset", pageable.getOffset());
        }
    }

    private void bindWhere(Object o, Object criteria){
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
                    for (FieldAnnotationHandler handler : fieldAnnotationHandlers){
                        if (handler.support(annotation)){
                            handler.addCriteria(criteria, annotation, value);
                        }
                    }
//                    if (filterAnnotationHandler != null){
//                        String fieldName = filterAnnotationHandler.getFieldValue(annotation);
//                        String operator = filterAnnotationHandler.getOperator();
//                        String methodName = createFilterMethodName(fieldName, operator);
//                        boolean hadParam = filterAnnotationHandler.hasParam();
//                        if (hadParam) {
//                            MethodUtils.invokeMethod(criteria, true, methodName, value);
//                        }else {
//                            if (value instanceof Boolean && (Boolean) value){
//                                MethodUtils.invokeMethod(criteria, true, methodName);
//                            }
//                        }
//                        continue;
//                    }

                    if (annotation.annotationType() == EmbeddedFilter.class){
                        bindWhere(value, criteria);
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
            E example = instanceExample();

            Object criteria = instanceCriteria(example);

//            testFilter(cls, criteria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private void testFilter(Class cls, Object criteria){
//        List<Field> allFieldsList = FieldUtils.getAllFieldsList(cls);
//        for (Field field : allFieldsList){
//            Class fieldType = field.getType();
//            Annotation[] annotations = field.getAnnotations();
//            for (Annotation annotation : annotations){
//                FilterAnnotationHandler filterAnnotationHandler = annotationHandlerMap.get(annotation.annotationType());
//                if (filterAnnotationHandler != null){
//                    String fieldName = filterAnnotationHandler.getFieldValue(annotation);
//                    String operator = filterAnnotationHandler.getOperator();
//                    String methodName = createFilterMethodName(fieldName, operator);
//                    Method matchingMethod = null;
//
//                    boolean hadParam = filterAnnotationHandler.hasParam();
//                    if (hadParam) {
//                        matchingMethod = MethodUtils.getMatchingMethod(criteria.getClass(), methodName, fieldType);
//                    }else {
//                        if (fieldType == Boolean.class || fieldType == Boolean.TYPE){
//                            matchingMethod = MethodUtils.getMatchingMethod(criteria.getClass(), methodName);
//                        }
//                    }
//                    if (matchingMethod == null){
//                        throw new RuntimeException("Can not find method " + methodName + " on class " + criteria.getClass());
//                    }
//                    continue;
//                }
//
//                if (annotation.annotationType() == ExtFilter.class){
//                    bindWhere(fieldType, criteria);
//                }
//            }
//        }
//    }
}
