package com.greenstar.mecwheel.crb.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.UUID;

public class HttpUtils {
    private static final String BASE_URL = "http://203.101.170.211:8081/mis/";
    //private static final String BASE_URL = "http://192.168.0.98:8080/";
    //private static final String BASE_URL = "http://192.168.4.18:8080/";
    //private static final String BASE_URL = "http://192.168.8.101:8080/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
       client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postWithContentType(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, Context context, String contentType) {
        client.post(context, getAbsoluteUrl(url), null, contentType, responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static String getUniqueId() {
        return UUID.randomUUID().toString();
    }
}
