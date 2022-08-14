package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class CreateUserFromV1 implements CreateUserFrom{
    @HSSFTemplateHeader(title = "姓名")
    private String name;
    @HSSFTemplateHeader(title = "出生日期")
    private Date birthAt;
    @HSSFTemplateHeader(title = "年龄")
    private Integer age;
}
