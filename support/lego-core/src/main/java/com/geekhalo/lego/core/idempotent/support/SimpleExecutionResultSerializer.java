package com.geekhalo.lego.core.idempotent.support;

import com.alibaba.fastjson.JSON;
import com.geekhalo.lego.common.idempotent.ExecutionException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by taoli on 2022/11/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class SimpleExecutionResultSerializer implements ExecutionResultSerializer{

    @Override
    public <T> String serializeResult(T data) {
        if (data == null){
            return "";
        }
        return JSON.toJSONString(data);
    }

    @Override
    public <T> T deserializeResult(String data, Class<T> cls) {
        if (StringUtils.isEmpty(data)){
            return null;
        }
        return JSON.parseObject(data, cls);
    }

    @Override
    public <T extends Exception> String serializeException(T data) {
        if (data == null){
            return "";
        }
        return data.getClass().getName();
    }

    @Override
    public <T extends Exception> T deserializeException(String data) {
        if (StringUtils.isEmpty(data)){
            return (T) new ExecutionException();
        }

        try {
            return (T) Class.forName(data).newInstance();
        }catch (Exception e){
            return (T) new ExecutionException();
        }
    }
}
