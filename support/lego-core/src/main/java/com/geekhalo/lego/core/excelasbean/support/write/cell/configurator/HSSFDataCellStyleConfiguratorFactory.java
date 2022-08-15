package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.annotation.excelasbean.HSSFDataStyle;
import com.geekhalo.lego.core.excelasbean.support.write.cell.style.HSSFCellStyleFactories;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 为 Data Cell 设置 Style
 */
public class HSSFDataCellStyleConfiguratorFactory implements HSSFHeaderCellConfiguratorFactory {
    private final HSSFCellStyleFactories cellStyleFactories;

    public HSSFDataCellStyleConfiguratorFactory(HSSFCellStyleFactories cellStyleFactories) {
        this.cellStyleFactories = cellStyleFactories;
    }

    @Override
    public boolean support(AnnotatedElement element) {
        return AnnotatedElementUtils.hasAnnotation(element, HSSFDataStyle.class);
    }

    @Override
    public HSSFCellConfigurator create(AnnotatedElement element) {
        HSSFDataStyle headerStyle =
                AnnotatedElementUtils.findMergedAnnotation(element, HSSFDataStyle.class);
        String name = headerStyle.value();

        return new HSSFCellStyleConfigurator(this.cellStyleFactories, name);
    }
}
