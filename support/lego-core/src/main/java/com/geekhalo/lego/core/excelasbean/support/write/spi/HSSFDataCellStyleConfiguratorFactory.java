package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.annotation.excelasbean.HSSFDataStyle;
import com.geekhalo.lego.annotation.excelasbean.HSSFHeaderStyle;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellStyleFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFHeaderCellConfiguratorFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
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
        HSSFDataStyle hssfHeaderStyle =
                AnnotatedElementUtils.findMergedAnnotation(element, HSSFDataStyle.class);
        String name = hssfHeaderStyle.value();

        return ((context, columnIndex, cell) -> {
            HSSFCellStyle hssfCellStyle = this.cellStyleFactories.createStyle(context, name);
            if (hssfCellStyle != null) {
                cell.setCellStyle(hssfCellStyle);
            }
        });
    }
}
