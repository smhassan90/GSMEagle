package com.greenstar.eagle.service;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.greenstar.eagle.controller.Codes;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getFrom().equals("/topics/updateMapping")){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor =  getApplicationContext().getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();

                    editor.putBoolean("updateMapping", true);
                    editor.apply();
                }
            });
        }else if(remoteMessage.getFrom().equals("/topics/syncAll")){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor =  getApplicationContext().getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();

                    editor.putBoolean("syncAll", true);
                    editor.apply();
                }
            });
        }

    }
}
