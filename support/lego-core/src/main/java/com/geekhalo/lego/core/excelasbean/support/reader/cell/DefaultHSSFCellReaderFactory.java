package com.geekhalo.lego.core.excelasbean.support.reader.cell;

import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(Integer.MAX_VALUE)
public class DefaultHSSFCellReaderFactory implements HSSFCellReaderFactory{

    @Override
    public HSSFCellReader createFor(String path, Field field) {
        return new DefaultHSSFCellReader();
    }

    @Override
    public boolean support(Field field) {
        return true;
    }
}
