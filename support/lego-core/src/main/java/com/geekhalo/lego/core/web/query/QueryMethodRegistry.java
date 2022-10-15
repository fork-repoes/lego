package com.geekhalo.lego.core.web.query;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.query.QueryServicesRegistry;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.geekhalo.lego.core.web.support.MethodRegistry;
import com.geekhalo.lego.core.web.support.MultiParamMethod;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class QueryMethodRegistry extends MethodRegistry {
    @Autowired
    private QueryServicesRegistry queryServicesRegistry;

    protected List<Object> getServices(){
        return this.queryServicesRegistry.getQueryServices();
    }
}
