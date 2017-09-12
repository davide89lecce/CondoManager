package com.gambino_serra.condomanager_amministratore;

import android.app.Application;

import com.firebase.client.Firebase;

public class condomanager_amministratore extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
