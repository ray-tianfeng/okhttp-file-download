package com.multi.thread.download.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author zl
 * @time 2018/11/27 0027.
 */

public class IOUtils {
    /**
     * 关闭资源
     *
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        int length = closeables.length;
        try {
            for (int i = 0; i < length; i++) {
                Closeable closeable = closeables[i];
                if (null != closeable)
                    closeables[i].close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (int i = 0; i < length; i++) {
                closeables[i] = null;
            }
        }
    }
}
