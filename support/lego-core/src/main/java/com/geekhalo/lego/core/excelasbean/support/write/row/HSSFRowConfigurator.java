package com.geekhalo.lego.core.excelasbean.support.write.row;

import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Created by taoli on 2022/8/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 提供对 Row 的配置能力
 */
public interface HSSFRowConfigurator {
    void configFor(HSSFRowWriterContext context, HSSFRow row);
}
