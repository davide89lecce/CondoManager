package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoSondaggio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.SezioneStabile;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Date;
import java.util.Map;

public class SondaggioSceltaSingola extends  AppCompatActivity{

    private static final String MY_PREFERENCES = "preferences";

    EditText editText_Oggetto;
    EditText editText_Descrizione;
    EditText editText_Risp1;
    EditText editText_Risp2;
    EditText editText_Risp3;

    TextView Tstabile;
    ConstraintLayout btnAnnulla;
    ConstraintLayout btnConferma;


    String uidFornitore;
    String categoria;
    String uidAmministratore;
    String idStabile;
    String oggetto;
    String descrizione;
    String scelta1;
    String scelta2;
    String scelta3;
    boolean campiOK = false;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    Map<String, Object> dettaglioFornitoreMap;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sondaggio_scelta_singola);

        firebaseAuth = FirebaseAuth.getInstance();
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        idStabile = sharedPrefs.getString("idStabile", "").toString();

        Tstabile = (TextView) findViewById(R.id.D_Stabile);
        editText_Oggetto = (EditText) findViewById(R.id.editText_Oggetto);
        editText_Descrizione = (EditText) findViewById(R.id.editText_Descrizione);
        editText_Risp1 = (EditText) findViewById(R.id.editText_Risp1);
        editText_Risp2 = (EditText) findViewById(R.id.editText_Risp2);
        editText_Risp3 = (EditText) findViewById(R.id.editText_Risp3);
        btnConferma = (ConstraintLayout) findViewById(R.id.btnConferma);
        btnAnnulla = (ConstraintLayout) findViewById(R.id.btnAnnulla);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                oggetto = editText_Oggetto.getText().toString();
                descrizione = editText_Descrizione.getText().toString();
                scelta1 = editText_Risp1.getText().toString();
                scelta2 = editText_Risp2.getText().toString();
                scelta3 = editText_Risp3.getText().toString();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Sondaggi");

                if ( ! oggetto.isEmpty() || ! descrizione.isEmpty()
                        || !scelta1.isEmpty() || !scelta2.isEmpty() || !scelta3.isEmpty()) {
                    addSondaggioSceltaSingola(databaseReference, oggetto, descrizione, scelta1, scelta2, scelta3, idStabile);
                    Intent intent = new Intent(getApplicationContext(), SezioneStabile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(SondaggioSceltaSingola.this, "Assicurarsi di aver compilato tutti i campi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        Query query;
        query = FirebaseDB.getStabili().child(idStabile).child("nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeStabile;
                nomeStabile = dataSnapshot.getValue(String.class);
                Tstabile.setText(nomeStabile);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    // TODO recupera messaggio condomino
    private void addSondaggioSceltaSingola(DatabaseReference postRef, final String oggetto, final String descrizione,
                                           final String scelta1, final String scelta2, final String scelta3, final String idStabile) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                //Legge il valore del nodo counter
                Integer counter = mutableData.child("counter").getValue(Integer.class);

                if (counter == null) {
                    return Transaction.success(mutableData);
                }

                //Incrementa counter
                counter = counter + 1;

                //Ricava la data e la formatta nel formato appropriato
                Date newDate = new Date(new Date().getTime());
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
                String stringdate = dt.format(newDate);


                //Setta il nome del nodo del messaggio (key)
                mutableData.child(counter.toString()).child("amministratore").setValue(uidAmministratore);
                mutableData.child(counter.toString()).child("oggetto").setValue(oggetto);
                mutableData.child(counter.toString()).child("descrizione").setValue(descrizione);
                mutableData.child(counter.toString()).child("opzioni").child("1").setValue(scelta1);
                mutableData.child(counter.toString()).child("opzioni").child("2").setValue(scelta2);
                mutableData.child(counter.toString()).child("opzioni").child("3").setValue(scelta3);
                mutableData.child(counter.toString()).child("stabile").setValue(idStabile);
                mutableData.child(counter.toString()).child("data").setValue(stringdate);
                mutableData.child(counter.toString()).child("stato").setValue("aperto");

                mutableData.child(counter.toString()).child("tipologia").setValue("scelta singola");
                mutableData.child(counter.toString()).child("scelte").child("1").setValue(0);
                mutableData.child(counter.toString()).child("scelte").child("2").setValue(0);
                mutableData.child(counter.toString()).child("scelte").child("3").setValue(0);
                mutableData.child(counter.toString()).child("partecipanti").child("num_partecipanti").setValue(0);

                //Setta il counter del nodo Messaggi_condomino
                mutableData.child("counter").setValue(counter);


                return Transaction.success(mutableData);

            }


            @Override
            public void onComplete(DatabaseError databaseError, boolean b, com.google.firebase.database.DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}
