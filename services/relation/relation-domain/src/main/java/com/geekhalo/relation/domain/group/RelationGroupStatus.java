package com.geekhalo.relation.domain.group;

import com.geekhalo.lego.common.enums.CommonEnum;

public enum RelationGroupStatus implements CommonEnum {
    ENABLE(1, "启动"),
    DISABLE(0, "禁用");
    private final int code;
    private final String descr;

    RelationGroupStatus(int code, String descr){
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
