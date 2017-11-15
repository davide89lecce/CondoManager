package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiSondaggi;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Sondaggio;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
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

    TextView tStabile;
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
            nomeStabile = bundle.get("nomeStabile").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idSondaggio", idSondaggio);
            editor.putString("nomeStabile", nomeStabile);
            editor.apply();
        }
        else {
            idSondaggio = sharedPrefs.getString("idSondaggio", "").toString();
            nomeStabile = sharedPrefs.getString("nomeStabile", "").toString();
            bundle = new Bundle();
            bundle.putString("idSondaggio", idSondaggio);
            bundle.putString("nomeStabile", nomeStabile);
        }

        tStabile = (TextView) findViewById(R.id.D_SondStabile);
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

        final Query query2;
        query2 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).orderByKey().equalTo("opzioni");

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                    sondaggioMap2.put( child.getKey(), child.getValue());


                Query query3;
                query3 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).orderByKey().equalTo("scelte");

                query3.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        for (DataSnapshot child : dataSnapshot.getChildren())
                            sondaggioMap3.put( child.getKey(), child.getValue());

                        Query query4;
                        query4 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).child("partecipanti").child("num_partecipanti");

                        query4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                sondaggioMap3.put ("totPartecipanti", dataSnapshot.getValue().toString() );
                                //final Integer totPartecipanti = Integer.parseInt( dataSnapshot.getValue().toString() );

                                Query query5;
                                query5 = FirebaseDB.getStabili()
                                        .child(sondaggioMap.get("stabile").toString())
                                        .child("lista_condomini");
                                query5.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        sondaggioMap3.put ("totResidenti", dataSnapshot.getChildrenCount() );
                                        //Integer totResidenti = (int) dataSnapshot.getChildrenCount();

                                        Sondaggio sondaggio = new Sondaggio(
                                                sondaggioMap.get("id").toString(),
                                                nomeStabile,
                                                sondaggioMap.get("tipologia").toString(),
                                                sondaggioMap.get("oggetto").toString(),
                                                sondaggioMap.get("descrizione").toString(),
                                                sondaggioMap2.get("1").toString(),      // OPZIONI
                                                sondaggioMap2.get("2").toString(),      // OPZIONI
                                                sondaggioMap2.get("3").toString(),      // OPZIONI
                                                sondaggioMap3.get("1").toString(),      // Num Risposte
                                                sondaggioMap3.get("2").toString(),      // Num Risposte
                                                sondaggioMap3.get("3").toString(),      // Num Risposte
                                                sondaggioMap.get("data").toString(),
                                                sondaggioMap.get("stato").toString()
                                        );


                                        tStabile.setText(sondaggio.getStabile());
                                        tData.setText(sondaggio.getData());
                                        tOggetto.setText(sondaggio.getOggetto());
                                        tDescrizione.setText(sondaggio.getDescrizione());
                                        tTipologia.append(" "+sondaggio.getTipologia());
                                        tNumPartecipanti.setText( sondaggioMap3.get("totPartecipanti").toString() );
                                        tNumCondomini.setText(sondaggioMap3.get("totResidenti").toString());


                                        tOpzione1.setText(sondaggio.getOpzione1());
                                        tOpzione2.setText(sondaggio.getOpzione2());
                                        tOpzione3.setText(sondaggio.getOpzione3());

                                        tNumOpzione1.setText(sondaggio.getVoti1());
                                        tNumOpzione2.setText(sondaggio.getVoti2());
                                        tNumOpzione3.setText(sondaggio.getVoti3());
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
