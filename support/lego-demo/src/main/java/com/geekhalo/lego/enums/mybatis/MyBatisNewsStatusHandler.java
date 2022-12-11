package com.geekhalo.lego.enums.mybatis;

import com.geekhalo.lego.core.enums.repository.mybatis.CommonEnumTypeHandler;
import com.geekhalo.lego.enums.NewsStatus;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@MappedTypes(NewsStatus.class)
public class MyBatisNewsStatusHandler extends CommonEnumTypeHandler<NewsStatus> {
    public MyBatisNewsStatusHandler() {
        super(NewsStatus.values());
    }
}
