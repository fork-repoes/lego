package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Builder
public class User {
    @HSSFHeader("编号")
    @HSSFIndex(1)
    private Long id;

    @HSSFHeader("姓名")
    @HSSFIndex(3)
    private String name;

    @HSSFHeader("生日")
    @HSSFIndex(5)
    private Date birthAt;

    @HSSFHeader("年龄")
    @HSSFIndex(4)
    private Integer age;

    @HSSFDateFormat("yyyy-MM-dd")
    @HSSFHeader("时间格式化")
    @HSSFIndex(6)
    private Date formatDate;

    @HSSFEmbedded
    @HSSFIndex(4)
    private Address address;

    @HSSFHeader(value = "显示地址")
    public String getShowAddress(){
        return this.address == null ?  "-" : this.address.toString();
    }
}
