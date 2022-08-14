package com.geekhalo.lego.core.excelasbean.support.reader;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFHeaderParser implements HSSFHeaderParser{

    @Override
    public String toTitle(String path, HSSFTemplateHeader hssfTemplateHeader) {
        StringBuilder value = new StringBuilder()
                .append(hssfTemplateHeader.title())
                .append("(").append(path).append(")");
        return value.toString();
    }

    @Override
    public String toPath(String parentPath, Field field) {
        return StringUtils.isEmpty(parentPath) ? field.getName() : parentPath + "." + field.getName();
    }

    @Override
    public String toPathFromTitle(String title) {
        int startIndex = title.indexOf('(');
        return title.substring(startIndex+1, title.length() -1);
    }
}
