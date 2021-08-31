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
import com.greenstar.eagle.dal.HSData;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.QATAreaDetail;
import com.greenstar.eagle.model.QATFormHeader;
import com.greenstar.eagle.model.QATFormQuestion;
import com.greenstar.eagle.model.QATTCForm;
import com.greenstar.eagle.model.QTVForm;
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
        int isQTVAllowed = 0;
        int isQATAllowed = 0;

        String approvalStatus= "";
        try{
            data = (String) params.get("data");
            status = (String) params.get("status");
        }catch(Exception e){
            Crashlytics.logException(e);
        }

        if(Codes.ALL_OK.equals(status)){
            HSData dataObj = new Gson().fromJson(data, HSData.class) ;

            if(dataObj!=null) {
                SharedPreferences.Editor editor = activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();

                if(dataObj.getName()!=null && !"".equals(dataObj.getName()))
                    editor.putString("name", dataObj.getName());

                if(dataObj.getAMName()!=null && !"".equals(dataObj.getAMName()))
                    editor.putString("AMName", dataObj.getAMName());

                if(dataObj.getAMCode()!=null && !"".equals(dataObj.getAMCode()))
                    editor.putString("AMCode", dataObj.getAMCode());

                if(dataObj.getRegion()!=null && !"".equals(dataObj.getRegion()))
                    editor.putString("region", dataObj.getRegion());
                if(dataObj.getIsQTVAllowed()==1 ||
                        dataObj.getIsQATAllowed()==1 ||
                        dataObj.getIsQATAMAllowed()==1){
                    if( !"".equals(dataObj.getIsQTVAllowed()))
                        editor.putInt("isQTVAllowed", dataObj.getIsQTVAllowed());


                    if(dataObj.getIsQATAllowed()==1 || dataObj.getIsQATAMAllowed()==1){
                        editor.putInt("isQATAllowed", 1);
                    }else{
                        editor.putInt("isQATAllowed", 0);
                    }
                }



                editor.apply();
                try {
                    db = AppDatabase.getAppDatabase(activity);

                    if (dataObj.getQattcForms() != null && dataObj.getQattcForms().size() > 0) {
                        db.getQattcFormDAO().nukeTable();
                        db.getQattcFormDAO().insertMultiple(dataObj.getQattcForms());
                    }

                    if (dataObj.getProviders() != null && dataObj.getProviders().size() > 0) {
                        db.getProvidersDAO().nukeTable();
                        db.getProvidersDAO().insertMultiple(dataObj.getProviders());
                    }

                    if (dataObj.getAreas() != null && dataObj.getAreas().size() > 0) {
                        db.getAreaDAO().nukeTable();
                        db.getAreaDAO().insertMultiple(dataObj.getAreas());
                    }

                    if (dataObj.getQuestions() != null && dataObj.getQuestions().size() > 0) {
                        db.getQuestionsDAO().nukeTable();
                        db.getQuestionsDAO().insertMultiple(dataObj.getQuestions());
                    }

                    if (dataObj.getApprovalQATForms() != null && dataObj.getApprovalQATForms().size() > 0) {
                        db.getApprovalQATFormDAO().nukeTable();
                        db.getApprovalQATFormDAO().insertMultiple(dataObj.getApprovalQATForms());
                    }

                    if (dataObj.getApprovalQATAreas() != null && dataObj.getApprovalQATAreas().size() > 0) {
                        db.getApprovalQATAreaDAO().nukeTable();
                        db.getApprovalQATAreaDAO().insertMultiple(dataObj.getApprovalQATAreas());
                    }

                    if (dataObj.getApprovalQATFormQuestions() != null && dataObj.getApprovalQATFormQuestions().size() > 0) {
                        db.getApprovalQATFormQuestionDAO().nukeTable();
                        db.getApprovalQATFormQuestionDAO().insertMultiple(dataObj.getApprovalQATFormQuestions());
                    }

                    if (dataObj.getQtvForms() != null && dataObj.getQtvForms().size() > 0) {
                        db.getApprovalQTVFormDAO().nukeTable();
                        db.getApprovalQTVFormDAO().insertMultiple(dataObj.getQtvForms());
                    }

                    if (dataObj.getDashboard() != null) {
                        db.getDashboardDAO().nukeTable();
                        db.getDashboardDAO().insert(dataObj.getDashboard());
                    }

                    if (dataObj.getQattcForms() != null && dataObj.getQattcForms().size() > 0) {
                        db.getQattcFormDAO().nukeTable();
                        db.getQattcFormDAO().insertMultiple(dataObj.getQattcForms());
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
        if(syncType.equals(Codes.SINGLE_QAT_FORM)){
            List<QATFormHeader> qatFormHeaders = db.getQatFormHeaderDAO().getFormById(formId);
            List<Long> ids = new ArrayList<>();
            ids.add(formId);
            List<QATFormQuestion> qatFormQuestions = db.getQatFormQuestionDAO().getAllPending(ids);
            List<QATAreaDetail> qatAreaDetails = db.getAreaDetailDAO().getAllPending(ids);

            syncObject.setQtvForms(null);
            syncObject.setQatAreaDetails(qatAreaDetails);
            syncObject.setQatFormHeaders(qatFormHeaders);
            syncObject.setQatFormQuestions(qatFormQuestions);
            syncObject.setQattcForms(null);
        }else if(syncType.equals(Codes.SINGLE_QTV_FORM)){
            List<QTVForm> forms = new ArrayList<>();
            forms = db.getQTVFormDAO().getQTVFormByID(formId);
            if(forms!=null && forms.size()>0){
                syncObject.setQtvForms(forms);
            }
        }


        final String data = new Gson().toJson(syncObject);
        return data;
    }

    public static String getCTSSyncData(Activity context){
        AppDatabase db = AppDatabase.getAppDatabase(context);

        List<QTVForm> qtvForms = db.getQTVFormDAO().getAllPendingForms();
        List<QATFormHeader> qatFormHeaders = db.getQatFormHeaderDAO().getAllPending();
        List<Long> ids = getAllFormIds(qatFormHeaders);
        List<QATFormQuestion> qatFormQuestions = db.getQatFormQuestionDAO().getAllPending(ids);
        List<QATAreaDetail> qatAreaDetails = db.getAreaDetailDAO().getAllPending(ids);
        List<QATTCForm> qattcForms = db.getQattcFormDAO().getAllPending();
        SyncObject syncObject = new SyncObject();
        syncObject.setQtvForms(qtvForms);
        syncObject.setQatAreaDetails(qatAreaDetails);
        syncObject.setQatFormHeaders(qatFormHeaders);
        syncObject.setQatFormQuestions(qatFormQuestions);
        syncObject.setQattcForms(qattcForms);

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
                long qatSuccessfulId =0;
                long qatRejectedId =0;
                int SuccessfulQTVID =0;
                int RejectedQTVID= 0;
                String data = "";
                String codeReceived = "";

                try {
                    message = response.get("message").toString();

                    codeReceived = response.get("status").toString();
                    if(codeReceived.equals(Codes.ALL_OK)){
                        if(syncType.equals(Codes.SINGLE_QAT_FORM)){
                            qatSuccessfulId = Long.valueOf(response.optString("qatSuccessfulId") == null || response.optString("qatSuccessfulId").toString() == null || response.optString("qatSuccessfulId").toString()=="" ? "0":response.optString("qatSuccessfulId").toString() );
                            qatRejectedId = Long.valueOf(response.optString("qatRejectedId") == null || response.optString("qatRejectedId").toString() == null || response.optString("qatRejectedId").toString()=="" ? "0":response.optString("qatRejectedId").toString() );
                        }else if(syncType.equals(Codes.SINGLE_QTV_FORM)){
                            SuccessfulQTVID = Integer.valueOf(response.optString("SuccessfulQTVID") == null || response.get("SuccessfulQTVID")== null || response.optString("SuccessfulQTVID")=="" ? "0":response.optString("SuccessfulQTVID"));
                            RejectedQTVID = Integer.valueOf(response.optString("RejectedQTVID") == null || response.get("RejectedQTVID") == null || response.optString("RejectedQTVID")=="" ? "0":response.optString("RejectedQTVID") );
                        }

                    }

                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.log("Sync Issue at "+ new Date());
                }
                    AppDatabase db = AppDatabase.getAppDatabase(context);
                    if(syncType.equals(Codes.SINGLE_QAT_FORM)){
                        if(qatSuccessfulId!=0)
                            db.getQatFormHeaderDAO().markQATSuccessful(qatSuccessfulId);
                        if(qatRejectedId!=0)
                            db.getQatFormHeaderDAO().markQATRejected(qatRejectedId);
                    }else if(syncType.equals(Codes.SINGLE_QTV_FORM)) {
                        db.getQTVFormDAO().markQTVSuccessful(SuccessfulQTVID);
                        db.getQTVFormDAO().markQTVRejected(RejectedQTVID);
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
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);


        rp.add("data",getCTSSyncData(context));

        HttpUtils.get("hssync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String data = "";
                String codeReceived = "";
                String staffName = "";
                int isQTVAllowed = 0;
                int isQATAllowed = 0;
                int isQATAMAllowed = 0;
                JSONObject params = new JSONObject();
                List<Integer> successfulIDs = new ArrayList<>();
                List<Integer> rejectedIDs = new ArrayList<>();

                List<Long> successfulQATIDs = new ArrayList<>();
                List<Long> rejectedQATIDs = new ArrayList<>();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("status",codeReceived);
                    params.put("isQTVAllowed",isQTVAllowed);
                    params.put("isQATAllowed", isQATAllowed);
                    params.put(Codes.ISQATAMALLOWED, isQATAMAllowed);
                    for(int i=0;i<response.getJSONArray("rejectedIDs").length();i++){
                        rejectedIDs.add(response.getJSONArray("rejectedIDs").getInt(i));
                    }
                    for(int i=0;i<response.getJSONArray("successfulIDs").length();i++){
                        successfulIDs.add(response.getJSONArray("successfulIDs").getInt(i));
                    }

                    for(int i=0;i<response.getJSONArray("rejectedQATIDs").length();i++){
                        rejectedQATIDs.add(response.getJSONArray("rejectedQATIDs").getLong(i));
                    }
                    for(int i=0;i<response.getJSONArray("successfulQATIDs").length();i++){
                        successfulQATIDs.add(response.getJSONArray("successfulQATIDs").getLong(i));
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.log("Sync Issue at "+ new Date());
                    Crashlytics.logException(e);
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    updateData(successfulIDs, rejectedIDs, context);
                    updateQATForms(rejectedQATIDs,successfulQATIDs,context);
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
                db.getQTVFormDAO().markQTVSuccessful(id);
            }
            for (int id : rejectedIDs) {
                db.getQTVFormDAO().markQTVRejected(id);
            }
        }catch(Exception e){
            Crashlytics.logException(e);
        }
    }

    private void updateQATForms(List<Long> rejectedQATIDs, List<Long> successfulQATIDs, Activity activity) {
        AppDatabase db = AppDatabase.getAppDatabase(activity);
        try {
            for (long id : successfulQATIDs) {
                db.getQatFormHeaderDAO().markQATSuccessful(id);
            }
            for (long id : rejectedQATIDs) {
                db.getQTVFormDAO().markQTVRejected(id);
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

    public static long getNextID(Activity activity, int type){
        SharedPreferences editor = activity.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String idType = "";

        if(type==Codes.QTVFORM){
            idType = "qtvFormID";
        }else if(type==Codes.QATFORM){
            idType = "qatFormID";
        }
        long qtvFormID = 0;


        qtvFormID = editor.getLong(idType,0);
        qtvFormID++;
        SharedPreferences.Editor edit =editor.edit();
        edit.putLong(idType,qtvFormID);
        edit.apply();

        return qtvFormID;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static List<Long> getAllFormIds(List<QATFormHeader> qatFormHeaders){
        List<Long> formIds = new ArrayList<>();
        for(QATFormHeader qatFormHeader : qatFormHeaders){
            formIds.add(qatFormHeader.getId());
        }
        return formIds;
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