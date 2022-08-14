package com.geekhalo.lego.core.excelasbean.support.reader.bean;

import com.geekhalo.lego.core.excelasbean.support.reader.cell.DefaultHSSFCellReader;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultBeanPropertyWriterChainFactory implements BeanPropertyWriterChainFactory{

    @Override
    public BeanPropertyWriterChain createForField(String path, Field field) {
        return new BeanPropertyWriterChain(new DefaultHSSFCellReader(), new DefaultBeanPropertyWriter(path));
    }
}
