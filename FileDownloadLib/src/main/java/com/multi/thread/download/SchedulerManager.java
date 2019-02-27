package com.multi.thread.download;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度器
 * @author zl
 * @time 2018/11/27 0027.
 */
public class SchedulerManager {
    private static SchedulerManager mSchedulerManager = null;
    private ScheduledThreadPoolExecutor mScheduledExecutor;
    public SchedulerManager (){
        mScheduledExecutor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2+1);
    };

    public static SchedulerManager  getInstance(){
        if(mSchedulerManager == null){
            mSchedulerManager = new SchedulerManager ();
        }
        return mSchedulerManager;
    }

    // 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
    public ScheduledFuture<Void> scheduleAtFixedRate(Runnable command,
                                                     long initialDelay,
                                                     long period,
                                                     TimeUnit unit){
        return (ScheduledFuture<Void>) mScheduledExecutor.scheduleAtFixedRate(command,initialDelay,period,unit);
    }

    // 延时任务值执行一次
    public ScheduledFuture<Void> schedule(Runnable command,
                                          long delay, TimeUnit unit){
        return (ScheduledFuture<Void>) mScheduledExecutor.schedule(command,delay,unit);
    }

    // 循环任务，以上一次任务的结束时间计算下一次任务的开始时间
    public ScheduledFuture<Void> scheduleWithFixedDelay(Runnable command,
                                                        long initialDelay,
                                                        long period,
                                                        TimeUnit unit){
        return (ScheduledFuture<Void>) mScheduledExecutor.scheduleWithFixedDelay(command,initialDelay,period,unit);
    }

    public <T> Future<T> submit(Runnable command, T result){
        return mScheduledExecutor.submit(command,result);
    }

    public void executeRunnable(Runnable command){
        mScheduledExecutor.execute(command);
    }

    public void executeRunnable(FutureTask<?> futureTask){
        mScheduledExecutor.execute(futureTask);
    }

    public void onDestroy(){
        try {
            if(!mScheduledExecutor.isShutdown()) mScheduledExecutor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            mScheduledExecutor.shutdownNow();
        }
    }
}
