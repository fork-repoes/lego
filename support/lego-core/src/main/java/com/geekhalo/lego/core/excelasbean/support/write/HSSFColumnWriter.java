package com.geekhalo.lego.core.excelasbean.support.write;

import com.geekhalo.lego.core.excelasbean.support.write.data.HSSFDataWriter;
import com.geekhalo.lego.core.excelasbean.support.write.header.HSSFHeaderWriter;
import org.springframework.core.Ordered;

/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入一列数据，包括数据头，和数据信息
 */
public interface HSSFColumnWriter<D> extends HSSFHeaderWriter, HSSFDataWriter<D>, Ordered {

}
