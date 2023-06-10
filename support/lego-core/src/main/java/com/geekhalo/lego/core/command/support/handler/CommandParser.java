package com.geekhalo.lego.core.command.support.handler;

import com.geekhalo.lego.core.command.support.handler.aggfactory.ConstructorBasedSmartAggFactory;
import com.geekhalo.lego.core.command.support.handler.aggfactory.SmartAggFactories;
import com.geekhalo.lego.core.command.support.handler.aggfactory.StaticMethodBasedSmartAggFactory;
import com.geekhalo.lego.core.command.support.handler.contextfactory.ConstructorBasedSmartContextFactory;
import com.geekhalo.lego.core.command.support.handler.contextfactory.SmartContextFactories;
import com.geekhalo.lego.core.command.support.handler.contextfactory.StaticMethodBasedSmartContextFactory;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

@Component
public class CommandParser {
    private final Set<Class> parsedAggClass = Sets.newHashSet();
    private final Set<Class> parsedContextClass = Sets.newHashSet();
    @Autowired
    private SmartAggFactories smartAggFactories;
    @Autowired
    private SmartContextFactories smartContextFactories;

    public void parseContext(Class context){
        if (this.parsedContextClass.contains(context)){
            return;
        }

        this.parsedContextClass.add(context);

        for (Method aggMethod : context.getDeclaredMethods()) {
            int modifiers = aggMethod.getModifiers();
            // 静态方法
            if (Modifier.isStatic(modifiers)) {
                int parameterCount = aggMethod.getParameterCount();
                if (parameterCount != 1) {
                    // 参数不为 1，直接跳过
                    continue;
                }
                Class paramClass = aggMethod.getParameterTypes()[0];
                StaticMethodBasedSmartContextFactory contextFactory = new StaticMethodBasedSmartContextFactory(paramClass, context, aggMethod);
                this.smartContextFactories.addSmartContextFactory(contextFactory);
            }
        }
        for (Constructor constructor : context.getConstructors()){
            if (constructor.getParameterCount() != 1){
                // 参数不为 1，直接跳过
                continue;
            }
            Class paramClass = constructor.getParameterTypes()[0];
            ConstructorBasedSmartContextFactory contextFactory = new ConstructorBasedSmartContextFactory(paramClass, context, constructor);
            this.smartContextFactories.addSmartContextFactory(contextFactory);
        }
    }

    public void parseAgg(Class agg) {
        if (this.parsedAggClass.contains(agg)){
            return;
        }
        this.parsedAggClass.add(agg);

        // 扫描静态方法
        for (Method aggMethod : agg.getDeclaredMethods()) {
            int modifiers = aggMethod.getModifiers();
            // 静态方法
            if (Modifier.isStatic(modifiers)) {
                int parameterCount = aggMethod.getParameterCount();
                if (parameterCount != 1) {
                    // 参数不为 1，直接跳过
                    continue;
                }
                Class paramClass = aggMethod.getParameterTypes()[0];
                StaticMethodBasedSmartAggFactory aggFactory = new StaticMethodBasedSmartAggFactory(paramClass, agg, aggMethod);
                this.smartAggFactories.addSmartAggFactory(aggFactory);
                parseContext(paramClass);
            }
        }
        for (Constructor constructor : agg.getConstructors()){
            if (constructor.getParameterCount() != 1){
                // 参数不为 1，直接跳过
                continue;
            }
            Class paramClass = constructor.getParameterTypes()[0];
            ConstructorBasedSmartAggFactory smartAggFactory = new ConstructorBasedSmartAggFactory(paramClass, agg, constructor);
            this.smartAggFactories.addSmartAggFactory(smartAggFactory);
            parseContext(paramClass);
        }
    }
}
