package com.geekhalo.lego.core.excelasbean.support.reader.parser;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFHeaderParser {
    /**
     * 生成 显示标题，标题由两部分组成 <br />
     * 1. header 展示文字 <br />
     * 2. path 唯一标识，用于定义 Index，解决数据绑定问题 <br />
     * 示例：姓名(name)，其中 姓名为 header，name 为path
     * @param path
     * @param hssfTemplateHeader
     * @return
     */
    String toTitle(String path, HSSFTemplateHeader hssfTemplateHeader);

    /**
     * path 为多级结构，以'.'为分隔符 <br />
     * @param parentPath
     * @param field
     * @return
     */
    String toPath(String parentPath, Field field);

    /**
     * 从 header 中提取 path
     * @param header
     * @return
     */
    String toPathFromTitle(String header);

}
