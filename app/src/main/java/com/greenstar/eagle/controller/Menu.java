package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.controller.IPCForms.ChildrenRegistrationForm;
import com.greenstar.eagle.controller.IPCForms.ClientRegistrationForm;
import com.greenstar.eagle.controller.IPCForms.FollowupForm;
import com.greenstar.eagle.controller.IPCForms.NeighbourhoodForm;
import com.greenstar.eagle.controller.IPCForms.TokenForm;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Menu extends AppCompatActivity implements View.OnClickListener, WebserviceResponse, View.OnLongClickListener {

    LinearLayout llCRForm;
    LinearLayout llProfile;
    LinearLayout llDashboard;
    LinearLayout llPartialSynchronization;
    LinearLayout llFollowupForm;
    LinearLayout llNeighbourForm;
    LinearLayout llTokenForm;

    ProgressDialog progressBar = null;
    AppDatabase db =null;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        activity = this;
        db = AppDatabase.getAppDatabase(this);

        llDashboard = findViewById(R.id.llDashboard);
        llDashboard.setOnClickListener(this);

        llProfile = findViewById(R.id.llApprovalStatus);
        llProfile.setOnClickListener(this);

        llCRForm = findViewById(R.id.llForm);
        llCRForm.setOnClickListener(this);

        llPartialSynchronization = findViewById(R.id.llPartialSynchronization);
        llPartialSynchronization.setOnClickListener(this);

        llFollowupForm = findViewById(R.id.llFollowupForm);
        llFollowupForm.setOnClickListener(this);

        llNeighbourForm = findViewById(R.id.llNeighbourForm);
        llNeighbourForm.setOnClickListener(this);

        llTokenForm = findViewById(R.id.llTokenForm);
        llTokenForm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.llDashboard){

            Intent myIntent = new Intent(this, ProviderProfile.class);
            startActivity(myIntent);


        }else if(v.getId()==R.id.llApprovalStatus){
            Intent myIntent = new Intent(this, ChildrenRegistrationForm.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llForm){
            if(db!=null){
                Intent myIntent = new Intent(activity, ClientRegistrationForm.class);
                startActivity(myIntent);
            }
        }else if(v.getId()==R.id.llPartialSynchronization){
            Intent myIntent = new Intent(activity, PartialSync.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llNeighbourForm){
            Intent myIntent = new Intent(activity, NeighbourhoodForm.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llFollowupForm){
            Intent myIntent = new Intent(activity, FollowupForm.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llTokenForm){
            Intent myIntent = new Intent(activity, TokenForm.class);
            startActivity(myIntent);
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

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
