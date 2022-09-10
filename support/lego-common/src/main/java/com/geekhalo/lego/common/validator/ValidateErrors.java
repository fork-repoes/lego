package com.geekhalo.lego.common.validator;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class ValidateErrors {
    private List<Error> errors = new ArrayList<>();
    public void addError(String name, String code, String msg){
        Error error = new Error(name, code, name);
        this.errors.add(error);
    }

    public boolean hasError() {
        return !this.errors.isEmpty();
    }

    @Value
    static class Error {
        private String name;
        private String code;
        private String msg;
    }
}
