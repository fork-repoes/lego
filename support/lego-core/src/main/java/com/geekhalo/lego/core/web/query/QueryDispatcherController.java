package com.geekhalo.lego.core.web.query;

import com.geekhalo.lego.core.web.RestResult;
import com.geekhalo.lego.core.web.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
public class QueryDispatcherController extends DispatcherController {
    public static final String QUERY_BY_BODY_PATH = "queryByBody";
    public static final String QUERY_BY_PARAM_PATH = "queryByParam";
    @Autowired
    private QueryMethodRegistry queryMethodRegistry;

    @PostMapping("/" + QUERY_BY_BODY_PATH + "/{serviceName}/{method}")
    public <T> RestResult<T> runBodyQuery(
            @PathVariable String serviceName,
            @PathVariable String method,
            NativeWebRequest webRequest){
        return runBodyMethod(serviceName, method, webRequest, this.queryMethodRegistry);
    }

    @RequestMapping(value = "/" + QUERY_BY_PARAM_PATH + "/{serviceName}/{method}", method = {RequestMethod.POST, RequestMethod.GET})
    public <T> RestResult<T> runParamQuery(
            @PathVariable String serviceName,
            @PathVariable String method,
            NativeWebRequest webRequest){
        return runParamMethod(serviceName, method, webRequest, this.queryMethodRegistry);
    }


}
