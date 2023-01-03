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
    TextView tvPendingFormsBasicInfo;

    //PS Providers
    View viewPSCRForms;
    Button btnPSCRForms;
    TextView tvPSLastTimeCRForms;
    TextView tvPSDescriptionCRForms;
    TextView tvPendingFormsCRForms;


    //PS Children
    View viewPSChildrenForms;
    Button btnPSChildrenForms;
    TextView tvPSLastTimeChildrenForms;
    TextView tvPSDescriptionChildrenForms;
    TextView tvPendingFormsChildrenForms;

    //PS Followup
    View viewPSFollowupForms;
    Button btnPSFollowupForms;
    TextView tvPSLastTimeFollowupForms;
    TextView tvPSDescriptionFollowupForms;
    TextView tvPendingFormsFollowupForms;

    //PS Neighbour
    View viewPSNeighbourForms;
    Button btnPSNeighbourForms;
    TextView tvPSLastTimeNeighbourForms;
    TextView tvPSDescriptionNeighbourForms;
    TextView tvPendingFormsNeighbourForms;

    //PS Token
    View viewPSTokenForms;
    Button btnPSTokenForms;
    TextView tvPSLastTimeTokenForms;
    TextView tvPSDescriptionTokenForms;
    TextView tvPendingFormsTokenForms;

    //PS Token
    View viewPSPullClients;
    Button btnPSPullClients;
    TextView tvPSDescriptionPullClients;

    //PS Areas and questions
    View viewPSPullAreasQuestions;
    Button btnPSPullAreasQuestions;
    TextView tvPSPullAreasQuestions;

    //PS Screening forms
    View viewInitialScreeningForms;
    Button btnPSInitialScreeningForms;
    TextView tvPSLastTimeInitialScreeningForms;
    TextView tvPSDescriptionInitialScreeningForms;
    TextView tvPendingFormsInitialScreeningForms;

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
        tvPendingFormsBasicInfo = viewPSBasicInfo.findViewById(R.id.tvPendingForms);


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
        tvPendingFormsCRForms = viewPSCRForms.findViewById(R.id.tvPendingForms);


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
        tvPendingFormsChildrenForms = viewPSChildrenForms.findViewById(R.id.tvPendingForms);


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
        tvPendingFormsTokenForms = viewPSTokenForms.findViewById(R.id.tvPendingForms);

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
        tvPendingFormsNeighbourForms = viewPSNeighbourForms.findViewById(R.id.tvPendingForms);

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
        tvPendingFormsFollowupForms = viewPSFollowupForms.findViewById(R.id.tvPendingForms);

        //PS ps_pull_clients
        viewPSPullClients= findViewById(R.id.ps_pull_clients);
        btnPSPullClients = viewPSPullClients.findViewById(R.id.btnPSync);
        tvPSDescriptionPullClients = viewPSPullClients.findViewById(R.id.tvPSDescription);
        btnPSPullClients.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_PULL_CLIENTS;
                int count = db.getCrFormDAO().getCount();
                if(count==0){
                    callAPI(partialSyncType);
                }else{
                    Toast.makeText(activity,"First Sync Client registration forms",Toast.LENGTH_LONG).show();
                }

            }
        });
        tvPSDescriptionPullClients.setText("Pull all clients");

        //PS ps_pull_areasquestions
        viewPSPullAreasQuestions= findViewById(R.id.ps_pull_areasquestions);
        tvPSPullAreasQuestions = viewPSPullAreasQuestions.findViewById(R.id.tvPSDescription);
        btnPSPullAreasQuestions = viewPSPullAreasQuestions.findViewById(R.id.btnPSync);
        tvPSPullAreasQuestions = viewPSPullAreasQuestions.findViewById(R.id.tvPSDescription);
        btnPSPullAreasQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_EAGLE_TYPE_PULL_QUESTIONS_AREAS;
                callAPI(partialSyncType);
            }
        });
        tvPSPullAreasQuestions.setText("Pull all screening Areas and its Questions");


        //PS Initial Screening Forms
        viewInitialScreeningForms= findViewById(R.id.ps_initial_screening_forms);
        btnPSInitialScreeningForms = viewInitialScreeningForms.findViewById(R.id.btnPSync);
        tvPSDescriptionInitialScreeningForms = viewInitialScreeningForms.findViewById(R.id.tvPSDescription);
        tvPSLastTimeInitialScreeningForms  = viewInitialScreeningForms.findViewById(R.id.lastSyncDateTime);
        btnPSInitialScreeningForms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_SCREENING;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionInitialScreeningForms.setText("Sync Initial Screening Forms");
        tvPendingFormsInitialScreeningForms = viewInitialScreeningForms.findViewById(R.id.tvPendingForms);
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
        if (db == null) {
            db = AppDatabase.getAppDatabase(this);
        }
        progressBar.dismiss();
        if(responseCode.equals(Codes.ALL_OK)) {
            SharedPreferences.Editor editor =  activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();
            if(PSCode.equals(Codes.PS_TYPE_BASIC_INFO)){
                editor.putString("lastTimeBasicInfo", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Client)){
                db.getCrFormDAO().markSynced();
                editor.putString("lastTimeClient", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Children)){
                db.getChildRegistrationFormDAO().nukeTable();
                editor.putString("lastTimeChildren", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Followup)){
                db.getFollowupModelDAO().nukeTable();
                editor.putString("lastTimeFollowup", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Neighbour)){
                db.getNeighbourhoodFormDAO().nukeTable();
                db.getNeighbourhoodAttendeesModelDAO().nukeTable();
                editor.putString("lastTimeNeighbour", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Token)){
                db.getTokenModelDAO().nukeTable();
                editor.putString("lastTimeToken", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_SCREENING)){
                db.getScreeningAreaDetailDAO().nukeTable();
                db.getScreeningFormHeaderDAO().nukeTable();
                editor.putString("lastTimeInitialScreeningForm", dateTime);
            }

            editor.apply();
            populateLastTime();
            Toast.makeText(this,"Sync Successful", Toast.LENGTH_SHORT).show();
        }
        else if (Codes.TIMEOUT.equals(responseCode)){
            Toast.makeText(this,"Session Timeout", Toast.LENGTH_SHORT).show();
        }else if (Codes.INVALID_VERSION.equals(responseCode)){
            Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
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
        String lastTimeInitialScreeningForm = prefs.getString("lastTimeInitialScreeningForm", "Never Synced");

        tvPSLastTimeBasicInfo.setText("Last Sync:"+lastTimeBasicInfo);
        tvPSLastTimeCRForms.setText("Last Sync:"+lastTimeProviders);
        tvPSLastTimeChildrenForms.setText("Last Sync:"+lastTimeChildren);
        tvPSLastTimeFollowupForms.setText("Last Sync:"+lastTimeFollowup);
        tvPSLastTimeNeighbourForms.setText("Last Sync:"+lastTimeNeighbour);
        tvPSLastTimeTokenForms.setText("Last Sync:"+lastTimeToken);
        tvPSLastTimeInitialScreeningForms.setText("Last Sync:"+lastTimeInitialScreeningForm);

        int count = 0;
        if (db == null) {
            db = AppDatabase.getAppDatabase(this);
        }
        count = db.getChildRegistrationFormDAO().getCount();
        tvPendingFormsChildrenForms.setText("Pending Forms : "+String.valueOf(count));
        if(count==0){
            btnPSChildrenForms.setEnabled(false);
        }

        count = db.getCrFormDAO().getCount();
        tvPendingFormsCRForms.setText("Pending Forms : "+String.valueOf(count));
        if(count==0){
            btnPSCRForms.setEnabled(false);
        }

        count = db.getFollowupModelDAO().getCount();
        tvPendingFormsFollowupForms.setText("Pending Forms : "+String.valueOf(count));
        if(count==0){
            btnPSFollowupForms.setEnabled(false);
        }

        count = db.getNeighbourhoodFormDAO().getCount();
        tvPendingFormsNeighbourForms.setText("Pending Forms : "+String.valueOf(count));
        if(count==0){
            btnPSNeighbourForms.setEnabled(false);
        }

        count = db.getTokenModelDAO().getCount();
        tvPendingFormsTokenForms.setText("Pending Forms : "+String.valueOf(count));
        if(count==0){
            btnPSTokenForms.setEnabled(false);
        }

        count = db.getScreeningFormHeaderDAO().getAllPendingCount();
        tvPendingFormsInitialScreeningForms.setText("Pending Forms : "+String.valueOf(count));
        if(count==0){
            btnPSInitialScreeningForms.setEnabled(false);
        }

    }

    @Override
    public void responseAlert(String response) {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String name = prefs.getString("name", "");
        if (name == null) {

            name="";
        }
        if(response.equals(Codes.TIMEOUT)){
            Toast.makeText(this, "Timeout Session - Could not connect to server. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.SOMETHINGWENTWRONG)){
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
            }
            if(Codes.ALL_OK.equals(status)){
                // db.getProvidersDAO().nukeTable();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        progressBar.dismiss();
    }
}
