package com.geekhalo.lego.core.web.command;

import com.geekhalo.lego.core.web.RestResult;
import com.geekhalo.lego.core.web.support.DispatcherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by taoli on 2022/10/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
public class CommandDispatcherController extends DispatcherController {
    public static final String COMMAND_BY_BODY_PATH = "commandByBody";
    public static final String COMMAND_BY_PARAM_PATH = "commandByParam";
    @Autowired
    private CommandMethodRegistry commandMethodRegistry;

    @PostMapping("/" + COMMAND_BY_BODY_PATH + "/{serviceName}/{method}")
    public <T> RestResult<T> runCommandByBody(
            @PathVariable String serviceName,
            @PathVariable String method,
            NativeWebRequest webRequest) {
        return runBodyMethod(serviceName, method, webRequest, this.commandMethodRegistry);
    }

    @RequestMapping(value = "/" + COMMAND_BY_PARAM_PATH + "/{serviceName}/{method}",
            method = {RequestMethod.POST})
    public <T> RestResult<T> runCommandByParam(
            @PathVariable String serviceName,
            @PathVariable String method,
            NativeWebRequest webRequest){
        return runParamMethod(serviceName, method, webRequest, this.commandMethodRegistry);
    }


}
