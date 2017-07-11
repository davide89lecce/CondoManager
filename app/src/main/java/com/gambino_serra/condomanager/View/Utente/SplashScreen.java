package com.gambino_serra.condomanager.View.Utente;

/**
 * Created by condomanager on 10/03/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.gambino_serra.condomanager.tesi.R;

/**
 * La classe modella lo splash screen di avvio dell'applicazione Kiu.
 */
public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        ImageView img = (ImageView) findViewById(R.id.icon_img);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this, LoginActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}