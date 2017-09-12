package com.gambino_serra.condomanager_amministratore.View.Home.InterventiInCorso.InterventoInCorso.RapportiIntervento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardRapportoIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.View.Home.InterventiInCorso.InterventoInCorso.InterventoInCorso;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Date;

public class InserimentoRapportoIntervento extends AppCompatActivity {
    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    EditText notaAmministratore;
    EditText notaPersonale;
    ConstraintLayout conferma;
    ConstraintLayout annulla;

    FirebaseAuth firebaseAuth;
    Firebase firebase;
    Firebase firebaseIntervento;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Bundle bundle;
    String idIntervento;
    String chiudi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserimento_rapporto_intervento);

        firebaseAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDB.getRapporti();
        firebaseIntervento = FirebaseDB.getInterventi();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        //final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        //username = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null)
        {
            bundle = getIntent().getExtras();
            idIntervento = bundle.get("idIntervento").toString();
            chiudi = bundle.get("Chiudi").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idIntervento", idIntervento);
            editor.apply();
        }
        else
        {
            idIntervento = sharedPrefs.getString("idIntervento", "").toString();
            bundle = new Bundle();
            bundle.putString("idIntervento", idIntervento);
        }

        notaAmministratore = (EditText) findViewById(R.id.textViewNotaAmministratore);
        notaPersonale = (EditText) findViewById(R.id.textViewNotaPersonale);
        conferma = (ConstraintLayout) findViewById(R.id.btnConferma);
        annulla = (ConstraintLayout) findViewById(R.id.btnAnnulla);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Rapporti_intervento");

    }



    @Override
    public void onStart() {
        super.onStart();

        conferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                addCardRapporto( databaseReference,
                                    notaAmministratore.getText().toString(),
                                    notaPersonale.getText().toString(),
                                    idIntervento);

                if(chiudi.equals("si")){
                    chiudiIntervento( idIntervento );
                }

                Toast.makeText(getApplicationContext(), "Rapporto di intervento inserito", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplicationContext(), MainDrawer.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent back = new Intent(getApplicationContext(), InterventoInCorso.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });
    }




    private void chiudiIntervento( String idIntervento ){

        firebaseIntervento.child(idIntervento).child("stato").setValue("completato");

    }

    private void addCardRapporto(DatabaseReference postRef, final String notaAmministratore, final String notaPersonale, final String idTicket) {
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
                SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String stringdate = dt.format(newDate);

                //Instanziamo un nuovo oggetto MessaggioCondomino contenente tutte le informazioni
                //per la creazione di un nuovo nodo Messaggi_condomino su Firebase
                CardRapportoIntervento m = new CardRapportoIntervento(counter.toString(),stringdate, notaAmministratore, notaPersonale, idTicket);

                //Setta il nome del nodo del messaggio (key)
                mutableData.child(counter.toString()).child("data").setValue( m.getData() );
                mutableData.child(counter.toString()).child("nota_amministratore").setValue( m.getNotaAmministratore() );
                mutableData.child(counter.toString()).child("nota_fornitore").setValue( m.getNotaFornitore() );
                mutableData.child(counter.toString()).child("ticket_intervento").setValue( m.getTicketIntervento() );

                //Setta il counter del nodo Messaggi_condomino
                mutableData.child("counter").setValue(counter);


                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

}
