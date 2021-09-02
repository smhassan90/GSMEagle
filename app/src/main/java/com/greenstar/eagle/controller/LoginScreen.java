package com.greenstar.eagle.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.utils.HttpUtils;
import com.greenstar.eagle.utils.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog = null;
    AppDatabase db =null;
    Activity activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        db = AppDatabase.getAppDatabase(this);

        setContentView(R.layout.activity_login_screen);
        Button btnLogin = (Button)this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Logging in");
        dialog.setMessage("In Progress. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        EditText etEmpCode = findViewById(R.id.etEmpCode);
        String code = etEmpCode.getText().toString();
        if(code !=null && !"".equals(code)){
            if(Util.isNetworkAvailable(this)){
                loginHit(code);
            }else{
                Toast.makeText(this,"Please connect to internet then try again.", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Employee code cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    private void loginHit(final String code){
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("uniqueId",HttpUtils.getUniqueId());
        rp.add("staffType", Codes.STAFFTYPE);
        dialog.show();
        HttpUtils.get("loginOnly", rp, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String codeReceived = "";
                String data =null;
                String token="";
                String staffName="";
                String staffCode = "";
                long baseID = 0;

                JSONObject params = new JSONObject();
                try{
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    token = response.get("token").toString();
                    staffName = response.get("staffName").toString();
                    staffCode = response.get("staffCode").toString();
                    baseID = Long.valueOf(response.get("baseID").toString());

                    params.put("token",token);
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);

                }catch(Exception e){
                    Crashlytics.logException(e);
                }finally {
                    dialog.dismiss();
                }
                Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
                if(Codes.ALL_OK.equals(codeReceived)){
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                    try {
                        SharedPreferences.Editor editor = activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();

                        editor.putString("token", token);
                        editor.putBoolean("isLoggedIn", true);
                        editor.putLong(Codes.CRFORMID, baseID);
                        editor.putString("staffCode", staffCode);
                        editor.apply();
                        Crashlytics.setUserName(staffName);
                        Crashlytics.setUserIdentifier(staffCode);
                        saveData(params);
                    }catch(Exception e){
                        Crashlytics.logException(e);
                    }
                }
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    public void saveData(JSONObject params){
        Util.saveData(params,this);
        Intent intent = new Intent(this, Menu.class);
        this.startActivity(intent);
        finish();
    }

}
