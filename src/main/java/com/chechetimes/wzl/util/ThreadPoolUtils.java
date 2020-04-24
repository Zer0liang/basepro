package com.chechetimes.wzl.util;

import com.chechetimes.wzl.entity.constants.ThreadConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池服务.
 *
 * @author WangLei
 * @date 2018 -05-25 13:47:46
 */
public class ThreadPoolUtils {
    private ThreadPoolUtils() {
    }

    public static ExecutorService getTaskPool() {
        return TaskThreadPool.taskPool;
    }

    public static ExecutorCompletionService getTaskCompletionPool() {
        return TaskThreadPool.taskCompletionPool;
    }

    public static ExecutorService getRunPool() {
        return RunThreadPool.runPool;
    }

    public static ExecutorCompletionService getRunCompletionPool() {
        return RunThreadPool.runCompletionPool;
    }

    private static class TaskThreadPool {
        private static ExecutorService taskPool;
        private static ExecutorCompletionService taskCompletionPool;

        static {
            ThreadFactory taskThreadFactory = new ThreadFactoryBuilder().setNameFormat("TaskManager-pool-%d").build();
            taskPool = new ThreadPoolExecutor(ThreadConstants.CORE_THREADS, ThreadConstants.MAX_THREADS, ThreadConstants.ALIVE_TIME,
                    TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
                    taskThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            taskCompletionPool = new ExecutorCompletionService(taskPool);
        }
    }

    private static class RunThreadPool {
        private static ExecutorService runPool;
        private static ExecutorCompletionService runCompletionPool;

        static {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("RunManager-pool-%d").build();
            runPool = new ThreadPoolExecutor(ThreadConstants.CORE_THREADS, ThreadConstants.MAX_THREADS, ThreadConstants.ALIVE_TIME,
                    TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
                    namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            runCompletionPool = new ExecutorCompletionService(runPool);
        }
    }
}
