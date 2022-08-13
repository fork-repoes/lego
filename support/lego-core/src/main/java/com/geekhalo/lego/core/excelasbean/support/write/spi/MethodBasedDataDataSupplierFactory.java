package com.geekhalo.lego.core.excelasbean.support.write.spi;

import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFDataDataSupplierFactory;
import com.geekhalo.lego.core.excelasbean.support.write.cell.HSSFDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class MethodBasedDataDataSupplierFactory implements HSSFDataDataSupplierFactory {
    @Override
    public HSSFDataSupplier create(AnnotatedElement annotatedElement) {
        return o ->{
            if (o == null){
                return null;
            }
            Method method = (Method) annotatedElement;

            try {
                return MethodUtils.invokeMethod(o, true, method.getName());
            } catch (Exception e) {
                log.error("Failed to invoke method {} on {}", method, o);
            }
            return null;
        };
    }

    @Override
    public boolean support(AnnotatedElement annotatedElement) {
        return annotatedElement instanceof Method;
    }
}
