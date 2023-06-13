package com.geekhalo.tinyurl.infra.generator.redis;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;
public class RedisBasedBatchNumberGenerator
        extends AbstractRedisBasedNumberGenerator
        implements NumberGenerator {

    @Value("${tinyurl.number.generator.batchSize:500}")
    private int batchSize = 500;

    // 用于存储批量生成的 Number
    private List<Long> tmp = Lists.newArrayList();

    @Override
    public Long nextNumber() {
        synchronized (tmp){
            // 如果 tmp 为空，将触发批量处理操作
            if (CollectionUtils.isEmpty(tmp)){
                long end = this.getStringRedisTemplate().opsForValue()
                        .increment(getGeneratorKey(), batchSize);
                long start = end - batchSize + 1;
                // 批量生成 Number
                for (int i = 0; i< batchSize; i++){
                    tmp.add(start + i);
                }
            }
            // 从集合中获取 Number
            return tmp.remove(0);
        }
    }
}
