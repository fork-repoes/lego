package com.geekhalo.lego.core.wide;

import java.util.List;

/**
 * Created by taoli on 2022/10/27.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface WideCommandRepository<W extends Wide> {
    void save(List<W> wides);
}
