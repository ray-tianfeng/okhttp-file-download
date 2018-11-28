package com.multi.thread.download.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zl
 * @time 2018/11/27 0027.
 */

public class SharedPUtils {
    private static final String SP_NAME= "sp_storage";

    public static String getString(Context context, String key, String defValue) {
        return context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor;
        (editor = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()).putString(key, value);
        editor.commit();
    }
}
