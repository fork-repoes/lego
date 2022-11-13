package com.geekhalo.lego.feign;

import com.geekhalo.lego.core.feign.CodeBasedException;

/**
 * Created by taoli on 2022/11/12.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class CustomException extends RuntimeException
    implements CodeBasedException {
    public static final int CODE = 550;

    @Override
    public int getErrorCode() {
        return CODE;
    }

    @Override
    public String getErrorMsg() {
        return "自定义异常";
    }
}
