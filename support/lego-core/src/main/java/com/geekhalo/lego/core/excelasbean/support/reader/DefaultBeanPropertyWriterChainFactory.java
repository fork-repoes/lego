package com.geekhalo.lego.core.excelasbean.support.reader;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultBeanPropertyWriterChainFactory implements BeanPropertyWriterChainFactory{
    private final HSSFHeaderParser hssfHeaderParser = new DefaultHSSFHeaderParser();;
    @Override
    public BeanPropertyWriterChain createForField(String path, Field field) {

        return new BeanPropertyWriterChain(
                new DefaultHSSFCellReader(),
                null,
                new DefaultBeanPropertyWriter(path));
    }
}
