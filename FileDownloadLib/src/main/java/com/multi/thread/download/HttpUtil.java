package com.multi.thread.download;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author zl
 * @time 2018/11/27 0027.
 */

public class HttpUtil {
    private static HttpUtil httpUtil;
    private OkHttpClient httpClient;
    private HttpUtil(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);//设置超时
        builder.writeTimeout(30,TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        httpClient = builder.build();
    }
    public static HttpUtil getInstance(){
        if(httpUtil == null){
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    public Call httpGet(String url, Map params, Callback callback){
        Call call = requestGet(url, params);
        call.enqueue(callback);
        return call;
    }

    public Call downloadFileByRange(String url, long startIndex, long endIndex, Callback callback){
        Request request = new Request.Builder().header("RANGE","bytes="+startIndex+"-"+endIndex)
                .url(url)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * 生成一个get请求
     * @param url url地址
     * @param params 参数
     * @return 请求
     */
    private Call requestGet(String url, Map params){
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        StringBuilder sb = new StringBuilder();
        sb.append(url+"?");
        for(Iterator<Map.Entry<String, String>> iterator = entrySet.iterator(); iterator.hasNext();){
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            Object object = entry.getValue();
            String value = object.toString();
            value = URLEncoder.encode(value);
            sb.append(key+"="+value);
            if(iterator.hasNext()){
                sb.append("&");
            }
        }
        url = sb.toString();
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClient.newCall(request);
        return  call;
    }
}
