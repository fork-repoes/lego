package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.core.excelasbean.support.write.cell.style.HSSFCellStyleFactories;
import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFCellStyleConfigurator implements HSSFCellConfigurator{
    private final HSSFCellStyleFactories cellStyleFactories;
    private final String name;
    public HSSFCellStyleConfigurator(HSSFCellStyleFactories cellStyleFactories, String name) {
        Preconditions.checkArgument(cellStyleFactories != null);
        Preconditions.checkArgument(StringUtils.isNotEmpty(name));

        this.name = name;
        this.cellStyleFactories = cellStyleFactories;
    }

    @Override
    public void configFor(HSSFCellWriterContext context,
                          int columnIndex,
                          HSSFCell cell) {
        HSSFCellStyle hssfCellStyle = this.cellStyleFactories.createStyle(context, name);
        if (hssfCellStyle != null) {
            cell.setCellStyle(hssfCellStyle);
        }
    }
}
