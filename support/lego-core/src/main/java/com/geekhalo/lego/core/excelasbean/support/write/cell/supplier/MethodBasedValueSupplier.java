package com.geekhalo.lego.core.excelasbean.support.write.cell.supplier;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class MethodBasedValueSupplier implements HSSFValueSupplier {
    private final Method method;

    public MethodBasedValueSupplier(Method method) {
        this.method = method;
    }


    @Override
    public Object apply(Object o) {
        if (o == null){
            return null;
        }

        try {
            return MethodUtils.invokeMethod(o, true, method.getName());
        } catch (Exception e) {
            log.error("Failed to invoke method {} on {}", method, o);
        }
        return null;
    }
}
