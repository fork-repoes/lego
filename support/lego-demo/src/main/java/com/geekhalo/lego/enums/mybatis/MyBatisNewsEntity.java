package com.geekhalo.lego.enums.mybatis;

import com.geekhalo.lego.enums.NewsStatus;
import lombok.Data;

/**
 * Created by taoli on 2022/12/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class MyBatisNewsEntity {
    private Long id;

    private NewsStatus status;
}
