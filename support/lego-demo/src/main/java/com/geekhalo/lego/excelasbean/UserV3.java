package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.HSSFEmbedded;
import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.annotation.excelasbean.HSSFIndex;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class UserV3 implements User{
    @HSSFHeader("编号")
    private Long id;

    @HSSFHeader("姓名")
    private String name;

    @HSSFHeader("生日")
    @HSSFIndex(value = 100)
    private Date birthAt;

    @HSSFHeader("年龄")
    private Integer age;

    @HSSFEmbedded
    @HSSFIndex(101)
    private Address address;
}
