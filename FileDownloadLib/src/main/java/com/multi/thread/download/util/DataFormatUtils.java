package com.multi.thread.download.util;

/**
 * @author zl
 * @time 2018/11/27 0027.
 */

public class DataFormatUtils {

    public static long formatLong(Object obj,long defaultValue) {
        long l = defaultValue;
        try {
            l = Long.valueOf(String.valueOf(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static int formatInteger(Object obj,int defaultValue){
        int l = defaultValue;
        try {
            l = Integer.valueOf(String.valueOf(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static float formatFloat(Object obj,float defaultValue) {
        float f = defaultValue;
        try {
            f = Float.valueOf(String.valueOf(obj));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return f;
        }
    }
}
