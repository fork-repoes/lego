package com.geekhalo.lego.core.excelasbean.support.write.cell.configurator;

import com.geekhalo.lego.core.SmartComponent;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 对 Header Cell 提供配置能力
 */
public interface HSSFHeaderCellConfiguratorFactory extends SmartComponent<AnnotatedElement> {

    HSSFCellConfigurator create(AnnotatedElement element);
}
