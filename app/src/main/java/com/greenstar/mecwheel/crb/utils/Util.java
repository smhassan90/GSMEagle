package com.greenstar.mecwheel.crb.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greenstar.mecwheel.crb.controller.Codes;
import com.greenstar.mecwheel.crb.db.AppDatabase;
import com.greenstar.mecwheel.crb.model.CRBData;
import com.greenstar.mecwheel.crb.model.CRBForm;
import com.greenstar.mecwheel.crb.model.Dashboard;
import com.greenstar.mecwheel.crb.model.DropdownCRBData;
import com.greenstar.mecwheel.crb.model.SyncObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class Util {
   WebserviceResponse responseListener;

    public static void saveData(JSONObject params, Activity activity){
        AppDatabase db =null;

        String data = "";
        String status = "";
        String approvalStatus= "";

        try{
            data = (String) params.get("data");
            status = (String) params.get("status");
        }catch(Exception e){
            Log.e("Error","Error while saving data after login");
        }

        if(Codes.ALL_OK.equals(status)){
            CRBData dataObj = null;
            try{
                dataObj = new Gson().fromJson(data, CRBData.class) ;
            }catch(Exception e ){
                Toast.makeText(activity,"Error parsing : "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }


            SharedPreferences.Editor editor =  activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();
            editor.putString("name", dataObj.getName());
            editor.putString("code", dataObj.getCode());
            editor.apply();
            try{
                db = AppDatabase.getAppDatabase(activity);
                List<CRBForm> crbForms = dataObj.getCrbForms();
                Dashboard dashboard = dataObj.getDashboard();
                List<DropdownCRBData> dropdownCRBData = dataObj.getDropdownCRBData();
                if(crbForms!=null && crbForms.size()>0){
                    db.getCRBFormDAO().nukeTable();
                    db.getCRBFormDAO().insertMultiple(crbForms);
                }
                if(dashboard!=null){
                    db.getDashboardDAO().nukeTable();
                    db.getDashboardDAO().insertMultiple(dashboard);
                }

                if(dropdownCRBData!=null && dropdownCRBData.size()>0){
                    db.getDropdownCRBDataDAO().nukeTable();
                    db.getDropdownCRBDataDAO().insertMultiple(dropdownCRBData);
                }

            }catch(Exception e){
                Toast.makeText(activity,"Something went wrong. Please sync later",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void performSync(final Activity context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("token",token);

        AppDatabase db = AppDatabase.getAppDatabase(context);

        List<CRBForm> forms = db.getCRBFormDAO().getPendingCRBForms();
        SyncObject syncObject = new SyncObject();
        syncObject.setCrbForms(forms);
        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);

        JSONObject response = null;

        HttpUtils.get("crbsync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String data = "";
                String codeReceived = "";
                String staffName = "";
                JSONObject params = new JSONObject();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    staffName = response.get("providerName").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);

                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    saveData(params, context);
                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseListener.responseAlert(Codes.SOMETHINGWENTWRONG);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseListener.responseAlert(Codes.TIMEOUT);
            }
        });
    }

    private void updateData(List<Integer> successfulIDs, List<Integer> rejectedIDs, Activity activity) {
        AppDatabase db = AppDatabase.getAppDatabase(activity);
        for(int id : successfulIDs){
           // db.getDTCFormDAO().markQTVSuccessful(id);
        }
    }

    public WebserviceResponse getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(WebserviceResponse responseListener) {
        this.responseListener = responseListener;
    }

    public static long getNextCRBFormID(Activity activity){
        SharedPreferences editor = activity.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        long crbFormID =  editor.getLong("crbFormID",0);
        crbFormID++;
        SharedPreferences.Editor edit =editor.edit();
        edit.putLong("crbFormID",crbFormID);
        edit.apply();

        return crbFormID;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}