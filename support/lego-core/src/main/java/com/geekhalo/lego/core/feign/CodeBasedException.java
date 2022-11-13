package com.geekhalo.lego.core.feign;

/**
 * Created by taoli on 2022/11/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CodeBasedException {
    int getErrorCode();
    String getErrorMsg();
}
