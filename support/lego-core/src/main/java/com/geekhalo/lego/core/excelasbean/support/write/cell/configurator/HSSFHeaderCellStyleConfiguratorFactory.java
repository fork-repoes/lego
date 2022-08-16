package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeaderStyle;
import com.geekhalo.lego.core.excelasbean.support.write.cell.style.HSSFCellStyleFactories;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 为 Header Cell 提供 Style 配置
 */
public class HSSFHeaderCellStyleConfiguratorFactory implements HSSFHeaderCellConfiguratorFactory {
    private final HSSFCellStyleFactories cellStyleFactories;

    public HSSFHeaderCellStyleConfiguratorFactory(HSSFCellStyleFactories cellStyleFactories) {
        this.cellStyleFactories = cellStyleFactories;
    }

    @Override
    public boolean support(AnnotatedElement element) {
        if (element instanceof Member){
            // Class 上存在全局配置
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
        // 读取字段或方法上的配置
        HSSFHeaderStyle hssfHeaderStyle =
                AnnotatedElementUtils.findMergedAnnotation(element, HSSFHeaderStyle.class);

        // 不存在，使用 Class 上的全局配置
        if (hssfHeaderStyle == null){
            Class cls = ((Member) element).getDeclaringClass();
            hssfHeaderStyle = AnnotatedElementUtils.findMergedAnnotation(cls, HSSFHeaderStyle.class);
        }

        String name = hssfHeaderStyle.value();
        return new HSSFCellStyleConfigurator(this.cellStyleFactories, name);
    }
}
