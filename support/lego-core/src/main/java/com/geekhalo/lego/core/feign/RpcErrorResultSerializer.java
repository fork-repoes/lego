package com.geekhalo.lego.core.feign;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class RpcErrorResultSerializer {
    public static RpcErrorResult decode(String json){
        if (StringUtils.isEmpty(json)){
            return null;
        }
        return JSON.parseObject(json, RpcErrorResult.class);
    }

    public static String encode(RpcErrorResult rpcErrorResult){
        if (rpcErrorResult == null){
            return null;
        }
        return JSON.toJSONString(rpcErrorResult);
    }
}
