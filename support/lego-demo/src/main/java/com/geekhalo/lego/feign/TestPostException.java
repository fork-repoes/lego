package com.geekhalo.lego.feign;

import com.geekhalo.lego.core.feign.CodeBasedException;

/**
 * Created by taoli on 2022/11/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class TestPostException extends RuntimeException implements CodeBasedException {
    @Override
    public int getErrorCode() {
        return 531;
    }

    @Override
    public String getErrorMsg() {
        return "测试异常";
    }
}
