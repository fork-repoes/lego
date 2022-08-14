package com.geekhalo.lego.core.excelasbean.support.reader;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFColumnToBeanWriterFactory {
    <D> List<HSSFColumnToBeanWriter> create(String parentPath, Class<D> cls);
}
