package com.multi.thread.download;
import com.multi.thread.download.util.IOUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author zl
 * @time 2018/11/16 0016.
 */

public class FileDownloadRequestCallback implements Callback {
    private DownLoadConfig.Burst burst;
    private RandomAccessFile downloadFile;

    public FileDownloadRequestCallback(DownLoadConfig.Burst burst, String downloadFilePath) throws FileNotFoundException {
        this.burst = burst;
        this.downloadFile = new RandomAccessFile(downloadFilePath,"rwd");
    }

    public DownLoadConfig.Burst getBurst() {
        return burst;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.code() != 206) return;
        InputStream is = response.body().byteStream();
        downloadFile.seek(burst.getStartSub()+burst.getDownloadLen());
        byte[] buffer = new byte[1024<<2];
        int length = -1;
        while (!call.isCanceled() && (length = is.read(buffer))>0){
            downloadFile.write(buffer,0,length);
            burst.setDownloadLen(burst.getDownloadLen() + length);
        }
        IOUtils.close(is);
        IOUtils.close(response.body());
    }

}
