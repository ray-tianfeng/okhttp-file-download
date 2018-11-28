package com.multi.thread.download.util;

import java.io.File;

/**
 * @author zl
 * @time 2018/11/27 0027.
 */

public class FileUtils {
    public static boolean isExits(String filePath){
        if(StringUtils.isEmpty(filePath)) return false;
        File file = new File(filePath);
        if(file.exists()) return true;
        return false;
    }
}
