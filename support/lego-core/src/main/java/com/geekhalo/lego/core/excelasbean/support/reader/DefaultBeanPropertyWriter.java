package com.geekhalo.lego.core.excelasbean.support.reader;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultBeanPropertyWriter implements BeanPropertyWriter {
    private final ExpressionParser parser = new SpelExpressionParser();
    private final Expression expression;

    public DefaultBeanPropertyWriter(String path) {
        this.expression = parser.parseExpression(path);
    }

    @Override
    public void writeToBean(Object bean, Object value) {
        this.expression.setValue(bean, value);
    }
}
