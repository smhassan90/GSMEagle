package com.greenstar.mecwheel.crb.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.greenstar.mecwheel.R;
import com.greenstar.mecwheel.crb.db.AppDatabase;
import com.greenstar.mecwheel.crb.model.Dashboard;

public class DashboardController extends AppCompatActivity {
    WebView wvDashboard;
    AppDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            db = AppDatabase.getAppDatabase(this);

            Dashboard dashboard = db.getDashboardDAO().getDashboard();
            wvDashboard = findViewById(R.id.wvDashboard);
            String html = "";
            if(dashboard!=null){
                html = dashboard.getHtml();
            }else{
                html = getHTML();
            }
            wvDashboard.loadData(html, "text/html", null);

        }catch(Exception e){
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getHTML(){
        String html = "";

        html="<head><body><h1>Dashboard is under development</h1></body></head>";

        return html;
    }
}
