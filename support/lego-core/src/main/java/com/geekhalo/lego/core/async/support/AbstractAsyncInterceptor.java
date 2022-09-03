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

/**
 * Created by taoli on 2022/9/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 异步拦截器，拦截方法调用，并将其发送至 MQ；
 */
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

    /**
     * 序列化操作
     * @param arguments
     * @return
     */
    protected String serialize(Object[] arguments) {
        Map<String, String> result = Maps.newHashMapWithExpectedSize(arguments.length);
        for (int i = 0; i < arguments.length; i++){
            result.put(String.valueOf(i), SerializeUtil.serialize(arguments[i]));
        }
        return SerializeUtil.serialize(result);
    }

    /**
     * 解析表达式，获取最终配置信息
     * @param value
     * @return
     */
    protected String resolve(String value) {
        if (StringUtils.hasText(value)) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }

    /**
     * 创建 destination
     * @param topic
     * @param tag
     * @return
     */
    protected String createDestination(String topic, String tag) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tag)){
            return topic + ":" + tag;
        }else {
            return topic;
        }
    }
}
