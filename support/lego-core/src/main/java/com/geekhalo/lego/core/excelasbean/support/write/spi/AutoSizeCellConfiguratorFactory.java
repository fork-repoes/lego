package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFDataCellConfiguratorFactory;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFHeaderCellConfiguratorFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class AutoSizeCellConfiguratorFactory implements HSSFHeaderCellConfiguratorFactory, HSSFDataCellConfiguratorFactory {
    @Override
    public boolean support(AnnotatedElement element) {
        return AnnotatedElementUtils.hasAnnotation(element, HSSFHeader.class);
    }

    @Override
    public HSSFCellConfigurator create(AnnotatedElement element) {
        HSSFHeader hssfHeader = AnnotatedElementUtils.findMergedAnnotation(element, HSSFHeader.class);
        return (context, columnIndex, cell) -> {
            if (hssfHeader.autoSizeColumn()) {
                context.getSheet().autoSizeColumn(columnIndex);
            }
        };
    }
}
