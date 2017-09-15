package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoTicketIntervento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreaTicketFinale extends AppCompatActivity {
    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    TextView Tnome_azienda;
    TextView Tnome;
    TextView Tcategoria;
    TextView Ttelefono;
    TextView Tpartita_iva;
    TextView Temail;
    TextView Tindirizzo;
    Button btnAnnulla;
    Button btnConferma;
    String uidFornitore;
    String telefono;
    String categoria;
    String uidAmministratore;

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
        setContentView(R.layout.crea_ticket_finale);

        firebaseAuth = FirebaseAuth.getInstance();
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        //username = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            uidFornitore = bundle.get("uidFornitore").toString(); // prende l'identificativo per fare il retrieve delle info
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("uidfornitore", uidFornitore);
            editor.apply();
            categoria = sharedPrefs.getString("categoria", "").toString();

        } else {

            uidFornitore = sharedPrefs.getString("uidFornitore", "").toString();
            categoria = sharedPrefs.getString("categoria", "").toString();

            bundle = new Bundle();
            bundle.putString("uidFornitore", uidFornitore);
            bundle.putString("categoria", categoria);

        }

        Tnome_azienda = (TextView) findViewById(R.id.textViewNotaAmministratore);
        Tnome = (TextView) findViewById(R.id.textViewNotaPersonale);
        btnConferma = (Button) findViewById(R.id.btnConferma);
        btnAnnulla = (Button) findViewById(R.id.btnAnnulla);


        databaseReference = firebaseDatabase.getReference("Ticket_intervento");

        addTicketIntervento(databaseReference,"",uidAmministratore,"",uidFornitore,"","","","");

        btnConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MainDrawer.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });

    }


    private void addTicketIntervento(DatabaseReference postRef, final String aggiornamentoCondomini, final String amministratore, final String descrizioneCondomini, final String fornitore, final String messaggioCondomino, final String oggetto, final String richiesta, final String stabile) {
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
                SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm ");
                String stringdate = dt.format(newDate);

                //Instanziamo un nuovo oggetto MessaggioCondomino contenente tutte le informazioni
                //per la creazione di un nuovo nodo Messaggi_condomino su Firebase

                TicketIntervento ticketIntervento = new TicketIntervento(counter.toString(),amministratore,stringdate,stringdate,fornitore,messaggioCondomino,aggiornamentoCondomini,descrizioneCondomini,oggetto,"",richiesta,stabile,"In Attesa","1","-");

                //MessaggioCondomino m = new MessaggioCondomino(counter.toString(),stringdate,"Segnalazione", descrizioneSegnalazione,uidCondomino,uidAmministratore, stabile, percorsoFoto, urlFoto);

                //Setta il nome del nodo del messaggio (key)
                mutableData.child(counter.toString()).setValue(m);
                //Setta il counter del nodo Messaggi_condomino
                mutableData.child("counter").setValue(counter);


                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}