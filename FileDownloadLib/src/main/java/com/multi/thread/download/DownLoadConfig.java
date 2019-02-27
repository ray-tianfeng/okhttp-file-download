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
            long start = i * burstLength;
            long end = (i+1) * burstLength;
            if(i == burstCount-1){
                end = fileLength-1;
            }else{
                end -= 1;
            }
            Burst burst = new Burst(start, end, 0);
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
        private long startIndex;
        private long endIndex;
        private long downloadIndex;

        public Burst() {
        }

        public Burst(long startIndex, long endIndex, long downloadIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.downloadIndex = downloadIndex;
        }

        public long getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(long startIndex) {
            this.startIndex = startIndex;
        }

        public long getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(long endIndex) {
            this.endIndex = endIndex;
        }

        public long getDownloadIndex() {
            return downloadIndex;
        }

        public void setDownloadIndex(long downloadIndex) {
            this.downloadIndex = downloadIndex;
        }
    }
}
