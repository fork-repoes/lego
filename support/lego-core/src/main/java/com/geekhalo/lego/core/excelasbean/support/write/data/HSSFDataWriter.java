package com.geekhalo.lego.core.excelasbean.support.write.data;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入数据
 */
public interface HSSFDataWriter<D> {
    /**
     * 写入数据
     * @param cell
     * @param data
     */
    void writeData(HSSFCell cell, D data);
}
