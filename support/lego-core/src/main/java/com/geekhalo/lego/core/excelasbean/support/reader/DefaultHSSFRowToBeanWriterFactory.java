package com.geekhalo.lego.core.excelasbean.support.reader;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultHSSFRowToBeanWriterFactory implements HSSFRowToBeanWriterFactory {
    private final HSSFColumnToBeanWriterFactories columnToBeanWriterFactories;

    public DefaultHSSFRowToBeanWriterFactory(HSSFColumnToBeanWriterFactories columnToBeanWriterFactories) {
        this.columnToBeanWriterFactories = columnToBeanWriterFactories;
    }

    @Override
    public <D> HSSFRowToBeanWriter<D> createForType(Class<D> cls) {
        List<HSSFColumnToBeanWriter> columnToBeanWriters = this.columnToBeanWriterFactories.create(null, cls);

        return new HSSFRowToBeanWriter(new ConstructorBasedSupplier(cls) , columnToBeanWriters);
    }

    class ConstructorBasedSupplier implements Supplier{
        private final Class cls;

        ConstructorBasedSupplier(Class cls) {
            this.cls = cls;
        }

        @Override
        public Object get() {
            try {
                return ConstructorUtils.invokeConstructor(cls);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
