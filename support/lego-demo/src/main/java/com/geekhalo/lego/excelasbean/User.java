package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
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
    private Long id;

    @HSSFHeader("姓名")
    private String name;

    @HSSFHeader("生日")
    private Date birthAt;

    @HSSFHeader("年龄")
    private Integer age;
}
