package com.geekhalo.lego.core.msg.sender.support;

import java.util.Date;
import java.util.List;

/**
 * Created by taoli on 2022/10/16.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface LocalMessageRepository {
    void save(LocalMessage message);

    void update(LocalMessage message);

    List<LocalMessage> loadNotSuccessByUpdateGt(Date latestUpdateTime, int size);
}