package com.geekhalo.lego.core.excelasbean.support.write.cell;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.util.List;
import java.util.Objects;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFCellStyleFactories {
    private final List<HSSFCellStyleFactory> cellStyleFactories = Lists.newArrayList();

    public HSSFCellStyleFactories(List<HSSFCellStyleFactory> cellStyleFactories){
        if (CollectionUtils.isNotEmpty(cellStyleFactories)){
            this.cellStyleFactories.addAll(cellStyleFactories);
        }
    }

    public HSSFCellStyle createStyle(HSSFCellWriterContext context, String name) {
        return this.cellStyleFactories.stream()
                .map(factory -> factory.createStyle(context, name))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
