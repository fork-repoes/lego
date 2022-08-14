package com.geekhalo.lego.core.excelasbean.support.reader;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface HSSFHeaderParser {

    String toTitle(String path, HSSFTemplateHeader hssfTemplateHeader);

    String toPath(String parentPath, Field field);

    String toPathFromTitle(String title);
}
