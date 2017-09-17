package com.gambino_serra.condomanager_amministratore.View.SezioneStabile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoTicketIntervento.SelezionaCategoriaIntervento;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Avvisi.BachecaAvvisi;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.BachecaInterventi;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Messaggi.BachecaMessaggi;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Sondaggi.BachecaSondaggi;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SezioneStabile extends AppCompatActivity {
    private static final String MY_PREFERENCES = "preferences";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView TitoloSezione;
    FrameLayout fl;

    Bundle bundle;
    String idStabile;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private String mParam1;
    private String mParam2;

    BottomNavigationView bottomNavigationView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sezione_stabile);

        TitoloSezione = (TextView) findViewById(R.id.D_Stabile);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        firebaseAuth = FirebaseAuth.getInstance();


        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        //username = sharedPrefs.getString(LOGGED_USER, "").toString();


        // RECUPERO L'ID DELLO STABILE SELEZIONATO PRECEDENTEMENTE PER VISUALIZZARNE IL DETTAGLIO
        if (getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            idStabile = bundle.get("idStabile").toString(); // prende l'identificativo per fare il retrieve delle info
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idStabile", idStabile);
            editor.apply();
        } else {
            idStabile = sharedPrefs.getString("idStabile", "").toString();
            bundle = new Bundle();
            bundle.putString("idStabile", idStabile);
        }

        //Floating button
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        materialDesignFAM.hideMenu(true);
        materialDesignFAM.setClosedOnTouchOutside(true);
        materialDesignFAM.showMenu(true);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelezionaCategoriaIntervento.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                materialDesignFAM.close(true);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                materialDesignFAM.close(true);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                materialDesignFAM.close(true);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item0:
                                selectedFragment = BachecaInformazioniStabile.newInstance();
                                TitoloSezione.setText("INFORMAZONI STABILE");
                                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item1:
                                selectedFragment = BachecaMessaggi.newInstance();
                                TitoloSezione.setText("MESSAGGI");
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item2:
                                selectedFragment = BachecaSondaggi.newInstance();
                                TitoloSezione.setText("BACHECA SONDAGGI");
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item3:
                                selectedFragment = BachecaAvvisi.newInstance();
                                TitoloSezione.setText("BACHECA AVVISI");
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item4:
                                selectedFragment = BachecaInterventi.newInstance();
                                TitoloSezione.setText("BACHECA INTERVENTI");
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(true);
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();

                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BachecaInformazioniStabile.newInstance());
        transaction.commit();
        //Seleziona l'item interventi in corso
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);
        bottomNavigationView.getMenu().getItem(4).setChecked(false);


    }
}
