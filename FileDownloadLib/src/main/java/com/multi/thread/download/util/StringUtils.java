package com.multi.thread.download.util;

/**
 * @author zl
 * @time 2018/11/27 0027.
 */

public class StringUtils {

    public static boolean isEmpty(String ... params) {
        for(String str:params){
            if(str==null || "".equals(str)) return true;
        }
        return false;
    }
}
