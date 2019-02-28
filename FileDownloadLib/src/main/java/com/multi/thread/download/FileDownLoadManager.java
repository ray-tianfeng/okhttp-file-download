package com.multi.thread.download;

import android.app.Application;
import android.content.Context;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 1.支持断点续传
 * 2.支持多线程下载
 * 3.支持多任务下载
 * 4.进度同步更新
 * 下载管理器
 * 每隔1s通知下载对象同步下载进度
 * 如果进入后台，每隔1s缓存所有下载对象下载进度
 * 每隔5s通知下载对象检查分片是否在下载，如果下载已经暂停则重启下载
 * @author zl
 * @time 2018/11/16 0016.
 */

public class FileDownLoadManager {
    private CopyOnWriteArrayList<DownLoader> downloaderList;
    private boolean isCallOnPause = false;
    private static FileDownLoadManager mFileDownLoadManager = null;
    private Context mContext;
    public FileDownLoadManager() {
        downloaderList = new CopyOnWriteArrayList<>();
        SchedulerManager.getInstance().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for(DownLoader downLoader:downloaderList){
                    if(!downLoader.syncDownloadProgress()){
                        downloaderList.remove(downLoader);
                    }
                    if(isCallOnPause){
                        downLoader.saveConfig();
                    }
                }
            }
        },0,1, TimeUnit.SECONDS);

        SchedulerManager.getInstance().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for(DownLoader downLoader:downloaderList){
                    try {
                        downLoader.checkOrRestartStem();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        downLoader.stop();
                        downLoader.getConfig().getCallback().onFail();
                        downloaderList.remove(downLoader);
                    }
                }
            }
        },5,5, TimeUnit.SECONDS);
    }

    public static synchronized FileDownLoadManager  getInstance(){
        if(mFileDownLoadManager == null){
            mFileDownLoadManager = new FileDownLoadManager ();
        }
        return mFileDownLoadManager;
    }

    public <T extends Application> void  init(T mContext){
        this.mContext = mContext;
    }

    /**
     * 开始下载
     * @param config 下载配置
     */
    public DownLoader startDownload(DownLoadConfig config) throws RuntimeException{
        if(config!=null && !config.checkParams()){
           throw new RuntimeException("check params!!!");
        }
        DownLoader downLoader = new DownLoader(config,mContext);
        downLoader.start();
        downloaderList.add(downLoader);
        return downLoader;
    }

    public void onPause(){
        isCallOnPause = true;
    }

    public void onResume(){
        isCallOnPause = false;
    }

    public void onDestroy(){
        SchedulerManager.getInstance().onDestroy();
    }
}
