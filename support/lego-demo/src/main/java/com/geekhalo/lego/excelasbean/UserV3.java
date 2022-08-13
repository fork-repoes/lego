package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.HSSFEmbedded;
import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.annotation.excelasbean.HSSFShowOrder;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/8/8.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class UserV3 implements User{
    @HSSFHeader(title = "编号", order = 1)
    private Long id;

    @HSSFHeader(title = "姓名", order = 2)
    private String name;

    @HSSFHeader(title = "生日", order = 4)
    private Date birthAt;

    @HSSFHeader(title = "年龄", order = 3)
    private Integer age;

    @HSSFEmbedded
    @HSSFShowOrder(5)
    private Address address;
}
