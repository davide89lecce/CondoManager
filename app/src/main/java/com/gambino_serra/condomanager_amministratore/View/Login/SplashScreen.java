package com.gambino_serra.condomanager_amministratore.View.Login;//package com.gambino_serra.condomanager_amministratore.View;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.ImageView;
//
//import com.gambino_serra.condomanager_amministratore.tesi.R;
//
///**
// * La classe modella lo splash screen di avvio dell'applicazione Kiu.
// */
//public class SplashScreen extends AppCompatActivity {
//
//    private final int SPLASH_DISPLAY_LENGTH = 500;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash_screen_layout);
//
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_logo);
//
//        ImageView img = (ImageView) findViewById(R.id.icon_img);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent in = new Intent(SplashScreen.this, LoginActivity_old.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
//                finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
//    }
//}