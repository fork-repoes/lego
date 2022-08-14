package com.geekhalo.lego.core.excelasbean.support.reader.column;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.BeanPropertyWriterChain;
import com.geekhalo.lego.core.excelasbean.support.reader.bean.BeanPropertyWriterChainFactory;
import com.geekhalo.lego.core.excelasbean.support.reader.parser.HSSFHeaderParser;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFColumnToBeanPropertyWriterFactory implements HSSFColumnToBeanPropertyWriterFactory {
    private final BeanPropertyWriterChainFactory beanPropertyWriterChainFactory;
    private final HSSFHeaderParser headerParser;

    public DefaultHSSFColumnToBeanPropertyWriterFactory(BeanPropertyWriterChainFactory beanPropertyWriterChainFactory,
                                                        HSSFHeaderParser headerParser) {
        Preconditions.checkArgument(beanPropertyWriterChainFactory != null);
        Preconditions.checkArgument(headerParser != null);

        this.beanPropertyWriterChainFactory = beanPropertyWriterChainFactory;
        this.headerParser = headerParser;
    }

    @Override
    public <D> List<HSSFColumnToBeanPropertyWriter> create(String parentPath, Class<D> cls) {
        // 遍历 @HSSFTemplateHeader 标注的字段，并依次解析
        return FieldUtils.getFieldsListWithAnnotation(cls, HSSFTemplateHeader.class)
                .stream()
                .map(field -> buildForField(parentPath, field))
                .collect(Collectors.toList());
    }

    private HSSFColumnToBeanPropertyWriter buildForField(String parentPath, Field field){
        HSSFTemplateHeader hssfTemplateHeader = AnnotatedElementUtils.findMergedAnnotation(field, HSSFTemplateHeader.class);

        String path = headerParser.toPath(parentPath, field);

        String title = headerParser.toTitle(path, hssfTemplateHeader);

        BeanPropertyWriterChain beanPropertyWriterChain =
                this.beanPropertyWriterChainFactory.createForField(path, field);

        return new HSSFColumnToBeanPropertyWriter(path, title,  beanPropertyWriterChain);
    }
}
