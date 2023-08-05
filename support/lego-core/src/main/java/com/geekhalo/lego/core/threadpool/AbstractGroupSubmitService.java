package com.geekhalo.lego.core.threadpool;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
abstract class AbstractGroupSubmitService<Q> {
    protected final String name;
    protected final BlockingQueue<Q> blockingQueue;
    protected final Thread dispatcherThread;
    protected final ExecutorService executorService;
    private final int maxWaitTime = 5;
    private final int maxSizePreTask = 1000;

    public AbstractGroupSubmitService(String name, ExecutorService executorService) {
        this.name = name;
        this.blockingQueue = new LinkedBlockingQueue<>(10000);
        this.dispatcherThread = new Thread(new DispatcherTask(), name + "DispatchThread");
        this.executorService = executorService;
    }

    public void start(){
        this.dispatcherThread.start();
    }

    private List<Q> takeFromQueue(){
        try {
            // 如果没有任务，将做等待处理
            Q task = this.blockingQueue.poll(maxWaitTime, TimeUnit.SECONDS);

            if (task == null){
                log.debug("poll from queue, data is null after {} s", maxWaitTime);
                return null;
            }

            // 初始化 buffer, 容量为最大 任务数
            List<Q> buffer = Lists.newArrayListWithCapacity(this.maxSizePreTask);
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

    public void shutdown() {
        this.dispatcherThread.interrupt();
        try {
            this.executorService.shutdown();
            this.executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("failed to shutdown {}", e);
        }
    }

    protected class DispatcherTask implements Runnable{

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                List<Q> tasks = takeFromQueue();
                if (CollectionUtils.isNotEmpty(tasks)) {
                    batchSubmitTasks(tasks);
                }
            }
            log.info("Dispatcher Thread for {} Stop!!!", name);
        }
    }

    private void batchSubmitTasks(List<Q> tasks){
        Runnable task = buildTask(tasks);
        this.executorService.submit(task);
    }

    protected abstract Runnable buildTask(List<Q> tasks);

}
