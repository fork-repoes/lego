package com.geekhalo.lego.core.excelasbean.support.write.header;

import com.geekhalo.lego.core.excelasbean.support.write.HSSFCellConfigurator;
import com.geekhalo.lego.core.excelasbean.support.write.HSSFWriterChain;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.util.List;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class HSSFHeaderWriterChain extends HSSFWriterChain {
    private final HSSFHeaderWriter headerWriter;

    public HSSFHeaderWriterChain(List<HSSFCellConfigurator> cellConfigs, HSSFHeaderWriter headerWriter) {
        super(cellConfigs);
        this.headerWriter = headerWriter;
    }

    public void write(HSSFCell cell) {
        configForCell(cell);
        headerWriter.writeHeader(cell);
    }
}
