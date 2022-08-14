package com.geekhalo.lego.core.excelasbean.support.reader.column;

import java.util.List;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFColumnToBeanPropertyWriterFactory {
    /**
     * 创建 HSSFColumnToBeanPropertyWriter
     * @param parentPath 父path
     * @param cls
     * @param <D>
     * @return
     */
    <D> List<HSSFColumnToBeanPropertyWriter> create(String parentPath, Class<D> cls);
}
