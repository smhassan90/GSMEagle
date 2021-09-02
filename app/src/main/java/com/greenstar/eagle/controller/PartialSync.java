package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.utils.PartialSyncResponse;
import com.greenstar.eagle.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PartialSync extends AppCompatActivity implements View.OnClickListener, PartialSyncResponse {

    ProgressDialog progressBar = null;
    AppDatabase db =null;
    Activity activity = null;

    //PS Basic Info
    View viewPSBasicInfo;
    Button btnPSBasicInfo;
    TextView tvPSLastTimeBasicInfo;
    TextView tvPSDescriptionBasicInfo;

    //PS Providers
    View viewPSCRForms;
    Button btnPSCRForms;
    TextView tvPSLastTimeCRForms;
    TextView tvPSDescriptionCRForms;

    String partialSyncType = "";

    SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partial_sync);

        activity = this;

        Initialization();
        populateLastTime();

    }

    private void Initialization() {

        //PS Basic Info
        viewPSBasicInfo = findViewById(R.id.ps_basic_info);
        btnPSBasicInfo = (Button)viewPSBasicInfo.findViewById(R.id.btnPSync);
        tvPSDescriptionBasicInfo = viewPSBasicInfo.findViewById(R.id.tvPSDescription);
        tvPSLastTimeBasicInfo = viewPSBasicInfo.findViewById(R.id.lastSyncDateTime);
        btnPSBasicInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_BASIC_INFO;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionBasicInfo.setText("Sync basic information");


        //PS CF Forms
        viewPSCRForms= findViewById(R.id.ps_cr_forms);
        btnPSCRForms = viewPSCRForms.findViewById(R.id.btnPSync);
        tvPSDescriptionCRForms = viewPSCRForms.findViewById(R.id.tvPSDescription);
        tvPSLastTimeCRForms  = viewPSCRForms.findViewById(R.id.lastSyncDateTime);
        btnPSCRForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Providers;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionCRForms.setText("Sync CR Forms");


    }

    @Override
    public void onClick(View v) {
        String partialSyncType="";
        if(btnPSBasicInfo.getId() == v.getId()){
            partialSyncType=Codes.PS_TYPE_BASIC_INFO;
        }else if(btnPSCRForms.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Providers;
        }

        if(Util.isNetworkAvailable(this)){
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Perform Sync ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar
            Util util = new Util();
            util.setPSResponse(this);
            util.performPSync(this,partialSyncType);
        }else{
            Toast.makeText(this,"Please connect to the internet and try again.",Toast.LENGTH_LONG).show();
        }

    }

    private void callAPI(String partialSyncType){
        if(Util.isNetworkAvailable(this)){
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Perform Sync ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar
            Util util = new Util();
            util.setPSResponse(this);
            util.performPSync(this,partialSyncType);
        }else{
            Toast.makeText(this,"Please connect to the internet and try again.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void response(String responseCode, String PSCode, String message) {
        Date date = new Date(System.currentTimeMillis());
        String dateTime=formatter.format(date);

        progressBar.dismiss();
        if(responseCode.equals(Codes.ALL_OK)) {
            SharedPreferences.Editor editor =  activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();
            if(PSCode.equals(Codes.PS_TYPE_BASIC_INFO)){
                editor.putString("lastTimeBasicInfo", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Providers)){
                editor.putString("lastTimeProviders", dateTime);
            }

            editor.apply();
            populateLastTime();
            Toast.makeText(this,"Sync Successful", Toast.LENGTH_SHORT).show();
        }
        else if (Codes.TIMEOUT.equals(responseCode)){
            Toast.makeText(this,"Session Timeout", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show();
        }



    }
    private void populateLastTime(){
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String lastTimeBasicInfo = prefs.getString("lastTimeBasicInfo", "Never Synced");
        String lastTimeProviders = prefs.getString("lastTimeProviders", "Never Synced");

        tvPSLastTimeBasicInfo.setText("Last Sync:"+lastTimeBasicInfo);
        tvPSLastTimeCRForms.setText("Last Sync:"+lastTimeProviders);
    }
}
