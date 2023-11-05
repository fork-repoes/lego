package com.geekhalo.lego.core.web.support;

import com.geekhalo.lego.core.validator.ValidateException;
import com.geekhalo.lego.core.web.RestResult;
import com.geekhalo.lego.core.web.RestResultFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

/**
 * Created by taoli on 2022/10/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultRestResultFactory implements RestResultFactory {
    @Override
    public <T> RestResult<T> success(T t) {
        return RestResult.success(t);
    }

    @Override
    public <T> RestResult<T> error(Throwable error) {
        String msg = getMsgFrom(error);
        return RestResult.error(msg);
    }

    private String getMsgFrom(Throwable error) {
        if (error instanceof ValidateException){
            return ((ValidateException)error).getMsg();
        }
        if (error instanceof EntityNotFoundException){
            return "数据丢失，请查证后重试";
        }
        return "ERROR";
    }
}
