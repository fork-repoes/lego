package com.geekhalo.lego.core.idempotent.support;

import com.google.common.collect.Maps;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * Created by taoli on 2022/11/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class SimpleIdempotentKeyParser implements IdempotentKeyParser{
    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final Map<String, Expression> expressionCache = Maps.newHashMap();

    @Override
    public String parse(String[] names, Object[] param, String el) {
        EvaluationContext evaluationContext = new StandardEvaluationContext();

        for (int i=0; i < names.length; i++) {
            evaluationContext.setVariable(names[i], param[i]);
        }

        Expression expression = expressionCache.computeIfAbsent(el, e -> this.expressionParser.parseExpression(e));
        Object value = expression.getValue(evaluationContext);
        return String.valueOf(value);
    }
}
