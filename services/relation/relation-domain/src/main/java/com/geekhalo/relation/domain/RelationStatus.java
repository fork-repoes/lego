package com.geekhalo.relation.domain;

import com.geekhalo.lego.common.enums.CommonEnum;

public enum RelationStatus implements CommonEnum {
    ;
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
