package com.gambino_serra.condomanager_amministratore.View.Login;//package com.gambino_serra.condomanager_amministratore.View;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * La classe modella lo splash screen di avvio dell'applicazione Kiu.
 */
public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;      //Oggetto per l'autenticazione
    private FirebaseUser utente;            //oggetto per definire l'utente del DB
    private Firebase userRef;               //posso conservare altri riferimenti ad oggetto che punto a piacere

    private final int SPLASH_DISPLAY_LENGTH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                firebaseAuth = FirebaseAuth.getInstance();
                if (firebaseAuth.getCurrentUser() != null) {
                    // PRENDO IL RIFERIMENTO DELL'UTENTE LOGGATO

                    //controllo nel caso in cui l'utente sia loggato con un altra app che utilizza lo stesso DB
                    checkLogin( firebaseAuth.getCurrentUser().getUid().toString() );
                }else
                {
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkLogin(final String UID) {

        userRef = FirebaseDB.getAmministratori().child(UID);
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ( dataSnapshot.exists() )
                {
                    Intent in = new Intent(getApplicationContext(), MainDrawer.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
                else
                {
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }
}