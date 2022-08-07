package com.geekhalo.lego.core.excelasbean.support.write.data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Date;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFDataWriter<D> implements HSSFDataWriter<D>{
    private final String fieldName;
    private final Expression expression;
    private final Object nullValue;

    public DefaultHSSFDataWriter(String fieldName, Object nullValue) {
        this.fieldName = fieldName;
        this.nullValue = nullValue;
        ExpressionParser parser = new SpelExpressionParser();
        this.expression = parser.parseExpression(fieldName);
    }

    @Override
    public void writeData(HSSFCell cell, D data) {
        Object value = this.expression.getValue(data);
        if (value == null){
            value = nullValue;
        }

        Class returnType = value.getClass();
        if (returnType == String.class){
            cell.setCellValue((String) value);
        }
        if (returnType == Integer.class){
            cell.setCellValue(String.valueOf(value));
        }
        if (returnType == Long.class){
            cell.setCellValue(String.valueOf(value));
        }
        if (returnType == Date.class){
            cell.setCellValue((Date) value);
        }
    }
}
