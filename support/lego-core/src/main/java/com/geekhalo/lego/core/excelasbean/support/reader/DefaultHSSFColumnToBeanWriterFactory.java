package com.geekhalo.lego.core.excelasbean.support.reader;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
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
public class DefaultHSSFColumnToBeanWriterFactory implements HSSFColumnToBeanWriterFactory{
    private final BeanPropertyWriterChainFactory beanPropertyWriterChainFactory = new DefaultBeanPropertyWriterChainFactory();
    private final HSSFHeaderParser headerParser = new DefaultHSSFHeaderParser();

        @Override
    public <D> List<HSSFColumnToBeanWriter> create(String parentPath, Class<D> cls) {
            return FieldUtils.getFieldsListWithAnnotation(cls, HSSFTemplateHeader.class)
                    .stream()
                    .map(field -> buildForField(parentPath, field))
                    .collect(Collectors.toList());
    }

    private HSSFColumnToBeanWriter buildForField(String parentPath, Field field){
        HSSFTemplateHeader hssfTemplateHeader = AnnotatedElementUtils.findMergedAnnotation(field, HSSFTemplateHeader.class);

        String path = headerParser.toPath(parentPath, field);

        String title = headerParser.toTitle(path, hssfTemplateHeader);

        BeanPropertyWriterChain beanPropertyWriterChain =
                this.beanPropertyWriterChainFactory.createForField(path, field);

        return new HSSFColumnToBeanWriter(path, title, false, beanPropertyWriterChain);
    }
}
