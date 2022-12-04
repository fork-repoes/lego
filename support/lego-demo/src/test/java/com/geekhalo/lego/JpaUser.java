package com.geekhalo.lego;

import lombok.Data;

import java.util.Date;

@Data
public class JpaUser{
    private Long id;

    private String name;

    private Integer status;

    private Date birthAt;

    private String mobile;
}
