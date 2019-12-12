package com.greenstar.mecwheel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnoferDosageCalculator extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
    Spinner spAgeGroup = null;
    EditText etBodyWeight = null;
    EditText etActualHb = null;
    TextView tvIronDose = null;
    TextView tvAmpuleResult = null;

    final double factor = 2.4;

    HashMap<Double,String> ageGroup = new HashMap<Double, String>();
    Double[] hb = new Double[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling_card_description);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");

        View view = getCardView(title);
        spAgeGroup = view.findViewById(R.id.spAgeGroup);
        spAgeGroup.setOnItemSelectedListener(this);
        etBodyWeight = view.findViewById(R.id.etBodyWeight);
        etBodyWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if("0".equals(etBodyWeight.getText().toString())){
                        etBodyWeight.setText("");
                    }
                } else {
                    if("".equals(etBodyWeight.getText().toString())){
                        etBodyWeight.setText("0");
                    }
                }
            }
        });
        etBodyWeight.addTextChangedListener(this);
        etActualHb = view.findViewById(R.id.etActualHb);
        etActualHb.addTextChangedListener(this);
        etActualHb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if("0".equals(etActualHb.getText().toString())){
                        etActualHb.setText("");
                    }
                } else {
                    if("".equals(etActualHb.getText().toString())){
                        etActualHb.setText("0");
                    }
                }
            }
        });
        tvIronDose = view.findViewById(R.id.tvIronDose);
        tvAmpuleResult = view.findViewById(R.id.tvAmpuleResult);

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

        List<String> arrGroup = new ArrayList<>();
        arrGroup.add("Age Group");
        arrGroup.add("Pregnant Women");
        arrGroup.add("Women above 19");
        arrGroup.add("Men above 19");
        arrGroup.add("Children and Teen aged 12-19");
        arrGroup.add("Children Below 11 years");

        hb[0] = 0.0;
        hb[1] = 14.0;
        hb[2] = 15.1;
        hb[3] = 17.7;
        hb[4] = 16.0;
        hb[5] = 15.5;

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAgeGroup.setAdapter(adapter);
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

        if(title.toLowerCase().trim().equals("Enofer Dosage Calculator".toLowerCase().trim()))
            view = getLayoutInflater().inflate(R.layout.enofer_dosage_calc, null, false);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        calculate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        calculate();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void calculate(){
        if(etBodyWeight==null || etActualHb.getText()==null || "0".equals(etActualHb.getText().toString()) || "0".equals(etBodyWeight.getText().toString())
                || 0 == spAgeGroup.getSelectedItemId()
                || "".equals(etActualHb.getText().toString()) || "".equals(etBodyWeight.getText().toString())){
            tvIronDose.setText(String.valueOf(0)+" mg");
            tvAmpuleResult.setText(String.valueOf(0)+" Ampules");
            return;
        }else{
            double result = 0.0;
            double targetHb = hb[(int) spAgeGroup.getSelectedItemId()];
            double weight = Double.valueOf(etBodyWeight.getText().toString());
            double actualHb = Double.valueOf(etActualHb.getText().toString());

            result = weight * (targetHb-actualHb) * factor;
            result = Math.round(result*100)/100.f;
            if(result<0){
                tvIronDose.setText("0 mg");
                tvAmpuleResult.setText("0 Ampules");
            }else{
                BigDecimal bd = new BigDecimal(result);
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                tvIronDose.setText(bd.toString()+" mg");
                int ampules = (int) Math.ceil( result/100);
                tvAmpuleResult.setText(String.valueOf(ampules)+" Ampules");
            }

        }
    }
}
