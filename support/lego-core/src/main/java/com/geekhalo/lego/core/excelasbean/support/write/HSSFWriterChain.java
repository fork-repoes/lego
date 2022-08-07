package com.geekhalo.lego.core.excelasbean.support.write;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class HSSFWriterChain {
    private final List<HSSFCellConfigurator> cellConfigs = Lists.newArrayList();

    protected HSSFWriterChain(List<HSSFCellConfigurator> cellConfigs){
        this.cellConfigs.addAll(cellConfigs);
        AnnotationAwareOrderComparator.sort(this.cellConfigs);
    }
    protected void configForCell(HSSFCell cell) {
        this.cellConfigs.forEach(hssfCellConfigurator -> hssfCellConfigurator.configFor(cell));
    }
}
