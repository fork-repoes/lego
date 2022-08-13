package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFDataCellConfiguratorFactory;
import com.geekhalo.lego.core.excelasbean.support.write.cell.configurator.HSSFHeaderCellConfiguratorFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 为 Cell 提供 自动大小 支持
 *
 */
public class AutoSizeCellConfiguratorFactory implements HSSFHeaderCellConfiguratorFactory,
        HSSFDataCellConfiguratorFactory {
    @Override
    public boolean support(AnnotatedElement element) {
        return AnnotatedElementUtils.hasAnnotation(element, HSSFHeader.class);
    }

    @Override
    public HSSFCellConfigurator create(AnnotatedElement element) {
        HSSFHeader hssfHeader = AnnotatedElementUtils.findMergedAnnotation(element, HSSFHeader.class);
        return new AutoSizeCellConfigurator(hssfHeader.autoSizeColumn());
    }
}
