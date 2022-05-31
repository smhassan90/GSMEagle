package com.greenstar.eagle.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
        Intent intent = null;
        if(isLoggedIn){
            intent = new Intent(getApplicationContext(),
                    Menu.class);

        }else{
            intent = new Intent(this,
                    LoginScreen.class);


        }
        startActivity(intent);
        finish();
    }
}