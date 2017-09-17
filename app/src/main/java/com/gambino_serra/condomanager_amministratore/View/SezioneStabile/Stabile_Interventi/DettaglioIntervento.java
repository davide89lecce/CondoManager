package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class DettaglioIntervento extends AppCompatActivity {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String username = "";
    String idTicket;

    // Oggetti di Layout NUOVI
    TextView mOggetto;
    TextView mDescrizione;
    TextView mStato;
    TextView mUltimoAggiornamento;
    TextView mFornitore;
    TextView mDataAgg;
    TextView mNomeFornitore;
    TextView mAziendaFornitore;
    TextView mCategoria;
    ImageView mFoto;
    ImageView mLogoStato;


    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    Map<String, Object> ticketInterventoMap;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sezionestabile_dettaglio_intervento);

        firebaseAuth = FirebaseAuth.getInstance();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            idTicket = bundle.get("idTicket").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idTicket", idTicket);
            editor.apply();

        } else {
            idTicket = sharedPrefs.getString("idSegnalazione", "").toString();

            bundle = new Bundle();
            bundle.putString("idSegnalazione", idTicket);

        }

        // Avvaloro i nuovi rierimenti al layout
        mOggetto = (TextView) findViewById(R.id.Descr_Avviso);
        mDescrizione = (TextView) findViewById(R.id.D_Stabile);
        mStato = (TextView) findViewById(R.id.D_Stato);
        mUltimoAggiornamento = (TextView) findViewById(R.id.D_ultimoAggiorn);
        mDataAgg = (TextView) findViewById(R.id.D_dataAggiorn);
        mFornitore = (TextView) findViewById(R.id.D_NomeFornitore);
        mNomeFornitore = (TextView) findViewById(R.id.D_NomeFornitore);
        mAziendaFornitore = (TextView) findViewById(R.id.D_AziendaFornitore);
        mCategoria = (TextView) findViewById(R.id.D_Categoria);
        mFoto = (ImageView) findViewById(R.id.D_Foto);
        mLogoStato = (ImageView) findViewById(R.id.D_LogoStato);


        Query intervento;
        intervento = FirebaseDB.getInterventi().orderByKey().equalTo(idTicket);

        intervento.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ticketInterventoMap = new HashMap<String, Object>();
                // Avvalora il primo oggetto del map con l'ID dell'intervento recuperato
                ticketInterventoMap.put("idIntervento", idTicket);


                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ticketInterventoMap.put(child.getKey(), child.getValue());
                }

                recuperaDettagliTicket(ticketInterventoMap);


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
    }








    public void recuperaDettagliTicket(final Map<String, Object> ticketInterventoMap) {

        final Map<String, Object> ticketInterventoMap2 = new HashMap<String, Object>();


        Query query2;
        query2 = FirebaseDB.getFornitori().orderByKey().equalTo(ticketInterventoMap.get("fornitore").toString());

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //HashMap temporaneo per immagazzinare i dati dello stabile
                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Stabile
                // recuperiamo i dati per inserirli nel MAP
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ticketInterventoMap2.put(child.getKey(), child.getValue());
                }

                TicketIntervento ticketIntervento = new TicketIntervento(
                        ticketInterventoMap.get("idIntervento").toString(),
                        ticketInterventoMap.get("amministratore").toString(),
                        ticketInterventoMap.get("data_ticket").toString(),
                        ticketInterventoMap.get("data_ultimo_aggiornamento").toString(),
                        ticketInterventoMap.get("fornitore").toString(),
                        ticketInterventoMap.get("messaggio_condomino").toString(),
                        ticketInterventoMap.get("aggiornamento_condomini").toString(),
                        ticketInterventoMap.get("descrizione_condomini").toString(),
                        ticketInterventoMap.get("oggetto").toString(),
                        ticketInterventoMap.get("rapporti_intervento").toString(),
                        ticketInterventoMap.get("richiesta").toString(),
                        ticketInterventoMap.get("stabile").toString(),
                        ticketInterventoMap.get("stato").toString(),
                        ticketInterventoMap.get("priorità").toString(),
                        ticketInterventoMap.get("foto").toString(),
                        ticketInterventoMap2.get("nome_azienda").toString(),
                        ticketInterventoMap2.get("nome").toString(),
                        ticketInterventoMap2.get("categoria").toString()
                );


                mOggetto.setText(ticketIntervento.getOggetto());
                mFornitore.setText(ticketIntervento.getFornitore());
                mDataAgg.setText(ticketIntervento.getDataUltimoAggiornamento());
                mUltimoAggiornamento.setText(ticketIntervento.getAggiornamentoCondomini());
                mDescrizione.setText(ticketIntervento.getDescrizioneCondomini());
                mStato.setText(ticketIntervento.getStato());
                mNomeFornitore.setText(ticketIntervento.getFornitore());
                //mAziendaFornitore.setText(ticketIntervento.getNomeAziendaFornitore());
                //mCategoria.setText(ticketIntervento.getCategoriaFornitore());

                if ( ! "-".equals(ticketIntervento.getFoto())  ) {
                    Picasso.with(getApplicationContext()).load( ticketIntervento.getFoto() ).fit().centerCrop().into(mFoto) ;
                }



                String stato = ticketIntervento.getStato();

                switch(stato) {
                    // intervento richiesto o rifiutato (al condomino interressa solo che sia stato processato
                    // dall'amministratore, se un fornitore lo rifiuterà, lui lo vedrà ancora in attesa
                    // di essere preso in carico
                    case "in attesa" :
                    case "rifiutato" :
                    {
                        mLogoStato.setImageResource(R.drawable.in_attesa3);
                        mStato.setText("Intervento Richiesto");
                        break;
                    }

                    case "in corso": // intervento in corso
                    {
                        mLogoStato.setImageResource(R.drawable.inwork2);
                        mStato.setText("Intervento in Corso");
                        break;
                    }
                    case "completato":   // intervento concluso
                    {
                        mLogoStato.setImageResource(R.drawable.interv_complet);
                        mStato.setText("Intervento Completato");
                        break;
                    }

                    default:
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


    }




}
