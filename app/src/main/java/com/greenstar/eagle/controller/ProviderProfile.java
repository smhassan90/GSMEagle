package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.utils.MyBrowser;

public class ProviderProfile  extends AppCompatActivity {
    ProgressDialog dialog = null;
    AppDatabase db =null;
    Activity activity = null;
    WebView wvPP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String providerCode = prefs.getString("providerCode", "");
        activity=this;
        db = AppDatabase.getAppDatabase(this);

        setContentView(R.layout.activity_provider_profile);

        wvPP = (WebView) findViewById(R.id.wvPP);
        wvPP.setWebViewClient(new MyBrowser());
        wvPP.getSettings().setJavaScriptEnabled(true);

        wvPP.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d("WebView", consoleMessage.message());
                return true;
            }
        });
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(wvPP.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.setCookie("https://greenstar.ikonbusiness.com/provider/profile.html?providercode=" + providerCode,"username=99998 ; Domain=.greenstar.ikonbusiness.com");
        cookieSyncManager.sync();
        wvPP.getSettings().setAppCacheEnabled(false);
        wvPP.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvPP.loadUrl("https://greenstar.ikonbusiness.com/provider/profile.html?providercode="+providerCode);
    }
}
