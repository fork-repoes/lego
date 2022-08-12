package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import lombok.Builder;
import lombok.Data;

/**
 * Created by taoli on 2022/8/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Builder
public class Address {
    private Long id;
    @HSSFHeader("省")
    private String l1;
    @HSSFHeader("市")
    private String l2;
    @HSSFHeader("区")
    private String l3;
    @HSSFHeader("详细地址")
    private String l4;
}
