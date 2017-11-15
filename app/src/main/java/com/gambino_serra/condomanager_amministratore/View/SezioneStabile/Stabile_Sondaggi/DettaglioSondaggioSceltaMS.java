package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Sondaggi;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.SondaggioSceltaSM;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DettaglioSondaggioSceltaMS extends AppCompatActivity {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String username = "";

    private FirebaseDB firebaseDB;
    private Firebase dbMessaggi;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorage;

    TextView tData;
    TextView tOggetto;
    TextView tDescrizione;
    TextView tNumCondomini;
    TextView tNumPartecipanti;
    TextView tOpzione1;
    TextView tOpzione2;
    TextView tOpzione3;
    TextView tNumOpzione1;
    TextView tNumOpzione2;
    TextView tNumOpzione3;
    TextView tTipologia;
    ConstraintLayout btnChiudi;

    CardView cardStabile;

    Bundle bundle;
    String idSondaggio;
    String nomeStabile;
    float risultato;
    Map<String, Object> SondaggioMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_sondaggio_scelta_multipla);

        firebaseAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            idSondaggio = bundle.get("idSondaggio").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idSondaggio", idSondaggio);
            editor.apply();
        }
        else {
            idSondaggio = sharedPrefs.getString("idSondaggio", "").toString();
            bundle = new Bundle();
            bundle.putString("idSondaggio", idSondaggio);
        }

        tData = (TextView) findViewById(R.id.D_SondData);
        tOggetto = (TextView) findViewById(R.id.D_SondOggetto);
        tDescrizione = (TextView) findViewById(R.id.D_SondDescrizione);
        tNumCondomini = (TextView) findViewById(R.id.sondNumCondomini);
        tNumPartecipanti = (TextView) findViewById(R.id.sondNumPartecipanti);
        tTipologia = (TextView) findViewById(R.id.textView31);
        tOpzione1 = (TextView) findViewById(R.id.Opzione1);
        tOpzione2 = (TextView) findViewById(R.id.Opzione2);
        tOpzione3 = (TextView) findViewById(R.id.Opzione3);
        tNumOpzione1 = (TextView) findViewById(R.id.numOpzione1);
        tNumOpzione2 = (TextView) findViewById(R.id.numOpzione2);
        tNumOpzione3 = (TextView) findViewById(R.id.numOpzione3);
        btnChiudi = (ConstraintLayout) findViewById(R.id.btnChiudi);

        cardStabile = (CardView) findViewById(R.id.cardView);
        cardStabile.setVisibility(View.GONE);

        btnChiudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase sondRef = FirebaseDB.getSondaggi().child(idSondaggio).child("stato");
                sondRef.setValue("chiuso");
            }
        });


        Query prova;
        prova = FirebaseDB.getSondaggi().orderByKey().equalTo(idSondaggio);

        prova.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String temp = dataSnapshot.getKey().toString();

                SondaggioMap = new HashMap<String, Object>();
                SondaggioMap.put("id", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    SondaggioMap.put(child.getKey(), child.getValue());
                }

                RecuperaVoti( SondaggioMap );

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }

        });

    }


    private void RecuperaVoti ( final Map<String,Object> sondaggioMap ) {

        final Map<String, Object> sondaggioMap2 = new HashMap<String, Object>();
        final Map<String, Object> sondaggioMap3 = new HashMap<String, Object>();
        final Map<String, Object> sondaggioMap4 = new HashMap<String, Object>();

        final Query query2;
        query2 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).orderByKey().equalTo("opzioni");

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    sondaggioMap2.put(child.getKey(), child.getValue());
                }

                Query query3;
                query3 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).orderByKey().equalTo("scelte");

                query3.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){

                            sondaggioMap3.put(child.getKey(), child.getValue());
                    }
                        Query query4;
                        query4 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).child("partecipanti").child("num_partecipanti");

                        query4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                sondaggioMap4.put ("totPartecipanti", dataSnapshot.getValue().toString() );
                                //final Integer totPartecipanti = Integer.parseInt( dataSnapshot.getValue().toString() );

                                Query query5;
                                query5 = FirebaseDB.getStabili()
                                        .child(sondaggioMap.get("stabile").toString())
                                        .child("lista_condomini");
                                query5.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        sondaggioMap4.put ("totResidenti", dataSnapshot.getChildrenCount() );
                                        //Integer totResidenti = (int) dataSnapshot.getChildrenCount();

                                        SondaggioSceltaSM sondaggio = new SondaggioSceltaSM(
                                                sondaggioMap.get("id").toString(),
                                                nomeStabile,
                                                sondaggioMap.get("tipologia").toString(),
                                                sondaggioMap.get("oggetto").toString(),
                                                sondaggioMap.get("descrizione").toString(),
                                                sondaggioMap2,
                                                sondaggioMap3,
                                                sondaggioMap.get("data").toString(),
                                                sondaggioMap.get("stato").toString()
                                        );



                                        tData.setText(sondaggio.getData());
                                        tOggetto.setText(sondaggio.getOggetto());
                                        tDescrizione.setText(sondaggio.getDescrizione());
                                        tTipologia.append(" "+sondaggio.getTipologia());
                                        tNumPartecipanti.setText( sondaggioMap4.get("totPartecipanti").toString() );
                                        tNumCondomini.setText(sondaggioMap4.get("totResidenti").toString());


                                        final LinearLayout llscelte = (LinearLayout) findViewById(R.id.linearOpzioni1);
                                        llscelte.setOrientation(LinearLayout.VERTICAL);
                                        final LinearLayout llopzioni = (LinearLayout) findViewById(R.id.linearOpzioni2);
                                        llopzioni.setOrientation(LinearLayout.VERTICAL);


                                        // Costruisce l'iteratore con il metodo dedicato
                                        Iterator opzioni = sondaggioMap2.entrySet().iterator();
                                        // Verifica con il metodo hasNext() che nella hashmap
                                        // ci siano altri elementi su cui ciclare
                                        while (opzioni.hasNext()) {
                                            // Utilizza il nuovo elemento (coppia chiave-valore)
                                            // dell'hashmap
                                            Map.Entry entry = (Map.Entry)opzioni.next();


                                            TextView opzione = new TextView(getApplicationContext());
                                            opzione.setText(entry.getValue().toString());
                                            opzione.setId(Integer.parseInt(entry.getKey().toString()));
                                            llopzioni.addView(opzione);

                                            TextView scelta = new TextView(getApplicationContext());
                                            scelta.setText(sondaggioMap3.get(entry.getKey()).toString());
                                            scelta.setId(Integer.parseInt(entry.getKey().toString()));
                                            llscelte.addView(scelta);
                                        }

                                    }


                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {}
                        });

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });


    }













}
