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

        if(title.toLowerCase().trim().equals("Monthly Injectable".toLowerCase().trim()))
            view = getLayoutInflater().inflate(R.layout.card_monthly_injectables, null, false);

        else if(title.toLowerCase().trim().equals("Emergency Contraceptive Pills".toLowerCase().trim()))
            view = getLayoutInflater().inflate(R.layout.card_emergency_contraceptive_pills, null, false);

        else if(title.toLowerCase().trim().equals("Hormonal Implants".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_harmonal_implants, null, false);
        }

        else if(title.toLowerCase().trim().equals("Intrauterine Device".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_intrauterine_device, null, false);
        }

        else if(title.toLowerCase().trim().equals("Intrauterine Device/System".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_intrauterine_device_system, null, false);
        }
        else if(title.toLowerCase().trim().equals("Lactational Amenorrhea Method".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_lactational_amenorrhea_method, null, false);
        }

        else if(title.toLowerCase().trim().equals("Male Condoms".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_male_condoms, null, false);
        }

        else if(title.toLowerCase().trim().equals("The Pill".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_the_pill, null, false);
        }

        else if(title.toLowerCase().trim().equals("Progestin-only Injectables".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_progestin_only_injectables, null, false);
        }

        else if(title.toLowerCase().trim().equals("Standard Days Method".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_standard_days_method, null, false);
        }
        else if(title.toLowerCase().trim().equals("Withdrawal".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_withdrawal, null, false);
        }

        else if(title.toLowerCase().trim().equals("Withdrawal".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_withdrawal, null, false);
        }
        else if(title.toLowerCase().trim().equals("Healthy Timing and Spacing of Pregnancy".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_healthy_timing_and_spacing_of_pregnancy, null, false);
        }

        else if(title.toLowerCase().trim().equals("Promoting a Healthy Postpartum Period for the Mother".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_promoting_a_healthy_postpartum_period, null, false);
        }
        else if(title.toLowerCase().trim().equals("Post Abortion Care".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_post_abortion_care, null, false);
        }
        else if(title.toLowerCase().trim().equals("STI/HIV Transmission & Prevention".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_sti_hiv_transmission_and_prevention, null, false);
        }
        else if(title.toLowerCase().trim().equals("STI and HIV Risk Assessment".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_sti_and_hiv_risk_assissment, null, false);
        }
        else if(title.toLowerCase().trim().equals("Dual Protection".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_dual_protection, null, false);
        }
        else if(title.toLowerCase().trim().equals("HIV Counseling and Testing".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_hiv_counseling_and_testing, null, false);
        }
        else if(title.toLowerCase().trim().equals("Screening for Cervical Cancer".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_screening_for_cervical_cancer, null, false);
        }
        else if(title.toLowerCase().trim().equals("Adolescent Counseling".toLowerCase().trim())){
            view = getLayoutInflater().inflate(R.layout.card_adolescent_counseling, null, false);
        }




        return view;
    }
}
