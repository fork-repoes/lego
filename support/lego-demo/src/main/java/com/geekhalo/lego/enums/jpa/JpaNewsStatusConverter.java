package com.geekhalo.lego.enums.jpa;

import com.geekhalo.lego.core.enums.repository.jpa.CommonEnumAttributeConverter;
import com.geekhalo.lego.enums.NewsStatus;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class JpaNewsStatusConverter extends CommonEnumAttributeConverter<NewsStatus> {
    public JpaNewsStatusConverter() {
        super(NewsStatus.values());
    }
}
