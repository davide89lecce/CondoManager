package com.gambino_serra.condomanager_amministratore.View.SezioneStabile;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoAvviso.DialogNuovoAvviso;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoSondaggio.DialogNuovoSondaggio;
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
    String nomeStabile;

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


    static final int TIME_DIALOG_ID1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sezione_stabile);

        TitoloSezione = (TextView) findViewById(R.id.D_Stabile);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        firebaseAuth = FirebaseAuth.getInstance();


        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);


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

        // Recupero nome dello stabile per visualizzarlo sulla barra superiore
        nomeStabile = "";
        Firebase nomeRef;
        nomeRef = FirebaseDB.getStabili().child( idStabile ).child("nome");

        nomeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomeStabile = dataSnapshot.getValue(String.class);
                TitoloSezione.setText(nomeStabile);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });


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
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("foto", "-");  // se effettua la creazione di un ticket da zero, non potrà allegare foto del condomino
                editor.apply();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                materialDesignFAM.close(true);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DialogFragment newFragment = new DialogNuovoAvviso();
                newFragment.show(getFragmentManager(), "NuovoAvviso");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                materialDesignFAM.close(true);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DialogNuovoSondaggio();
                newFragment.show(getFragmentManager(), "NuovoSondaggio");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
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
                                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item1:
                                selectedFragment = BachecaMessaggi.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item2:
                                selectedFragment = BachecaSondaggi.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                bottomNavigationView.getMenu().getItem(3).setChecked(false);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item3:
                                selectedFragment = BachecaAvvisi.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                                bottomNavigationView.getMenu().getItem(4).setChecked(false);
                                break;
                            case R.id.action_item4:
                                selectedFragment = BachecaInterventi.newInstance();
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

    /**
     * Il metodo aggiorna il tempo visualizzato nelle TextView.
     */
    private void saveDataScadenza(Integer Year, Integer monthOfyear, Integer dayOfMonth) {

        String day = dayOfMonth.toString();
        String month = monthOfyear.toString();
        String year = Year.toString();

        if(dayOfMonth < 10){
           day = "0" + dayOfMonth.toString();
        }
        if(monthOfyear < 10){
            month = "0" + monthOfyear.toString();
        }

        Bundle bundle = new Bundle();
        bundle.putString("dataScadenza", day + "/" + month + "/" + year); //+ " 00:00");
        DialogFragment newFragment = new DialogNuovoAvviso();
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "NuovoAvviso");
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        materialDesignFAM.close(true);
    }

    /**
     * Il metodo viene invocata quando l'utente imposta il tempo nella Dialog e preme il bottone "OK".
     */
    private android.app.DatePickerDialog.OnDateSetListener mDateSetListener = new android.app.DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            final Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            saveDataScadenza(year, monthOfYear + 1, dayOfMonth);

        }
    };

    /**
     * Il metodo gestisce la creazione della Dialog TimePicker.
     * @param id
     * @return Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        Integer year = mCalendar.get(Calendar.YEAR);
        Integer monthOfYear = mCalendar.get(Calendar.MONTH);
        Integer dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case TIME_DIALOG_ID1:
                return new android.app.DatePickerDialog(this, mDateSetListener, year, monthOfYear, dayOfMonth);
        }
        return null;
    }



    private void recuperaNomeStabile(String idStabile) {

        Firebase nomeRef;
        nomeRef = FirebaseDB.getStabili().child( idStabile ).child("nome");

        nomeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nomeStabile = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

    }
}
