package com.geekhalo.lego.core.feign;

import lombok.Data;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class RpcErrorResult {
    private int code;
    private String msg;
}
