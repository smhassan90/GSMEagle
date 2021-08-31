package com.greenstar.eagle.controller.qtv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Dashboard;

public class DashboardController extends AppCompatActivity {
    WebView wvDashboard;
    AppDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        try {
            db = AppDatabase.getAppDatabase(this);

            Dashboard dashboard = db.getDashboardDAO().getAll();

            wvDashboard = findViewById(R.id.wvDashboard);
            wvDashboard.loadData(dashboard.getHtml(), "text/html", null);
        }catch(Exception e){
            Crashlytics.logException(e);
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
}
