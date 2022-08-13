package com.geekhalo.lego.excelasbean;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface User {
    void setId(Long id);

    void setName(String name);

    void setBirthAt(java.util.Date birthAt);

    void setAge(Integer age);

    void setAddress(Address address);
}
