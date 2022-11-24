package com.geekhalo.lego.faultrecovery.smart;

import com.geekhalo.lego.core.faultrecovery.smart.ActionType;
import com.geekhalo.lego.core.faultrecovery.smart.ActionTypeProvider;
import org.springframework.stereotype.Component;

/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Component
public class ActionBasedActionTypeProvider implements ActionTypeProvider {
    @Override
    public ActionType get() {
        return ActionContext.get();
    }
}
