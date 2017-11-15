package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggionamentiTicket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.Intervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AggiornamentiTicket extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<TicketIntervento> data;
    public static View.OnClickListener myOnClickListener;
    private Firebase firebaseDB;
    Context context;

    private FirebaseAuth firebaseAuth;
    private String uidAmministratore;
    Map<String, Object> ticketInterventoMap;
    ArrayList<CardTicketIntervento> interventi;
    ArrayList<String> rapportiAziende;
    Integer numeroAggiornamentiTicket = 0;

    public static AggiornamentiTicket newInstance() {
        AggiornamentiTicket fragment = new AggiornamentiTicket();
        return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiornamenti_notifiche_ticket);
        firebaseAuth = FirebaseAuth.getInstance();
        }


    @Override
    public void onStart() {
        super.onStart();

        context = getApplicationContext();

        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<TicketIntervento>();
        ticketInterventoMap = new HashMap<String,Object>();
        interventi = new ArrayList<CardTicketIntervento>();
        rapportiAziende = new ArrayList<String>();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_notifiche_ticket);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        myOnClickListener = new  AggiornamentiTicket.MyOnClickListener(context);

        //lettura di TUTTI i fornitori
        Query query;
        query = FirebaseDB.getInterventi().orderByChild("amministratore").equalTo(uidAmministratore);

        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ticketInterventoMap = new HashMap<String, Object>();
                ticketInterventoMap.put("idIntervento", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ticketInterventoMap.put(child.getKey(), child.getValue());
                }

                recuperaDettagliTicket(ticketInterventoMap);
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


    public void recuperaDettagliTicket(final Map<String, Object> ticketInterventoMap) {

        final Map<String, Object> ticketInterventoMap2 = new HashMap<String, Object>();


        Query query2;
        query2 = FirebaseDB.getStabili().orderByKey().equalTo(ticketInterventoMap.get("stabile").toString());


        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //HashMap temporaneo per immagazzinare i dati dello stabile
                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Stabile
                // recuperiamo i dati per inserirli nel MAP
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ticketInterventoMap2.put(child.getKey(), child.getValue());
                }


                Firebase nomeAmm = FirebaseDB.getAmministratori()
                        .child( ticketInterventoMap.get("amministratore").toString() )
                        .child("nome");

                nomeAmm.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ticketInterventoMap2.put("nomeAmministratore", dataSnapshot.getValue().toString());

                        Firebase nomeStabile = FirebaseDB.getStabili()
                                .child(ticketInterventoMap.get("stabile").toString())
                                .child("nome");
                        nomeStabile.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ticketInterventoMap2.put("nomeStabile", dataSnapshot.getValue().toString());

                                // Avvaloro tutti i dati della card che mi interessano inserendone i relativi dati
                                // anche quelli provenienti dallo stabile sovrascrivendo i codici passati in ticketIntervento
                                // Avvaloriamo una variabile TicketIntervento appositamente creata in modo da inserire poi questo
                                // oggetto all'interno di un Array di interventi che utilizzeremo per popolare la lista Recycle
                                //try {
                                CardTicketIntervento cardTicketIntervento = new CardTicketIntervento(
                                        ticketInterventoMap.get("idIntervento").toString(),
                                        ticketInterventoMap2.get("nomeStabile").toString(),
                                        ticketInterventoMap.get("oggetto").toString(),
                                        ticketInterventoMap.get("priorità").toString(),
                                        ticketInterventoMap.get("stato").toString(),
                                        ticketInterventoMap.get("descrizione_condomini").toString(),
                                        ticketInterventoMap.get("aggiornamento_condomini").toString(),
                                        ticketInterventoMap.get("data_ticket").toString(),
                                        ticketInterventoMap.get("data_ultimo_aggiornamento").toString(),
                                        ticketInterventoMap.get("numero_aggiornamenti").toString()
                                );

                                if (!cardTicketIntervento.getNumeroAggiornamenti().equals("0"))
                                    interventi.add(cardTicketIntervento);

                                adapter = new AdapterAggiornamentiTicket(interventi);
                                recyclerView.setAdapter(adapter);

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                            }
                        });

                    }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }

                    });
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

    private static class MyOnClickListener extends AppCompatActivity implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            detailsIntervento(v,context);
        }

        private void detailsIntervento(View v, Context context) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.IDTicket);
            String selectedName = (String) textViewName.getText();

            Bundle bundle = new Bundle();
            bundle.putString("idIntervento", selectedName);

            Intent intent = new Intent( context, Intervento.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


}