package com.greenstar.mecwheel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class ProductDescription extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling_card_description);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");

        View view = getProductDetailView(title);
        View includeLayout = findViewById(R.id.layout_counseling_card_description);
        LinearLayout child = includeLayout.findViewById(R.id.llMainCounseling);
        child.addView(view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_nav_icon));
        toolbar.setNavigationOnClickListener(this);

       // setSupportActionBar(toolbar);

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

    private View getProductDetailView(String title){
        View view = null;

        if(title.toLowerCase().trim().equals("Emergency Pill".toLowerCase().trim()))
            view = getLayoutInflater().inflate(R.layout.product_emergency_pill, null, false);

        else if(title.toLowerCase().trim().equals("Novodol".toLowerCase().trim()))
            view = getLayoutInflater().inflate(R.layout.product_novodol, null, false);

        else if(title.toLowerCase().trim().equals("Depotone".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_depotone, null, false);
        }

        else if(title.toLowerCase().trim().equals("Nova-Ject".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_novaject, null, false);
        }

        else if(title.toLowerCase().trim().equals("Femi-Ject".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_femiject, null, false);
        }
        else if(title.toLowerCase().trim().equals("Protect-5".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_protect5, null, false);
        }

        else if(title.toLowerCase().trim().equals("Safeload".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_safeload, null, false);
        }

        else if(title.toLowerCase().trim().equals("Enofer".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_enofer, null, false);
        }

        else if(title.toLowerCase().trim().equals("Vical Plus".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_vical, null, false);
        }

        else if(title.toLowerCase().trim().equals("Vitaferol".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_vitaferol, null, false);
        }
        else if(title.toLowerCase().trim().equals("Misotal".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_misotal, null, false);
        }

        else if(title.toLowerCase().trim().equals("Clean Delivery Kit".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.product_cdk, null, false);
        }

        return view;
    }
}
