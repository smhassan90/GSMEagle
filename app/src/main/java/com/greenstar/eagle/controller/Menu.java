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

import com.greenstar.eagle.R;
import com.greenstar.eagle.controller.IPCForms.ChildrenRegistrationForm;
import com.greenstar.eagle.controller.IPCForms.ClientRegistrationForm;
import com.greenstar.eagle.controller.IPCForms.FollowupForm;
import com.greenstar.eagle.controller.IPCForms.NeighbourhoodForm;
import com.greenstar.eagle.controller.IPCForms.Notification;
import com.greenstar.eagle.controller.IPCForms.TokenForm;
import com.greenstar.eagle.controller.oncology.AdvanceScreening;
import com.greenstar.eagle.controller.oncology.InitialScreening;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.ChildRegistrationForm;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.utils.PartialSyncResponse;
import com.greenstar.eagle.utils.Util;
import com.greenstar.eagle.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Menu extends AppCompatActivity implements View.OnClickListener, WebserviceResponse, View.OnLongClickListener, PartialSyncResponse {

    LinearLayout llCRForm;
    LinearLayout llProfile;
    LinearLayout llDashboard;
    LinearLayout llPartialSynchronization;
    LinearLayout llFollowupForm;
    LinearLayout llNeighbourForm;
    LinearLayout llTokenForm;
    LinearLayout llInitialScreening;
    LinearLayout llAdvanceScreening;

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

        llProfile = findViewById(R.id.llProviderProfile);
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

        llInitialScreening = findViewById(R.id.llInitialScreening);
        llInitialScreening.setOnClickListener(this);

        syncPendingForms();
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncPendingForms();
    }

    private void syncPendingForms() {
        Util util = new Util();
        util.setPSResponse(this);
        if(Util.isNetworkAvailable(this)){
            if(db==null){
                db = AppDatabase.getAppDatabase(this);
            }
            int count=0;
            count = db.getCrFormDAO().getCount();
            if(count!=0){
                util.performPSync(this,Codes.PS_TYPE_Client);
                return;
            }

            count = db.getChildRegistrationFormDAO().getCount();
            if(count!=0){
                util.performPSync(this,Codes.PS_TYPE_Children);
                return;
            }

            count = db.getTokenModelDAO().getCount();
            if(count!=0){
                util.performPSync(this,Codes.PS_TYPE_Token);
                return;
            }

            count = db.getNeighbourhoodFormDAO().getCount();
            if(count!=0){
                util.performPSync(this,Codes.PS_TYPE_Neighbour);
                return;
            }

            count = db.getFollowupModelDAO().getCount();
            if(count!=0){
                util.performPSync(this,Codes.PS_TYPE_Followup);
                return;
            }

            count = db.getScreeningFormHeaderDAO().getAllPendingCount();
            if(count!=0){
                util.performPSync(this,Codes.PS_TYPE_SCREENING);
                return;
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.llProviderProfile){
            Intent myIntent = new Intent(this, ProviderProfile.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llDashboard){
            Intent myIntent = new Intent(this, Notification.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llInitialScreening){
            SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
            int type = prefs.getInt("type", 0);
            Intent myIntent = null;
            if(type==Codes.CLIENTS_FOR_PROVIDERS){
                myIntent=new Intent(activity, AdvanceScreening.class);
                startActivity(myIntent);
            }else if(type==Codes.CLIENTS_FOR_SITARABAJI){
                myIntent=new Intent(activity, InitialScreening.class);
                startActivity(myIntent);
            }else{
                Toast.makeText(this, "Please do Basic sync from Partial Sync and then try again.", Toast.LENGTH_LONG).show();
            }

        }else if(v.getId()==R.id.llForm){
            Intent myIntent = new Intent(activity, ClientRegistrationForm.class);
            startActivity(myIntent);
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

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void response(String responseCode, String PSCode, String message) {
        if (db == null) {
            db = AppDatabase.getAppDatabase(this);
        }
        if(responseCode.equals(Codes.ALL_OK)) {
           if(PSCode.equals(Codes.PS_TYPE_Client)){
                db.getCrFormDAO().markSynced();

            }else if(PSCode.equals(Codes.PS_TYPE_Children)){
                db.getChildRegistrationFormDAO().nukeTable();

            }else if(PSCode.equals(Codes.PS_TYPE_Followup)){
                db.getFollowupModelDAO().nukeTable();

            }else if(PSCode.equals(Codes.PS_TYPE_Neighbour)){
                db.getNeighbourhoodFormDAO().nukeTable();
                db.getNeighbourhoodAttendeesModelDAO().nukeTable();

            }else if(PSCode.equals(Codes.PS_TYPE_Token)){
                db.getTokenModelDAO().nukeTable();

            }else if(PSCode.equals(Codes.PS_TYPE_SCREENING)){
               db.getScreeningFormHeaderDAO().nukeTable();
               db.getScreeningAreaDetailDAO().nukeTable();
                db.getScreeningTestDAO().nukeTable();
           }
            Toast.makeText(this,"Auto Sync Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
