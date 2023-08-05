package com.geekhalo.lego.core.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

@Slf4j
public class GroupSubmitService<T> extends AbstractGroupSubmitService<T>{
    private final Function<List<T>, Void> taskRunner;

    public GroupSubmitService(String name,
                              ExecutorService executorService,
                              Function<List<T>, Void> taskBuilder){
        super(name, executorService);

        this.taskRunner = taskBuilder;
        this.dispatcherThread.setDaemon(true);
    }


    public void submitTask(T task){
        this.blockingQueue.add(task);
    }

    public void submitTask(List<T> tasks){
        if (CollectionUtils.isNotEmpty(tasks)){
            tasks.forEach(this::submitTask);
        }
    }

    @Override
    protected Runnable buildTask(List<T> tasks) {
        return () -> {
            this.taskRunner.apply(tasks);
        };
    }

}
