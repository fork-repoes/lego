package com.geekhalo.lego.core.threadpool;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

@Slf4j
public class GroupSubmitService<T> {
    private final String name;
    private final BlockingQueue<T> blockingQueue;
    private final Thread dispatcherThread;
    private final ExecutorService executorService;
    private final int maxWaitTime = 5;
    private final int maxSizePreTask = 1000;
    private final Function<List<T>, Void> taskRunner;

    public GroupSubmitService(String name,
                              ExecutorService executorService,
                              Function<List<T>, Void> taskBuilder){
        this.name = name;
        this.executorService = executorService;

        this.taskRunner = taskBuilder;
        this.blockingQueue = new LinkedBlockingQueue<>(10000);
        this.dispatcherThread = new Thread(new DispatcherTask(), name + "DispatchThread");
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

    public void start(){
        this.dispatcherThread.start();
    }

    private List<T> takeFromQueue(){
        try {
            // 如果没有任务，将做等待处理
            T task = this.blockingQueue.poll(maxWaitTime, TimeUnit.SECONDS);

            if (task == null){
                log.debug("poll from queue, data is null after {} s", maxWaitTime);
                return null;
            }

            // 初始化 buffer, 容量为最大 任务数
            List<T> buffer = Lists.newArrayListWithCapacity(this.maxSizePreTask);
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



    private void batchSubmitTasks(List<T> tasks){
        Runnable task = buildTask(tasks);
        this.executorService.submit(task);
    }

    private Runnable buildTask(List<T> tasks) {
        return () -> {
            this.taskRunner.apply(tasks);
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
                List<T> tasks = takeFromQueue();
                if (CollectionUtils.isNotEmpty(tasks)) {
                    batchSubmitTasks(tasks);
                }
            }
            log.info("Dispatcher Thread for {} Stop!!!", name);
        }
    }

}
