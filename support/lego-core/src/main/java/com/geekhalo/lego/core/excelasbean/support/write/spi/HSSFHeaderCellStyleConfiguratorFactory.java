package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeaderStyle;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFCellStyleFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFHeaderCellConfiguratorFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFHeaderCellStyleConfiguratorFactory implements HSSFHeaderCellConfiguratorFactory {
    private final HSSFCellStyleFactories cellStyleFactories;

    public HSSFHeaderCellStyleConfiguratorFactory(HSSFCellStyleFactories cellStyleFactories) {
        this.cellStyleFactories = cellStyleFactories;
    }

    @Override
    public boolean support(AnnotatedElement element) {
        if (element instanceof Member){
            Class cls = ((Member) element).getDeclaringClass();
            boolean clsHad = AnnotatedElementUtils.hasAnnotation(cls, HSSFHeaderStyle.class);
            if (clsHad){
                return true;
            }
        }
        return AnnotatedElementUtils.hasAnnotation(element, HSSFHeaderStyle.class);
    }

    @Override
    public HSSFCellConfigurator create(AnnotatedElement element) {
        HSSFHeaderStyle hssfHeaderStyle =
                AnnotatedElementUtils.findMergedAnnotation(element, HSSFHeaderStyle.class);

        if (hssfHeaderStyle == null){
            Class cls = ((Member) element).getDeclaringClass();
            hssfHeaderStyle = AnnotatedElementUtils.findMergedAnnotation(cls, HSSFHeaderStyle.class);
        }
        String name = hssfHeaderStyle.value();

        return ((context, columnIndex, cell) -> {
            HSSFCellStyle hssfCellStyle = this.cellStyleFactories.createStyle(context, name);
            if (hssfCellStyle != null) {
                cell.setCellStyle(hssfCellStyle);
            }
        });
    }
}
