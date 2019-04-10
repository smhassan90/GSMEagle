package com.greenstar.mecwheel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class CounselingCardDescription extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling_card_description);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");

        View view = getCardView(title);
        View includeLayout = findViewById(R.id.layout_counseling_card_description);
        LinearLayout child = includeLayout.findViewById(R.id.llMainCounseling);
        child.addView(view);


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

    private View getCardView(String title){
        View view = null;

        if(title.toLowerCase().trim().equals("Emergency Contraceptive Pills".toLowerCase().trim()))
            view = getLayoutInflater().inflate(R.layout.card_emergency_contraceptive_pills, null, false);
        return view;
    }
}
