package com.geekhalo.lego.enums;

import com.geekhalo.lego.common.enums.CommonEnum;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public enum NewsStatus implements CommonEnum {
    DELETE(1, "删除"),
    ONLINE(10, "上线"),
    OFFLINE(20, "下线");
    private final int code;
    private final String desc;

    NewsStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }
}
