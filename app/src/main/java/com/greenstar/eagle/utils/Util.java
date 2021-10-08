package com.greenstar.eagle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.dal.EagleData;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.SyncObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class Util {
   WebserviceResponse responseListener;
   PartialSyncResponse PSResponse;

    public static void saveData(JSONObject params, Context activity){
        AppDatabase db =null;

        String data = "";
        String status = "";

        String approvalStatus= "";
        try{
            data = (String) params.get("data");
            status = (String) params.get("status");
        }catch(Exception e){
            Crashlytics.logException(e);
        }

        if(Codes.ALL_OK.equals(status)){
            EagleData dataObj = new Gson().fromJson(data, EagleData.class) ;

            if(dataObj!=null) {
                SharedPreferences.Editor editor = activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();

                if(dataObj.getSitaraBajiName()!=null && !"".equals(dataObj.getSitaraBajiName()))
                    editor.putString("sitaraBajiName", dataObj.getSitaraBajiName());

                if(dataObj.getSitaraBajiCode()!=null && !"".equals(dataObj.getSitaraBajiCode()))
                    editor.putString("sitaraBajiCode", dataObj.getSitaraBajiCode());

                if(dataObj.getAMName()!=null && !"".equals(dataObj.getAMName()))
                    editor.putString("AMName", dataObj.getAMName());

                if(dataObj.getAMCode()!=null && !"".equals(dataObj.getAMCode()))
                    editor.putString("AMCode", dataObj.getAMCode());

                if(dataObj.getRegion()!=null && !"".equals(dataObj.getRegion()))
                    editor.putString("region", dataObj.getRegion());

                if(dataObj.getDistrict()!=null && !"".equals(dataObj.getDistrict()))
                    editor.putString("district", dataObj.getDistrict());

                if(dataObj.getProviderCode()!=null && !"".equals(dataObj.getProviderCode()))
                    editor.putString("providerCode", dataObj.getProviderCode());

                if(dataObj.getProviderName()!=null && !"".equals(dataObj.getProviderName()))
                    editor.putString("providerName", dataObj.getProviderName());

                editor.apply();
                try {
                    db = AppDatabase.getAppDatabase(activity);

                    if (dataObj.getDropdownCRBData() != null && dataObj.getDropdownCRBData().size() > 0) {
                        db.getDropdownCRBDataDAO().nukeTable();
                        db.getDropdownCRBDataDAO().insertMultiple(dataObj.getDropdownCRBData());
                    }
                } catch (Exception e) {
                    Toast.makeText(activity, "Something went wrong. Please sync later", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void pullMapping(final Context context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);

        SyncObject syncObject = new SyncObject();

        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);

        HttpUtils.get("hssync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String data = "";
                String codeReceived = "";
                String staffName = "";
                JSONObject params = new JSONObject();
                List<Integer> successfulIDs = new ArrayList<>();
                List<Integer> rejectedIDs = new ArrayList<>();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    staffName = response.get("staffName").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);

                    for(int i=0;i<response.getJSONArray("rejectedIDs").length();i++){
                        rejectedIDs.add(response.getJSONArray("rejectedIDs").getInt(i));
                    }
                    for(int i=0;i<response.getJSONArray("successfulIDs").length();i++){
                        successfulIDs.add(response.getJSONArray("successfulIDs").getInt(i));
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.logException(e);
                    Crashlytics.log("Sync Issue on pulling mapping at "+ new Date());
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    saveData(params, context);
                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Crashlytics.log("Sync successful but not handled  "+ new Date());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseListener.responseAlert(Codes.SOMETHINGWENTWRONG);
                Crashlytics.log("Sync Issue in pulling mapping on Failure "+ new Date());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseListener.responseAlert(Codes.TIMEOUT);
                Crashlytics.log("Sync Issue on pulling mapping on Timeout  "+ new Date());
            }
        });

    }

    public static String getSingleFormData(Activity context, long formId, String syncType){
        AppDatabase db = AppDatabase.getAppDatabase(context);
        SyncObject syncObject = new SyncObject();
        if(syncType.equals(Codes.SINGLE_CR_FORM)){
            CRForm crForm = db.getCrFormDAO().getFormByID(formId);
            List<CRForm> crForms = new ArrayList<>();
            crForms.add(crForm);
            List<Long> ids = new ArrayList<>();
            ids.add(formId);
            syncObject.setCrForms(crForms);
        }

        final String data = new Gson().toJson(syncObject);
        return data;
    }

    public static String getCTSSyncData(Activity context){
        AppDatabase db = AppDatabase.getAppDatabase(context);

        List<CRForm> crForms = db.getCrFormDAO().getAllPendingForms();

        SyncObject syncObject = new SyncObject();
        syncObject.setCrForms(crForms);

        final String data = new Gson().toJson(syncObject);
        return data;
    }

    public void performSingleFormSync(final Activity context, long formId, final String syncType){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("syncType", syncType);
        rp.add("token",token);
        rp.add("data",getSingleFormData(context,formId,syncType));

        HttpUtils.get("singleFormSync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                long crfSuccessfulId =0;
                long crfRejectedId =0;
                String data = "";
                String codeReceived = "";

                try {
                    message = response.get("message").toString();

                    codeReceived = response.get("status").toString();
                    if(codeReceived.equals(Codes.ALL_OK)){
                        if(syncType.equals(Codes.SINGLE_CR_FORM)){
                            crfSuccessfulId = Long.valueOf(response.optString("crfSuccessfulId") == null || response.optString("crfSuccessfulId").toString() == null || response.optString("crfSuccessfulId").toString()=="" ? "0":response.optString("crfSuccessfulId").toString() );
                            crfRejectedId = Long.valueOf(response.optString("crfRejectedId") == null || response.optString("crfRejectedId").toString() == null || response.optString("crfRejectedId").toString()=="" ? "0":response.optString("crfRejectedId").toString() );
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.log("Sync Issue at "+ new Date());
                }
                    AppDatabase db = AppDatabase.getAppDatabase(context);
                    if(syncType.equals(Codes.SINGLE_CR_FORM)){
                        if(crfSuccessfulId!=0)
                            db.getCrFormDAO().markSuccessful(crfSuccessfulId);
                        if(crfRejectedId!=0)
                            db.getCrFormDAO().markRejected(crfRejectedId);
                    }

                responseListener.responseAlert(message);
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

    public void performSync(final Activity context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("syncType", Codes.PullAllEagleData);
        rp.add("token",token);
        rp.add("data",getCTSSyncData(context));

        HttpUtils.get("syncEagle", rp, new JsonHttpResponseHandler() {
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
                    params.put("message", message);
                    params.put("data", data);
                    params.put("status",codeReceived);

                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.log("Sync Issue at "+ new Date());
                    Crashlytics.logException(e);
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    saveData(params, context);
                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Crashlytics.log("Sync Successful but not handled at "+ new Date());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseListener.responseAlert(Codes.SOMETHINGWENTWRONG);
                Crashlytics.log("Sync failed and got Something went wrong at  "+ new Date());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseListener.responseAlert(Codes.TIMEOUT);
                Crashlytics.log("Sync failed and Timeout at "+ new Date());
            }
        });
    }

    private void updateData(List<Integer> successfulIDs, List<Integer> rejectedIDs, Activity activity) {
        AppDatabase db = AppDatabase.getAppDatabase(activity);
        try {
            for (int id : successfulIDs) {
                db.getCrFormDAO().markSuccessful(id);
            }
            for (int id : rejectedIDs) {
                db.getCrFormDAO().markRejected(id);
            }
        }catch(Exception e){
            Crashlytics.logException(e);
        }
    }

    public WebserviceResponse getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(WebserviceResponse responseListener) {
        this.responseListener = responseListener;
    }

    public PartialSyncResponse getPSResponse() {
        return PSResponse;
    }

    public void setPSResponse(PartialSyncResponse PSResponse) {
        this.PSResponse = PSResponse;
    }

    public static long getNextID(Activity activity, String type){
        SharedPreferences editor = activity.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);

        long id = 0;

        id = editor.getLong(type,0);
        id++;
        SharedPreferences.Editor edit =editor.edit();
        edit.putLong(type,id);
        edit.apply();

        return id;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void performPSync(final Activity context,String PSType){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("token",token);
        rp.add("PSType",PSType);

        HttpUtils.get("PSBasicInfo", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String codeReceived = "";
                String data = "";
                String staffName = "";
                String PSType = "";
                JSONObject params = new JSONObject();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    staffName = response.get("staffName").toString();
                    PSType = response.get("PSType").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);
                    params.put("PSType",PSType);
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.log("Sync Issue at "+ new Date());
                    codeReceived=Codes.SOMETHINGWENTWRONG;
                }
                if(codeReceived.equals(Codes.ALL_OK))
                    saveData(params,context);
                PSResponse.response(codeReceived,PSType,message);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                PSResponse.response(Codes.SOMETHINGWENTWRONG,"","");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                PSResponse.response(Codes.SOMETHINGWENTWRONG,"","");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                PSResponse.response(Codes.TIMEOUT,"","");
            }
        });
    }
}