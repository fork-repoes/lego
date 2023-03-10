package com.geekhalo.lego.core.singlequery.support;

import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.QueryConverter;
import com.geekhalo.lego.core.singlequery.Sort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Slf4j
public abstract class AbstractQueryConverter<E> implements QueryConverter<E> {
    public Sort findSort(Object o) {
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

    @Override
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
}
