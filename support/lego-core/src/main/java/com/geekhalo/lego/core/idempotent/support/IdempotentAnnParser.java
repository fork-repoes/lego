package com.geekhalo.lego.core.idempotent.support;

import com.geekhalo.lego.annotation.idempotent.Idempotent;
import com.geekhalo.lego.annotation.idempotent.IdempotentHandleType;
import com.geekhalo.lego.core.idempotent.IdempotentMeta;
import com.geekhalo.lego.core.idempotent.IdempotentMetaParser;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/11/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class IdempotentAnnParser implements IdempotentMetaParser {
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public IdempotentMeta parse(Method method) {
        Idempotent idempotent = AnnotatedElementUtils.findMergedAnnotation(method, Idempotent.class);
        if (idempotent == null){
            return null;
        }
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        return new AnnBasedIdempotentMeta(idempotent, parameterNames, method.getReturnType());
    }

    class AnnBasedIdempotentMeta implements IdempotentMeta{
        private final Idempotent idempotent;
        private final String[] paramNames;
        private final Class returnType;

        AnnBasedIdempotentMeta(Idempotent idempotent, String[] paramNames, Class returnType) {
            this.idempotent = idempotent;
            this.paramNames = paramNames;
            this.returnType = returnType;
        }

        @Override
        public String executorFactory() {
            return this.idempotent.executorFactory();
        }

        @Override
        public int group() {
            return this.idempotent.group();
        }

        @Override
        public String[] paramNames() {
            return this.paramNames;
        }

        @Override
        public String keyEl() {
            return this.idempotent.keyEl();
        }

        @Override
        public IdempotentHandleType handleType() {
            return this.idempotent.handleType();
        }

        @Override
        public Class returnType() {
            return this.returnType;
        }
    }
}
