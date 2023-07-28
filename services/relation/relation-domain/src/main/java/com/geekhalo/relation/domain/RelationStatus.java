package com.geekhalo.relation.domain;

import com.geekhalo.lego.common.enums.CommonEnum;
import com.geekhalo.relation.domain.sendRequest.SendRequestContext;

public enum RelationStatus implements CommonEnum {
    NONE(0, "初始化"),
    REQUEST_SENT(1, "请求已发送"),
    REQUEST_RECEIVED(2, "请求已接收"),
    REQUEST_ACCEPTED(3, "请求已接受"),
    REQUEST_CANCELLED(10, "请求已取消");
    private final int code;
    private final String descr;

    RelationStatus(int code, String descr){
        this.code = code;
        this.descr = descr;
    }
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.descr;
    }

}
