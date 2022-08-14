package com.geekhalo.lego.excelasbean;

import com.geekhalo.lego.annotation.excelasbean.HSSFHeader;
import com.geekhalo.lego.annotation.excelasbean.HSSFTemplateHeader;
import lombok.Data;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class AddressForm {
    @HSSFTemplateHeader(title = "省")
    private String l1;
    @HSSFTemplateHeader(title = "市")
    private String l2;
    @HSSFTemplateHeader(title = "区")
    private String l3;
    @HSSFTemplateHeader(title = "详细地址")
    private String l4;
}
