package com.geekhalo.lego.core.excelasbean.support.reader;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFCellReader {
    Object readValue(HSSFCell cell);
}
