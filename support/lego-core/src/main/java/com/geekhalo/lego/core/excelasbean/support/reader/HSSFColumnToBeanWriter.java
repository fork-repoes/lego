package com.geekhalo.lego.core.excelasbean.support.reader;

import lombok.Data;

/**
 * Created by taoli on 2022/8/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class HSSFColumnToBeanWriter {
    private final String path;
    private final String title;
    private final boolean init;
    private final BeanPropertyWriterChain propertyWriterChain;

    public HSSFColumnToBeanWriter(String path,
                                  String title,
                                  boolean init,
                                  BeanPropertyWriterChain propertyWriterChain) {
        this.path = path;
        this.title = title;
        this.init = init;
        this.propertyWriterChain = propertyWriterChain;
    }
}
