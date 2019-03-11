package com.multi.thread.download.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zl
 * @time 2019/3/7 0007.
 */

public class Platform {
    public static Platform PLATFORM = findPlatform();

    public static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        return new Android();
    }

    protected Executor defaultCallbackExecutor(){
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable){
        defaultCallbackExecutor().execute(runnable);
    }

    static class Android extends Platform{
        @Override
        protected Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor{
            private final Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable command) {
                mHandler.post(command);
            }
        }
    }

}
