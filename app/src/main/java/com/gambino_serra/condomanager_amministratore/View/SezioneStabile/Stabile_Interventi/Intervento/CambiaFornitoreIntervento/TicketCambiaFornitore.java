package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.CambiaFornitoreIntervento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Dialog.DialogVisualizzaFotoRapporto;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;

import com.google.firebase.database.Transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TicketCambiaFornitore extends AppCompatActivity {
    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    EditText mRichiestaFornitore;
    EditText mDescrizioneCondomino;
    EditText mOggetto;
    TextView Tnome;
    TextView Tcategoria;
    TextView Ttelefono;
    TextView Tpartita_iva;
    TextView Temail;
    TextView Tindirizzo;
    ConstraintLayout btnAnnulla;
    ConstraintLayout btnConferma;
    ImageButton btnFoto;
    CheckBox mCheckBox;


    Map<String, Object> ticketInterventoMap;
    String uidFornitore;
    String categoria;
    String uidAmministratore;
    String idStabile;
    String foto;
    String richiesta;
    String descrizione;
    String oggetto;
    String idIntervento;
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
        setContentView(R.layout.crea_ticket_finale);

        firebaseAuth = FirebaseAuth.getInstance();
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        ticketInterventoMap = new HashMap<String, Object>();

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            uidFornitore = bundle.get("uidFornitore").toString(); // prende l'identificativo per fare il retrieve delle info
            categoria = bundle.get("categoria").toString();
            idStabile = bundle.get("idStabile").toString();
            idIntervento = bundle.get("idInterventoRifiutato").toString();
            foto = bundle.get("foto").toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("uidfornitore", uidFornitore);
            editor.putString("categoria", categoria);
            editor.putString("idStabile", idStabile);
            editor.putString("foto", foto);
            editor.putString("idInterventoRifiutato", idIntervento);
            editor.apply();

        } else {

            uidFornitore = sharedPrefs.getString("uidFornitore", "").toString();
            categoria = sharedPrefs.getString("categoria", "").toString();
            idStabile = sharedPrefs.getString("idStabile", "").toString();
            foto = sharedPrefs.getString("foto", "").toString();
            idIntervento = sharedPrefs.getString("idInterventoRifiutato", "").toString();


            bundle = new Bundle();
            bundle.putString("uidFornitore", uidFornitore);
            bundle.putString("categoria", categoria);
            bundle.putString("idStabile", idStabile);
            bundle.putString("foto", foto);
            bundle.putString("idInterventoRifiutato", idIntervento);

        }

        mRichiestaFornitore = (EditText) findViewById(R.id.textViewRichiestaFornitore);
        mDescrizioneCondomino = (EditText) findViewById(R.id.textViewDescrizioneCondomini);
        mOggetto = (EditText) findViewById(R.id.editTextOggetto);
        btnFoto = (ImageButton) findViewById(R.id.imageButton);
        mCheckBox = (CheckBox) findViewById(R.id.checkBoxFoto);
        btnConferma = (ConstraintLayout) findViewById(R.id.btnConferma);
        btnAnnulla = (ConstraintLayout) findViewById(R.id.btnAnnulla);

        //Lettura ticket esistente
        Query query1;
        query1 = FirebaseDB.getInterventi().orderByKey().equalTo(idIntervento);
        Log.d("Ciao", " " + idIntervento);

        query1.addChildEventListener(new ChildEventListener() {
                                         @Override
                                         public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                             for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                 ticketInterventoMap.put(child.getKey(), child.getValue());
                                             }

                                             TicketIntervento ticketIntervento = new TicketIntervento(
                                                     idIntervento,
                                                     ticketInterventoMap.get("amministratore").toString(),
                                                     ticketInterventoMap.get("data_ticket").toString(),
                                                     ticketInterventoMap.get("data_ultimo_aggiornamento").toString(),
                                                     ticketInterventoMap.get("fornitore").toString(),
                                                     ticketInterventoMap.get("aggiornamento_condomini").toString(),
                                                     ticketInterventoMap.get("descrizione_condomini").toString(),
                                                     ticketInterventoMap.get("oggetto").toString(),
                                                     ticketInterventoMap.get("richiesta").toString(),
                                                     ticketInterventoMap.get("stabile").toString(),
                                                     ticketInterventoMap.get("stato").toString(),
                                                     ticketInterventoMap.get("priorità").toString(),
                                                     ticketInterventoMap.get("foto").toString(),
                                                     "categoria", "nomeFornitore", "nomeAziendaFornitore","nomeStabile","indirizzoStabile"
                                             );

                                             try {
                                                 mRichiestaFornitore.setText(ticketIntervento.getRichiesta());
                                                 mDescrizioneCondomino.setText(ticketIntervento.getDescrizioneCondomini());
                                                 mOggetto.setText(ticketIntervento.getOggetto());

                                                 if (foto.equals("-")) {
                                                     btnFoto.setVisibility(View.GONE);
                                                     mCheckBox.setVisibility(View.GONE);
                                                 }

                                             } catch (NullPointerException e) {
                                             }
                                         }

                                         @Override
                                         public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                         }

                                         @Override
                                         public void onChildRemoved(DataSnapshot dataSnapshot) {

                                         }

                                         @Override
                                         public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                         }

                                         @Override
                                         public void onCancelled(FirebaseError firebaseError) {

                                         }
                                     });


                firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Ticket_intervento");

        if ( foto.equals("-") )
        {
            btnFoto.setVisibility(View.GONE);
            mCheckBox.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();


        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogVisualizzaFotoRapporto newFragment = new DialogVisualizzaFotoRapporto();
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "DialogFoto");
            }
        });



        btnConferma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                oggetto = mOggetto.getText().toString();
                richiesta = mRichiestaFornitore.getText().toString();
                descrizione = mDescrizioneCondomino.getText().toString();

                if ( ! oggetto.isEmpty() || ! richiesta.isEmpty() || ! descrizione.isEmpty() ) {

                    addTicketIntervento(databaseReference, oggetto, richiesta, descrizione);

                    Intent intent = new Intent(getApplicationContext(), MainDrawer.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(TicketCambiaFornitore.this, "Assicurarsi di aver compilato tutti i campi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }

    // TODO recupera messaggio condomino
    private void addTicketIntervento(DatabaseReference postRef, final String oggetto, final String richiestaAFornitore, final String descrizioneCondomini) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                //Legge il valore del nodo counter
                Integer counter = mutableData.child("counter").getValue(Integer.class);

                if (counter == null) {
                    return Transaction.success(mutableData);
                }

                //Ricava la data e la formatta nel formato appropriato
                Date newDate = new Date(new Date().getTime());
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
                String stringdate = dt.format(newDate);

                //Setta il nome del nodo del messaggio (key)
                mutableData.child(idIntervento).child("aggiornamento_condomini").setValue("-");
                mutableData.child(idIntervento).child("amministratore").setValue(uidAmministratore);
                mutableData.child(idIntervento).child("data_ticket").setValue(stringdate);
                mutableData.child(idIntervento).child("data_ultimo_aggiornamento").setValue("-");
                mutableData.child(idIntervento).child("descrizione_condomini").setValue(descrizioneCondomini);
                mutableData.child(idIntervento).child("fornitore").setValue(uidFornitore);
                if ( mCheckBox.isChecked()) {
                    mutableData.child(idIntervento).child("foto").setValue(foto);
                }else{
                    mutableData.child(idIntervento).child("foto").setValue("-");
                }
                mutableData.child(idIntervento).child("oggetto").setValue(oggetto);
                mutableData.child(idIntervento).child("priorità").setValue("2");      // PRIORITà DI DEFAULT MEDIA
                mutableData.child(idIntervento).child("richiesta").setValue(richiestaAFornitore);
                mutableData.child(idIntervento).child("stabile").setValue(idStabile);
                mutableData.child(idIntervento).child("stato").setValue("in attesa");
                mutableData.child(idIntervento).child("numero_aggiornamenti").setValue("0");


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