package com.geekhalo.lego.core.web;

import lombok.Data;

/**
 * Created by taoli on 2022/10/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class RestResult<T> {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 1;
    public static final int CODE_FAIL = 2;

    private int code;
    private T data;
    private String msg;

    public static <T> RestResult<T> success(){
        return success(null);
    }

    public static <T> RestResult<T> success(T data){
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(CODE_SUCCESS);
        restResult.setMsg("SUCCESS");
        restResult.setData(data);
        return restResult;
    }

    public static <T> RestResult<T> fail(){
        return fail("请求失败");
    }

    public static <T> RestResult<T> fail(String msg){
        return fail(msg, null);
    }

    public static <T> RestResult<T> fail(String msg, T data){
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(CODE_FAIL);
        restResult.setMsg(msg);
        restResult.setData(data);
        return restResult;
    }

    public static <T> RestResult<T> error(String msg){
        RestResult<T> restResult = new RestResult<>();
        restResult.setCode(CODE_ERROR);
        restResult.setMsg(msg);
        return restResult;
    }
}
