package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Sondaggi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RatingBar;
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

public class DettaglioSondaggioRating extends AppCompatActivity {

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
    TextView tMedia;
    TextView tNumCondomini;
    TextView tNumPartecipanti;
    RatingBar tRatingBar;
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
        setContentView(R.layout.dettaglio_sondaggio_rating);

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
        tMedia = (TextView) findViewById(R.id.sondMedia);
        tNumCondomini = (TextView) findViewById(R.id.sondNumCondomini);
        tNumPartecipanti = (TextView) findViewById(R.id.sondNumPartecipanti);
        tRatingBar = (RatingBar) findViewById(R.id.ratingBarSondaggio);
        btnChiudi = (ConstraintLayout) findViewById(R.id.btnChiudi);

        // nella sezionestabile la card stabile non serve
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

        Query query2;
        query2 = FirebaseDB.getSondaggi().child( sondaggioMap.get("id").toString() ).orderByKey().equalTo("risposte");

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                    sondaggioMap2.put( child.getKey(), child.getValue());


                Query query3;
                query3 = FirebaseDB.getStabili()
                        .child(sondaggioMap.get("stabile").toString())
                        .child("lista_condomini");
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Integer totResidenti = (int) dataSnapshot.getChildrenCount();

                        Sondaggio sondaggio = new Sondaggio(
                                sondaggioMap.get("id").toString(),
                                sondaggioMap.get("stabile").toString(),
                                sondaggioMap.get("tipologia").toString(),
                                sondaggioMap.get("oggetto").toString(),
                                sondaggioMap.get("descrizione").toString(),
                                sondaggioMap2.get("somma").toString(),      //nel campo opzione1 mettiamo la somma delle votazioni
                                sondaggioMap2.get("num_risposte").toString(),//nel campo opzione2 il numero dei votanti
                                totResidenti.toString(),                    // nel campo opzione3 il numero totale dei condomini
                                sondaggioMap.get("data").toString(),
                                sondaggioMap.get("stato").toString()
                        );


                        tData.setText(sondaggio.getData());
                        tOggetto.setText(sondaggio.getOggetto());
                        tDescrizione.setText(sondaggio.getDescrizione());
                        tNumPartecipanti.setText( sondaggio.getOpzione2().toString() );

                        Float somma = Float.parseFloat(sondaggio.getOpzione1());
                        Float partecipanti = Float.parseFloat( sondaggio.getOpzione2() );
                        Float risultato = somma / partecipanti;
                        tRatingBar.setRating( Float.parseFloat(risultato.toString()) );

                        tMedia.setText( risultato.toString() );
                        tNumCondomini.setText(sondaggio.getOpzione3());


                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
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