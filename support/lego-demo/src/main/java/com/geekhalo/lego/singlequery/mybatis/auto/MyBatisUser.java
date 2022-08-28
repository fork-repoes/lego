package com.geekhalo.lego.singlequery.mybatis.auto;

import com.geekhalo.lego.singlequery.User;

import java.io.Serializable;
import java.util.Date;

public class MyBatisUser implements Serializable, User {
    private Long id;

    private String name;

    private Integer status;

    private Date birthAt;

    private String mobile;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBirthAt() {
        return birthAt;
    }

    public void setBirthAt(Date birthAt) {
        this.birthAt = birthAt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", birthAt=").append(birthAt);
        sb.append(", mobile=").append(mobile);
        sb.append("]");
        return sb.toString();
    }
}