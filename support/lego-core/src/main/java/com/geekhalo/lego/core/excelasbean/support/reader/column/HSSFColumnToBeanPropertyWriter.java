package com.geekhalo.lego.core.excelasbean.support.reader.column;

import com.geekhalo.lego.core.excelasbean.support.reader.bean.BeanPropertyWriterChain;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class HSSFColumnToBeanPropertyWriter {
    private final String path;
    private final String title;
    @Getter(AccessLevel.PRIVATE)
    private final BeanPropertyWriterChain propertyWriterChain;

    public HSSFColumnToBeanPropertyWriter(String path,
                                          String title,
                                          BeanPropertyWriterChain propertyWriterChain) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(path));
        Preconditions.checkArgument(propertyWriterChain != null);
        this.path = path;
        this.title = title;
        this.propertyWriterChain = propertyWriterChain;
    }

    public void writeToBean(HSSFCell cell, Object bean){
        this.getPropertyWriterChain().writeToBean(cell, bean);
    }
}
