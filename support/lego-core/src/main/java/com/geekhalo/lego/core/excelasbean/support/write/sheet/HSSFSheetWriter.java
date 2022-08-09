package com.geekhalo.lego.core.excelasbean.support.write.sheet;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFSheetWriter<D> {
    void write(HSSFSheetContext context, HSSFSheet sheet, List<D> data);
}
