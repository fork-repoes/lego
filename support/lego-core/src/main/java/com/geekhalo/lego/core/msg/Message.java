package com.geekhalo.lego.core.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private boolean orderly;

    private String topic;

    private String shardingKey;

    private String msgKey;

    private String tag;

    private String msg;
}
