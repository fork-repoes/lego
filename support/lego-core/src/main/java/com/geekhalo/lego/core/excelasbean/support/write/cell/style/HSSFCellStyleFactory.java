package com.geekhalo.lego.core.excelasbean.support.write.cell.style;

import com.geekhalo.lego.core.excelasbean.support.write.cell.writer.HSSFCellWriterContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * Style 工厂，根据配置创建不同的 HSSFStyle
 */
public interface HSSFCellStyleFactory {

    /**
     * 创建 Style
     * @param context
     * @param name
     * @return
     */
    HSSFCellStyle createStyle(HSSFCellWriterContext context, String name);
}
