package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ProviderAdapter;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.utils.MyBrowser;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

public class ProviderProfile  extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ProgressDialog dialog = null;
    AppDatabase db =null;
    Activity activity = null;
    WebView wvPP = null;


    ProviderAdapter providerAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    @Override
    public void onClick(View v) {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String providerCode = prefs.getString("providerCode", "");

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(wvPP.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.setCookie("http://greenstar.ikonbusiness.com/provider/profile.html?providercode=" + providerCode,"username=99998 ; Domain=.greenstar.ikonbusiness.com");
        cookieSyncManager.sync();
        wvPP.loadUrl("http://greenstar.ikonbusiness.com/provider/profile.html?providercode="+providerCode);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
