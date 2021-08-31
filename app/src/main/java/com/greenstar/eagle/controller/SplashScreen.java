package com.greenstar.eagle.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.greenstar.eagle.controller.qat.QATMenu;
import com.greenstar.eagle.controller.qtv.QTVMenu;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FirebaseMessaging.getInstance().subscribeToTopic("updateMapping");
        FirebaseMessaging.getInstance().subscribeToTopic("syncAll");
        FirebaseMessaging.getInstance().subscribeToTopic("generalMessage");

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
        int isQATAllowed = (shared.getInt("isQATAllowed", 0));
        int isQTVAllowed = (shared.getInt("isQTVAllowed", 0));
        int isQATAMAllowed = (shared.getInt("isQATAMAllowed", 0));
        Intent intent = null;
        if(isLoggedIn){
            if(isQTVAllowed==1 && isQATAllowed==1){
                intent = new Intent(getApplicationContext(),
                        FormMenu.class);

            }else if(isQTVAllowed==0 && isQATAllowed==1){
                intent = new Intent(getApplicationContext(),
                        QATMenu.class);

            }else if(isQTVAllowed==1 && isQATAllowed==0){
                intent = new Intent(getApplicationContext(),
                        QTVMenu.class);

            }else{
                intent = new Intent(getApplicationContext(),
                        FormMenu.class);

            }
        }else{
            intent = new Intent(this,
                    LoginScreen.class);


        }
        startActivity(intent);
        finish();
    }
}