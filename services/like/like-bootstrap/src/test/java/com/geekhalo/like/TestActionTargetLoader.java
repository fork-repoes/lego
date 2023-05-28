package com.geekhalo.like;

import com.geekhalo.like.domain.target.AbstractSingleActionTargetLoader;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.target.SingleActionTargetLoader;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class TestActionTargetLoader
        extends AbstractSingleActionTargetLoader
        implements SingleActionTargetLoader {

    public TestActionTargetLoader() {
        super("Test");
    }

    @Override
    protected ActionTarget doLoadById(String type, Long id) {
        if (id == null || id.longValue() < 0){
            return ActionTarget.apply(type, id, false);
        }else {
            return ActionTarget.apply(type, id);
        }
    }

}
