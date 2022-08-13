package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.*;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class UserV1 implements User{
    @HSSFHeader(title = "编号")
    private Long id;

    @HSSFHeader(title = "姓名")
    private String name;

    @HSSFHeader(title = "生日")
    private Date birthAt;

    @HSSFHeader(title = "年龄")
    private Integer age;


    public void setAddress(Address address){

    }
}
