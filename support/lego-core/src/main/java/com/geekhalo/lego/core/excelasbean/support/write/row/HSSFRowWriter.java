package com.geekhalo.lego.core.excelasbean.support.write.row;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFRowWriter<D> {
    void writeHead(HSSFRowWriterContext context);

     void writeData(HSSFRowWriterContext context, D data);
}
