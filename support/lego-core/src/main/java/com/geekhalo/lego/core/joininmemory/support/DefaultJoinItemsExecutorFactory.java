package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.annotation.joininmemory.JoinInMemeoryExecutorType;
import com.geekhalo.lego.annotation.joininmemory.JoinInMemoryConfig;
import com.geekhalo.lego.core.joininmemory.JoinItemExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemExecutorFactory;
import com.geekhalo.lego.core.joininmemory.JoinItemsExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemsExecutorFactory;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/8/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultJoinItemsExecutorFactory implements JoinItemsExecutorFactory {
    private final List<JoinItemExecutorFactory> joinItemExecutorFactories;
    private final Map<String, ExecutorService> executorServiceMap;

    public DefaultJoinItemsExecutorFactory(Collection<? extends JoinItemExecutorFactory> joinItemExecutorFactories,
                                           Map<String, ExecutorService> executorServiceMap) {
        this.joinItemExecutorFactories = Lists.newArrayList(joinItemExecutorFactories);
        AnnotationAwareOrderComparator.sort(this.joinItemExecutorFactories);
        this.executorServiceMap = executorServiceMap;
    }


    @Override
    public <D> JoinItemsExecutor<D> createFor(Class<D> cls) {
        List<JoinItemExecutor<D>> joinItemExecutors = this.joinItemExecutorFactories.stream()
                .flatMap(factory -> factory.createForType(cls).stream())
                .collect(Collectors.toList());
        JoinInMemoryConfig joinInMemoryConfig = cls.getAnnotation(JoinInMemoryConfig.class);
        return buildJoinItemsExecutor(cls, joinInMemoryConfig, joinItemExecutors);
    }

    private  <D> JoinItemsExecutor<D> buildJoinItemsExecutor(Class<D> cls, JoinInMemoryConfig joinInMemoryConfig, List<JoinItemExecutor<D>> joinItemExecutors){
        if (joinInMemoryConfig.executorType() == JoinInMemeoryExecutorType.SERIAL){
            return new SerialJoinItemsExecutor<>(cls, joinItemExecutors);
        }
        if (joinInMemoryConfig.executorType() == JoinInMemeoryExecutorType.PARALLEL){
            ExecutorService executor = executorServiceMap.get(joinInMemoryConfig.executorName());
            Preconditions.checkArgument(executor != null);
            return new ParallelJoinItemsExecutor<>(cls, joinItemExecutors, executor);
        }
        throw new IllegalArgumentException();
    }
}
