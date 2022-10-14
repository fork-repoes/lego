package com.geekhalo.lego.core.web.query;

import com.alibaba.fastjson.JSON;
import com.geekhalo.lego.core.web.RestResult;
import com.geekhalo.lego.core.web.support.MultiParamMethod;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
public class QueryDispatcherController {
    @Autowired
    private QueryMethodRegistry queryMethodRegistry;

    @PostMapping("/bodyQuery/{serviceName}/{method}")
    public <T> RestResult<T> runBodyQuery(
            @PathVariable String serviceName,
            @PathVariable String method,
            @RequestBody String payload){
        Map<String, SingleParamMethod> methodMap = this.queryMethodRegistry.getSingleQueryServiceMap().get(serviceName);
        if (methodMap == null){
            throw new RuntimeException("ServiceName " + serviceName + " Not Found");
        }
        SingleParamMethod queryMethod = methodMap.get(method);
        if (queryMethod == null){
            throw new RuntimeException("ServiceName " + serviceName + " Method" + method + " Not Found");
        }

        Type paramCls = queryMethod.getParamCls();
        try {
            Object param = JSON.parseObject(payload, paramCls);
            Object result = queryMethod.invoke(param);
            return RestResult.success((T)result);
        }catch (Exception e){
            return RestResult.error("Error");
        }
    }

    @PostMapping("/paramQuery/{serviceName}/{method}")
    public <T> RestResult<T> runParamQuery(
            @PathVariable String serviceName,
            @PathVariable String method,
            HttpServletRequest request){
        Map<String, MultiParamMethod> methodMap = this.queryMethodRegistry.getMultiQueryServiceMap().get(serviceName);
        if (methodMap == null){
            throw new RuntimeException("ServiceName " + serviceName + " Not Found");
        }
        MultiParamMethod queryMethod = methodMap.get(method);
        if (queryMethod == null){
            throw new RuntimeException("ServiceName " + serviceName + " Method" + method + " Not Found");
        }

//        Type paramCls = queryMethod.getParamCls();
//        try {
//            Object param = JSON.parseObject(payload, paramCls);
//            Object result = queryMethod.invoke(param);
//            return RestResult.success((T)result);
//        }catch (Exception e){
//            return RestResult.error("Error");
//        }
        return null;
    }

}
