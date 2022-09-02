package com.geekhalo.lego.core.async.support;

import com.geekhalo.lego.core.async.SerializeUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Map;

public abstract class AbstractAsyncInterceptor {
    @Getter(AccessLevel.PROTECTED)
    private final RocketMQTemplate rocketMQTemplate;
    @Getter(AccessLevel.PROTECTED)
    private final Environment environment;

    public AbstractAsyncInterceptor(Environment environment, RocketMQTemplate rocketMQTemplate) {
        Preconditions.checkArgument(environment != null);
        Preconditions.checkArgument(rocketMQTemplate != null);

        this.environment = environment;
        this.rocketMQTemplate = rocketMQTemplate;
    }

    protected String serialize(Object[] arguments) {
        Map<String, String> result = Maps.newHashMapWithExpectedSize(arguments.length);
        for (int i = 0; i < arguments.length; i++){
            result.put(String.valueOf(i), SerializeUtil.serialize(arguments[i]));
        }
        return SerializeUtil.serialize(result);
    }

    protected String resolve(String value) {
        if (StringUtils.hasText(value)) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }
}
