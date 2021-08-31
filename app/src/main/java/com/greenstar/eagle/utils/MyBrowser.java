package com.greenstar.eagle.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyBrowser extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
    public void onLoadResource(WebView view, String url) {
        // Check to see if there is a progress dialog
        int i= 9;
    }

    public void onPageFinished(WebView view, String url) {
        // Page is done loading;
        // hide the progress dialog and show the webvie
        String k = "";
    }


}
