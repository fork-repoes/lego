package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinExecutor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class ParallelJoinExecutorGroup<DATA> extends AbstractJoinExecutorGroup<DATA>{
    private final ExecutorService executor;
    private final List<JoinExecutorWithLevel> joinExecutorWithLevel;
    public ParallelJoinExecutorGroup(Class<DATA> dataCls,
                                     List<JoinExecutor<DATA>> joinExecutors,
                                     ExecutorService executor) {
        super(dataCls, joinExecutors);
        this.executor = executor;
        this.joinExecutorWithLevel = buildJoinExecutorWithLevel();
    }

    private List<JoinExecutorWithLevel> buildJoinExecutorWithLevel() {
        List<JoinExecutorWithLevel> collect = getJoinExecutors().stream()
                .collect(Collectors.groupingBy(joinExecutor -> joinExecutor.runOnLevel()))
                .entrySet().stream()
                .map(entry -> new JoinExecutorWithLevel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        Collections.sort(collect, Comparator.comparingInt(o -> o.level));
        return collect;
    }

    @Override
    public void execute(List<DATA> datas) {
        this.joinExecutorWithLevel.forEach(joinExecutorWithLevel1 -> {
            log.debug("run join on level {} use {}", joinExecutorWithLevel1.getLevel(),
                    joinExecutorWithLevel1.getJoinExecutors());

            List<Task> tasks = buildTasks(joinExecutorWithLevel1, datas);
            try {
                this.executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                log.error("invoke task {} interrupted", tasks, e);
            }
        });
    }

    private List<Task> buildTasks(JoinExecutorWithLevel joinExecutorWithLevel, List<DATA> datas) {
        return joinExecutorWithLevel.getJoinExecutors().stream()
                .map(joinExecutor -> new Task(joinExecutor, datas))
                .collect(Collectors.toList());
    }

    @Value
    class Task implements Callable<Void> {
        private final JoinExecutor<DATA> joinExecutor;
        private final List<DATA> datas;

        @Override
        public Void call() throws Exception {
            this.joinExecutor.execute(this.datas);
            return null;
        }
    }

    @Value
    class JoinExecutorWithLevel{
        private final Integer level;
        private final List<JoinExecutor<DATA>> joinExecutors;
    }
}
