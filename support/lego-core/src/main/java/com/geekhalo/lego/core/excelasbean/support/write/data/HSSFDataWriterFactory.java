package com.geekhalo.lego.core.excelasbean.support.write.data;

import com.geekhalo.lego.core.SmartComponent;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/07.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 写入数据
 */
public interface HSSFDataWriterFactory<D> extends SmartComponent<Field> {
    boolean support(Field field);

    HSSFDataWriter create(Field field);
}
