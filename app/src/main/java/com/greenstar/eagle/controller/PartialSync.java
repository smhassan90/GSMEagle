package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.utils.PartialSyncResponse;
import com.greenstar.eagle.utils.Util;
import com.greenstar.eagle.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PartialSync extends AppCompatActivity implements View.OnClickListener, PartialSyncResponse, WebserviceResponse {

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

    //PS Children
    View viewPSChildrenForms;
    Button btnPSChildrenForms;
    TextView tvPSLastTimeChildrenForms;
    TextView tvPSDescriptionChildrenForms;

    //PS Followup
    View viewPSFollowupForms;
    Button btnPSFollowupForms;
    TextView tvPSLastTimeFollowupForms;
    TextView tvPSDescriptionFollowupForms;

    //PS Neighbour
    View viewPSNeighbourForms;
    Button btnPSNeighbourForms;
    TextView tvPSLastTimeNeighbourForms;
    TextView tvPSDescriptionNeighbourForms;

    //PS Token
    View viewPSTokenForms;
    Button btnPSTokenForms;
    TextView tvPSLastTimeTokenForms;
    TextView tvPSDescriptionTokenForms;

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
                partialSyncType = Codes.PS_TYPE_Client;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionCRForms.setText("Sync Client Forms");


        //PS ps_children_forms
        viewPSChildrenForms= findViewById(R.id.ps_children_forms);
        btnPSChildrenForms = viewPSChildrenForms.findViewById(R.id.btnPSync);
        tvPSDescriptionChildrenForms = viewPSChildrenForms.findViewById(R.id.tvPSDescription);
        tvPSLastTimeChildrenForms  = viewPSChildrenForms.findViewById(R.id.lastSyncDateTime);
        btnPSChildrenForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Children;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionChildrenForms.setText("Sync Children Forms");



        //PS ps_token_forms
        viewPSTokenForms= findViewById(R.id.ps_token_forms);
        btnPSTokenForms = viewPSTokenForms.findViewById(R.id.btnPSync);
        tvPSDescriptionTokenForms = viewPSTokenForms.findViewById(R.id.tvPSDescription);
        tvPSLastTimeTokenForms  = viewPSTokenForms.findViewById(R.id.lastSyncDateTime);
        btnPSTokenForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Token;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionTokenForms.setText("Sync Token Forms");

        //PS ps_neighbour_forms
        viewPSNeighbourForms= findViewById(R.id.ps_neighbour_forms);
        btnPSNeighbourForms = viewPSNeighbourForms.findViewById(R.id.btnPSync);
        tvPSDescriptionNeighbourForms = viewPSNeighbourForms.findViewById(R.id.tvPSDescription);
        tvPSLastTimeNeighbourForms  = viewPSNeighbourForms.findViewById(R.id.lastSyncDateTime);
        btnPSNeighbourForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Neighbour;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionNeighbourForms.setText("Sync Neighbour Forms");

        //PS ps_followup_forms
        viewPSFollowupForms= findViewById(R.id.ps_followup_forms);
        btnPSFollowupForms = viewPSFollowupForms.findViewById(R.id.btnPSync);
        tvPSDescriptionFollowupForms = viewPSFollowupForms.findViewById(R.id.tvPSDescription);
        tvPSLastTimeFollowupForms  = viewPSFollowupForms.findViewById(R.id.lastSyncDateTime);
        btnPSFollowupForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Followup;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionFollowupForms.setText("Sync Followup Forms");
    }

    @Override
    public void onClick(View v) {
        String partialSyncType="";
        if(btnPSBasicInfo.getId() == v.getId()){
            try{
                if(Util.isNetworkAvailable(this)){
                    Util util = new Util();
                    util.setResponseListener(this);
                    progressBar = new ProgressDialog(this);
                    progressBar.setCancelable(false);//you can cancel it by pressing back button
                    progressBar.setMessage("Perform Sync ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();//displays the progress bar
                    util.performSync(this);
                }else{
                    Toast.makeText(this,"Please connect to the internet service and try again.", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Crashlytics.logException(e);
            }
            return;
        }else if(btnPSCRForms.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Client;
        }else if(btnPSChildrenForms.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Children;
        }else if(btnPSFollowupForms.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Followup;
        }else if(btnPSNeighbourForms.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Neighbour;
        }else if(btnPSTokenForms.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Token;
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
            }else if(PSCode.equals(Codes.PS_TYPE_Client)){
                editor.putString("lastTimeProviders", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Client)){
                editor.putString("lastTimeClient", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Children)){
                editor.putString("lastTimeChildren", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Followup)){
                editor.putString("lastTimeFollowup", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Neighbour)){
                editor.putString("lastTimeNeighbour", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Token)){
                editor.putString("lastTimeToken", dateTime);
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
        String lastTimeClient = prefs.getString("lastTimeClient", "Never Synced");
        String lastTimeChildren = prefs.getString("lastTimeChildren", "Never Synced");
        String lastTimeFollowup = prefs.getString("lastTimeFollowup", "Never Synced");
        String lastTimeNeighbour = prefs.getString("lastTimeNeighbour", "Never Synced");
        String lastTimeToken = prefs.getString("lastTimeToken", "Never Synced");

        tvPSLastTimeBasicInfo.setText("Last Sync:"+lastTimeBasicInfo);
        tvPSLastTimeCRForms.setText("Last Sync:"+lastTimeProviders);
        tvPSLastTimeChildrenForms.setText("Last Sync:"+lastTimeChildren);
        tvPSLastTimeFollowupForms.setText("Last Sync:"+lastTimeFollowup);
        tvPSLastTimeNeighbourForms.setText("Last Sync:"+lastTimeNeighbour);
        tvPSLastTimeTokenForms.setText("Last Sync:"+lastTimeToken);
    }

    @Override
    public void responseAlert(String response) {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String name = prefs.getString("name", "");
        if (name == null) {

            name="";
        }
        if(response.equals(Codes.TIMEOUT)){
            Crashlytics.log(Log.ERROR, name,  " Timeout session at "+new Date());
            Toast.makeText(this, "Timeout Session - Could not connect to server. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.SOMETHINGWENTWRONG)){
            Crashlytics.log(Log.ERROR, name," Something went wrong at "+new Date());
            Toast.makeText(this, "Something went wrong. Please contact Admin",Toast.LENGTH_LONG).show();
        }else{
            JSONObject responseObj=null;
            String status="";
            String message = "";
            String data="";

            try {
                responseObj = new JSONObject(response);
                status=responseObj.get("status").toString();
                message=responseObj.get("message").toString();
                data=responseObj.get("data").toString();
            } catch (JSONException e) {
                Crashlytics.logException(e);
            }
            if(Codes.ALL_OK.equals(status)){
                // db.getProvidersDAO().nukeTable();
            }
            Crashlytics.log(Log.ERROR, name, " Sync Successful at "+new Date());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        progressBar.dismiss();
    }
}
