package com.greenstar.mecwheel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class CoronaVirusStats extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_corona);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");


        View view = null;

        view = findViewById(R.id.corona_webview);

        WebView wvCoronaStats = view.findViewById(R.id.wvCoronaStats);
        wvCoronaStats.loadUrl("https://www.worldometers.info/coronavirus/");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_nav_icon));
        toolbar.setNavigationOnClickListener(this);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:080011171"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
