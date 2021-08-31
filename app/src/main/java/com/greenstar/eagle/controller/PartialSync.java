package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

    //PS ApprovalQATForm
    View viewPSApprovalQATForm;
    Button btnPSApprovalQATForm;
    TextView tvPSLastTimeApprovalQATForm;
    TextView tvPSDescriptionApprovalQATForm;

    //PS ApprovalQTVForm
    View viewPSApprovalQTVForm;
    Button btnPSApprovalQTVForm;
    TextView tvPSLastTimeApprovalQTVForm;
    TextView tvPSDescriptionApprovalQTVForm;

    //PS Providers
    View viewPSProviders;
    Button btnPSProviders;
    TextView tvPSLastTimeProviders;
    TextView tvPSDescriptionProviders;

    //PS Question
    View viewPSQuestion;
    Button btnPSQuestion;
    TextView tvPSLastTimeQuestion;
    TextView tvPSDescriptionQuestion;

    //PS Area
    View viewPSArea;
    Button btnPSArea;
    TextView tvPSLastTimeArea;
    TextView tvPSDescriptionArea;

    //PS QATTCForm
    View viewPSQATTCForm;
    Button btnPSQATTCForm;
    TextView tvPSLastTimeQATTCForm;
    TextView tvPSDescriptionQATTCForm;

    String partialSyncType = "";

    LinearLayout psQAT;
    LinearLayout psQTV;

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
        psQAT = findViewById(R.id.psQAT);
        psQTV = findViewById(R.id.psQTV);

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        int isQATAllowed = (shared.getInt("isQATAllowed", 0));
        int isQTVAllowed = (shared.getInt("isQTVAllowed", 0));
        int isQATAMAllowed = (shared.getInt(Codes.ISQATAMALLOWED, 0));
        if(isQATAllowed==0 && isQATAMAllowed==0){
            psQAT.setVisibility(View.GONE);
        }
        if(isQTVAllowed==0){
            psQTV.setVisibility(View.GONE);
        }

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

        //PS ApprovalQATForm
        viewPSApprovalQATForm = findViewById(R.id.ps_approval_qat_forms);
        btnPSApprovalQATForm = (Button)viewPSApprovalQATForm.findViewById(R.id.btnPSync);
        tvPSDescriptionApprovalQATForm = viewPSApprovalQATForm.findViewById(R.id.tvPSDescription);
        tvPSLastTimeApprovalQATForm  = viewPSApprovalQATForm.findViewById(R.id.lastSyncDateTime);
        btnPSApprovalQATForm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_ApprovalQATForm;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionApprovalQATForm.setText("Sync Approval QAT Form");

        //PS ApprovalQTVForm
        viewPSApprovalQTVForm= findViewById(R.id.ps_approval_qtv_forms);
        btnPSApprovalQTVForm = viewPSApprovalQTVForm.findViewById(R.id.btnPSync);
        tvPSDescriptionApprovalQTVForm = viewPSApprovalQTVForm.findViewById(R.id.tvPSDescription);
        tvPSLastTimeApprovalQTVForm  = viewPSApprovalQTVForm.findViewById(R.id.lastSyncDateTime);
        btnPSApprovalQTVForm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_ApprovalQTVForm;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionApprovalQTVForm.setText("Sync QTV Approved Forms");

        //PS Providers
        viewPSProviders= findViewById(R.id.ps_providers);
        btnPSProviders = viewPSProviders.findViewById(R.id.btnPSync);
        tvPSDescriptionProviders = viewPSProviders.findViewById(R.id.tvPSDescription);
        tvPSLastTimeProviders  = viewPSProviders.findViewById(R.id.lastSyncDateTime);
        btnPSProviders.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Providers;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionProviders.setText("Sync Providers");


        //PS Question
        viewPSQuestion= findViewById(R.id.ps_questions);
        btnPSQuestion = viewPSQuestion.findViewById(R.id.btnPSync);
        tvPSDescriptionQuestion = viewPSQuestion.findViewById(R.id.tvPSDescription);
        tvPSLastTimeQuestion  = viewPSQuestion.findViewById(R.id.lastSyncDateTime);
        btnPSQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Question;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionQuestion.setText("Sync Questions");

        //PS Area
        viewPSArea= findViewById(R.id.ps_areas);
        btnPSArea = viewPSArea.findViewById(R.id.btnPSync);
        tvPSLastTimeArea  = viewPSArea.findViewById(R.id.lastSyncDateTime);
        tvPSDescriptionArea = viewPSArea.findViewById(R.id.tvPSDescription);
        btnPSArea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_Area;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionArea.setText("Sync Areas");

        //PS QATTCForm
        viewPSQATTCForm= findViewById(R.id.ps_qat_tc_forms);
        btnPSQATTCForm = viewPSQATTCForm.findViewById(R.id.btnPSync);
        tvPSLastTimeQATTCForm  = viewPSQATTCForm.findViewById(R.id.lastSyncDateTime);
        tvPSDescriptionQATTCForm = viewPSQATTCForm.findViewById(R.id.tvPSDescription);
        btnPSQATTCForm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                partialSyncType = Codes.PS_TYPE_QATTCForm;
                callAPI(partialSyncType);
            }
        });
        tvPSDescriptionQATTCForm.setText("Sync QAT TC Forms");
    }

    @Override
    public void onClick(View v) {
        String partialSyncType="";
        if(btnPSBasicInfo.getId() == v.getId()){
            partialSyncType=Codes.PS_TYPE_BASIC_INFO;
        }else if(btnPSProviders.getId() == v.getId()){
            partialSyncType = Codes.PS_TYPE_Providers;
        }else if(btnPSApprovalQATForm.getId()==v.getId()){
            partialSyncType = Codes.PS_TYPE_ApprovalQATForm;
        }else if(btnPSApprovalQTVForm.getId()==v.getId()){
            partialSyncType = Codes.PS_TYPE_ApprovalQTVForm;
        }else if(btnPSArea.getId()==v.getId()){
            partialSyncType = Codes.PS_TYPE_Area;
        }else if(btnPSQATTCForm.getId()==v.getId()){
            partialSyncType = Codes.PS_TYPE_QATTCForm;
        }else if(btnPSQuestion.getId()==v.getId()){
            partialSyncType = Codes.PS_TYPE_Question;
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
            }else if(PSCode.equals(Codes.PS_TYPE_ApprovalQATForm)){
                editor.putString("lastTimeApprovalQATForm", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_ApprovalQTVForm)){
                editor.putString("lastTimeApprovalQTVForm", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Area)){
                editor.putString("lastTimeArea", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Providers)){
                editor.putString("lastTimeProviders", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_QATTCForm)){
                editor.putString("lastTimeQATTCForm", dateTime);
            }else if(PSCode.equals(Codes.PS_TYPE_Question)){
                editor.putString("lastTimeQuestion", dateTime);
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
        String lastTimeApprovalQATForm = prefs.getString("lastTimeApprovalQATForm", "Never Synced");
        String lastTimeApprovalQTVForm = prefs.getString("lastTimeApprovalQTVForm", "Never Synced");
        String lastTimeArea = prefs.getString("lastTimeArea", "Never Synced");
        String lastTimeProviders = prefs.getString("lastTimeProviders", "Never Synced");
        String lastTimeQATTCForm = prefs.getString("lastTimeQATTCForm", "Never Synced");
        String lastTimeQuestion = prefs.getString("lastTimeQuestion", "Never Synced");

        tvPSLastTimeBasicInfo.setText("Last Sync:"+lastTimeBasicInfo);
        tvPSLastTimeApprovalQATForm.setText("Last Sync:"+lastTimeApprovalQATForm);
        tvPSLastTimeApprovalQTVForm.setText("Last Sync:"+lastTimeApprovalQTVForm);
        tvPSLastTimeArea.setText("Last Sync:"+lastTimeArea);
        tvPSLastTimeProviders.setText("Last Sync:"+lastTimeProviders);
        tvPSLastTimeQATTCForm.setText("Last Sync:"+lastTimeQATTCForm);
        tvPSLastTimeQuestion.setText("Last Sync:"+lastTimeQuestion);

    }
}
