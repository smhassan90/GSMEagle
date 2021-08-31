package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.greenstar.eagle.controller.qat.QATMenu;
import com.greenstar.eagle.controller.qtv.QTVMenu;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.utils.Util;
import com.greenstar.eagle.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.fabric.sdk.android.Fabric;

public class FormMenu extends AppCompatActivity implements View.OnClickListener, WebserviceResponse {

    LinearLayout llQTVForm;
    LinearLayout llQATForm;
    LinearLayout llSync;

    ProgressDialog progressBar = null;
    AppDatabase db =null;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_menu_activity);

        Fabric.with(this, new Crashlytics());

        activity = this;
        db = AppDatabase.getAppDatabase(this);

        llQTVForm = findViewById(R.id.llQTVForm);
        llQTVForm.setOnClickListener(this);

        llQATForm = findViewById(R.id.llQATForm);
        llQATForm.setOnClickListener(this);

        llSync = findViewById(R.id.llSync);
        llSync.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences editor = this.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        boolean updateMapping = editor.getBoolean("updateMapping",false);
        boolean syncAll = editor.getBoolean("syncAll",false);

        if(updateMapping && Util.isNetworkAvailable(this)){
            Util util = new Util();
            util.setResponseListener(this);
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Perform Sync ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar

            util.pullMapping(this);

            SharedPreferences.Editor edit = editor.edit();
            edit.putBoolean("updateMapping", false);
            edit.apply();
        }
        if(syncAll && Util.isNetworkAvailable(this)){
            Util util = new Util();
            util.setResponseListener(this);
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Perform Sync ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar
            util.performSync(this);

            SharedPreferences.Editor edit = editor.edit();
            edit.putBoolean("syncAll", false);
            edit.apply();
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences editor = this.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        int isQTVAllowed = editor.getInt("isQTVAllowed",0);
        int isQATAllowed = editor.getInt("isQATAllowed",0);
        int isQATAMAllowed = editor.getInt("isQATAMAllowed",0);


        if(v.getId()==R.id.llQTVForm){
            if(isQTVAllowed==1){
                Intent myIntent = new Intent(this, QTVMenu.class);
                startActivity(myIntent);
            }else{
                Toast.makeText(this,"You are not allowed to perform QTV. Please contact your supervisor",Toast.LENGTH_LONG).show();
            }


        }else if(v.getId()==R.id.llQATForm){
            if(isQATAllowed==1 || isQATAMAllowed==1){
                Intent myIntent = new Intent(this, QATMenu.class);
                startActivity(myIntent);
            }else{
                Toast.makeText(this,"You are not allowed to perform QAT. Please contact your supervisor",Toast.LENGTH_LONG).show();
            }

        }else if(v.getId()==R.id.llSync){
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
}
