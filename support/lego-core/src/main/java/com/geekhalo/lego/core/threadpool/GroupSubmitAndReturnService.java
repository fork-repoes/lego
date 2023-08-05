package com.geekhalo.lego.core.threadpool;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class GroupSubmitAndReturnService<T,R>
    extends AbstractGroupSubmitService<GroupSubmitAndReturnService.FutureAndTask<T,R>>{
    private final Function<List<T>, List<Pair<T, R>>> taskRunner;

    public GroupSubmitAndReturnService(String name,
                                       ExecutorService executorService,
                                       Function<List<T>, List<Pair<T, R>>> taskRunner){
        super(name, executorService);
        this.taskRunner = taskRunner;
    }


    public Future<R> submitTask(T task){
        CustomFuture customFuture = new CustomFuture<>();
        FutureAndTask futureAndTask = new FutureAndTask(customFuture, task);
        this.blockingQueue.add(futureAndTask);
        return futureAndTask.getFuture();
    }


    @Override
    protected Runnable buildTask(List<FutureAndTask<T, R>> tasks) {
        Map<T, FutureAndTask> futureAndTaskMap = tasks.stream()
                .collect(Collectors.toMap(futureAndTask -> futureAndTask.getTask(), Function.identity()));
        List<T> ts = new ArrayList<>(futureAndTaskMap.keySet());
        return () ->{
            List<Pair<T, R>> results = this.taskRunner.apply(ts);
            for (Pair<T, R> result : results){
                FutureAndTask futureAndTask = futureAndTaskMap.remove(result.getKey());
                futureAndTask.getFuture().setResult(result.getRight());
            }
            for (FutureAndTask futureAndTask : futureAndTaskMap.values()){
                futureAndTask.getFuture().setResult(null);
            }
        };
    }


    @Value
    static class FutureAndTask<T, R>{
        private final CustomFuture<R> future;
        private final T task;
    }

    static class CustomFuture<R> extends FutureTask<R>{
        public CustomFuture() {
            super(() -> null);
        }

        public void setResult(R result) {
            set(result);
        }
    }

}
