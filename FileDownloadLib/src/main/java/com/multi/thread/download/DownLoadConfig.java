package com.multi.thread.download;

import com.multi.thread.download.util.DataFormatUtils;
import com.multi.thread.download.util.MD5Utils;
import com.multi.thread.download.util.StringUtils;

import java.util.ArrayList;

/**
 * @author zl
 * @time 2018/11/16 0016.
 */

public class DownLoadConfig {
    private int burstCount = 5;
    private String downloadUrl;
    private DownLoader.DownLoadCallback callback;
    private long fileLength;
    private String savePath;
    private String md5;
    private ArrayList<Burst> bursts;

    public void spiltByFileLength(){
        if(bursts.size() > 0) return;
        long burstLength = fileLength / DataFormatUtils.formatLong(burstCount, 5L);
        for(int i = 0; i < burstCount; i++){
            long startSub = i * burstLength;
            long endSub = (i+1) * burstLength;
            if(i == burstCount-1){
                endSub = fileLength-1;
            }else{
                endSub -= 1;
            }
            Burst burst = new Burst(startSub, endSub, 0,0);
            bursts.add(burst);
        }
    }

    public long getFileLength() {
        return fileLength;
    }

    public ArrayList<Burst> getBursts() {
        return bursts;
    }

    public DownLoadConfig() {
        bursts = new ArrayList<>();
    }

    public void setBurstCount(int burstCount) {
        this.burstCount = burstCount;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setCallback(DownLoader.DownLoadCallback callback) {
        this.callback = callback;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    protected void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public int getBurstCount() {
        return burstCount;
    }

    public DownLoader.DownLoadCallback getCallback() {
        return callback;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String makeFile(){
        return StringUtils.isEmpty(md5)? MD5Utils.md5(downloadUrl):md5;
    }

    boolean checkParams(){
        return !StringUtils.isEmpty(downloadUrl) && burstCount>0 && callback!=null;
    }

    public static class Burst{
        private long startSub;//开始下载下标
        private long endSub;//结束下标
        private long downloadLen;//已经下载的下标
        private volatile long downloadPastLen;//上1s下载的长度
        public Burst() {
        }

        public Burst(long startSub, long endSub, long downloadLen, long downloadPastLen) {
            this.startSub = startSub;
            this.endSub = endSub;
            this.downloadLen = downloadLen;
            this.downloadPastLen = downloadPastLen;
        }

        public long getStartSub() {
            return startSub;
        }

        public void setStartSub(long startSub) {
            this.startSub = startSub;
        }

        public long getEndSub() {
            return endSub;
        }

        public void setEndSub(long endSub) {
            this.endSub = endSub;
        }

        public long getDownloadLen() {
            return downloadLen;
        }

        public void setDownloadLen(long downloadLen) {
            this.downloadLen = downloadLen;
        }

        public long getDownloadPastLen() {
            return downloadPastLen;
        }

        public void setDownloadPastLen(long downloadPastLen) {
            this.downloadPastLen = downloadPastLen;
        }
    }
}
