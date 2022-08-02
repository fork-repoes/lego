package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class SerialJoinItemsExecutor<DATA> extends AbstractJoinItemsExecutor<DATA> {
    public SerialJoinItemsExecutor(Class<DATA> dataCls,
                                   List<JoinItemExecutor<DATA>> joinItemExecutors) {
        super(dataCls, joinItemExecutors);
    }

    @Override
    public void execute(List<DATA> datas) {
        getJoinItemExecutors().forEach(dataJoinExecutor -> {
            log.debug("run join on level {} use {}",
                    dataJoinExecutor.runOnLevel(), dataJoinExecutor);
            dataJoinExecutor.execute(datas);
        });
    }
}
