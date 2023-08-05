package com.geekhalo.lego.core.threadpool;

import com.google.common.collect.Lists;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.concurrent.FutureAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class GroupSubmitAndReturnService<T,R> {
    private final String name;
    private final BlockingQueue<FutureAndTask> blockingQueue;
    private final Thread dispatcherThread;
    private final ExecutorService executorService;
    private final int maxWaitTime = 5;
    private final int maxSizePreTask = 1000;
    private final Function<List<T>, List<Pair<T, R>>> taskBuilder;

    public GroupSubmitAndReturnService(String name,
                                       ExecutorService executorService,
                                       Function<List<T>, List<Pair<T, R>>> taskBuilder){
        this.name = name;
        this.executorService = executorService;

        this.taskBuilder = taskBuilder;
        this.blockingQueue = new LinkedBlockingQueue<>(10000);
        this.dispatcherThread = new Thread(new DispatcherTask(), name + "DispatchThread");
        this.dispatcherThread.setDaemon(true);
    }


    public Future<R> submitTask(T task){
        CustomFuture customFuture = new CustomFuture<>();
        FutureAndTask futureAndTask = new FutureAndTask(customFuture, task);
        this.blockingQueue.add(futureAndTask);
        return futureAndTask.getFuture();
    }


    public void start(){
        this.dispatcherThread.start();
    }

    private List<FutureAndTask> takeFromQueue(){
        try {
            // 如果没有任务，将做等待处理
            FutureAndTask task = this.blockingQueue.poll(maxWaitTime, TimeUnit.SECONDS);

            if (task == null){
                log.debug("poll from queue, data is null after {} s", maxWaitTime);
                return null;
            }

            // 初始化 buffer, 容量为最大 任务数
            List<FutureAndTask> buffer = Lists.newArrayListWithCapacity(this.maxSizePreTask);
            // 将 take 出来的任务 放到队列头，保持顺序性
            buffer.add(task);

            // 队列中存在任务，批量将任务提交到 buffer
            this.blockingQueue.drainTo(buffer, this.maxSizePreTask - 1);

            return buffer;
        } catch (InterruptedException e) {
            log.info("failed to take task from queue", e);
        }
        return null;
    }



    private void batchSubmitTasks(List<FutureAndTask> tasks){
        Runnable task = buildTask(tasks);
        this.executorService.submit(task);
    }

    private Runnable buildTask(List<FutureAndTask> tasks) {
        Map<T, FutureAndTask> futureAndTaskMap = tasks.stream()
                .collect(Collectors.toMap(FutureAndTask::getTask, Function.identity()));
        List<T> ts = new ArrayList<>(futureAndTaskMap.keySet());
        return () ->{
            List<Pair<T, R>> results = this.taskBuilder.apply(ts);
            for (Pair<T, R> result : results){
                FutureAndTask futureAndTask = futureAndTaskMap.remove(result.getKey());
                futureAndTask.getFuture().setResult(result.getRight());
            }
            for (FutureAndTask futureAndTask : futureAndTaskMap.values()){
                futureAndTask.getFuture().setResult(null);
            }
        };
    }

    public void shutdown() {
        this.dispatcherThread.interrupt();

        try {
            this.executorService.shutdown();
            this.executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("failed to shutdown {}", e);
        }
    }

    private class DispatcherTask implements Runnable{

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                List<FutureAndTask> tasks = takeFromQueue();
                if (CollectionUtils.isNotEmpty(tasks)) {
                    batchSubmitTasks(tasks);
                }
            }
            log.info("Dispatcher Thread for {} Stop!!!", name);
        }
    }

    @Value
    private class FutureAndTask{
        private final CustomFuture<R> future;
        private final T task;
    }

    class CustomFuture<R> extends FutureTask<R>{
        public CustomFuture() {
            super(() -> null);
        }

        public void setResult(R result) {
            set(result);
        }
    }

}
