package com.geekhalo.like.api;

import lombok.Data;

/**
 * Created by taoli on 2023/5/20.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class ActionVO {
    private String targetType;
    private Long targetId;
    private Long userId;
    private boolean valid;
}
