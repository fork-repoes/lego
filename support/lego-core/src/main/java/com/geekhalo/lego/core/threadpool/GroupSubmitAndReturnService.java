package com.geekhalo.lego.core.threadpool;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
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
        Map<T, List<FutureAndTask>> futureAndTaskListMap = new HashMap<>();

        tasks.stream().forEach(trFutureAndTask -> futureAndTaskListMap
                .computeIfAbsent(trFutureAndTask.task,(key)->new ArrayList<>()).add(trFutureAndTask));

        List<T> ts = new ArrayList<>(futureAndTaskListMap.keySet());
        return () ->{
            List<Pair<T, R>> results = this.taskRunner.apply(ts);
            for (Pair<T, R> result : results){
                List<FutureAndTask> futureAndTasks = futureAndTaskListMap.remove(result.getKey());
                futureAndTasks.stream().forEach(futureAndTask->futureAndTask.getFuture().setResult(result.getRight()));
            }
            for (List<FutureAndTask> futureAndTasks : futureAndTaskListMap.values()){
                futureAndTasks.stream().forEach(futureAndTask->futureAndTask.getFuture().setResult(null));
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
