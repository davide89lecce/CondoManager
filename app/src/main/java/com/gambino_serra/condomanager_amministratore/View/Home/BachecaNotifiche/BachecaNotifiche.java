package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggionamentiTicket.AggiornamentiTicket;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BachecaNotifiche extends Fragment {

    // SEZIONE NOTIFICHE MESSAGGI
    private static RecyclerView.Adapter adapterMess;
    private RecyclerView.LayoutManager layoutManagerMess;
    private static RecyclerView recyclerViewMess;

    // SEZIONE NOTIFICHE RAPPORTI
    private static RecyclerView.Adapter adapterAgg;
    private RecyclerView.LayoutManager layoutManagerAgg;
    private static RecyclerView recyclerViewAgg;


    //public static View.OnClickListener myOnClickListener;
    private Firebase firebaseDB;
    Context context;
    private ConstraintLayout notificheTicket;
    private ConstraintLayout notificheMessaggi;


    TextView TnumeroAggiornamenti;
    TextView TnumeroMessaggi;

    private FirebaseAuth firebaseAuth;
    private String uidAmministratore;

    Map<String, Object> ticketInterventoMap;
    Map<String, Object> MessaggioMap;
    ArrayList<CardTicketIntervento> interventi;
    ArrayList<CardTicketIntervento> messaggi;

    // STRUTTURE DI APPOGGIO CHE VERRANNO STAMPATE NELLE OPPORTUNE RECYCLE
    ArrayList<String> nomiAziende;
    ArrayList<String> nomiStabili;

    Integer numeroAggiornamentiTicket = 0;
    Integer numeroNuoviMessaggi = 0;

    public static BachecaNotifiche newInstance() {
        BachecaNotifiche fragment = new BachecaNotifiche();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bacheca_notifiche, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // INIZIALIZZO AD OGNI onStart PER EVITARE CHE RIAMANGA IN MEMORIA ED AUMENTI
        numeroAggiornamentiTicket = 0;
        numeroNuoviMessaggi = 0;

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();

        ticketInterventoMap = new HashMap<String,Object>();
        interventi = new ArrayList<CardTicketIntervento>();

        MessaggioMap = new HashMap<String,Object>();
        nomiAziende = new ArrayList<String>();

        // Sezione Aggiornamenti
        notificheTicket = (ConstraintLayout) getActivity().findViewById(R.id.notificheTicket);
        TnumeroAggiornamenti = (TextView) getActivity().findViewById(R.id.textNumAggiornamenti);
        recyclerViewAgg = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_rapporti);
        recyclerViewAgg.setHasFixedSize(true);
        layoutManagerAgg = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewAgg.setLayoutManager(layoutManagerAgg);
        recyclerViewAgg.setItemAnimator(new DefaultItemAnimator());

        // Sezione Messaggi
        notificheMessaggi = (ConstraintLayout) getActivity().findViewById(R.id.notificheMessaggi);
        TnumeroMessaggi = (TextView) getActivity().findViewById(R.id.textNumMessaggi);
        recyclerViewMess = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_messaggi);
        recyclerViewMess.setHasFixedSize(true);
        layoutManagerMess = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewMess.setLayoutManager(layoutManagerMess);
        recyclerViewMess.setItemAnimator(new DefaultItemAnimator());

        //myOnClickListener = new MyOnClickListener(context);




        notificheTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AggiornamentiTicket.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();


        Query query;
        query = FirebaseDB.getInterventi().orderByChild("amministratore").equalTo(uidAmministratore);

        // la query seleziona solo gli interventi con un determinato fornitore
        //il listener lavora sui figli della query, ovvero su titti gli interventi recuperati
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                String fornitore = "";
                String numeroAggiornamenti = "0";

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().equals("numero_aggiornamenti")){
                        numeroAggiornamenti = child.getValue().toString();
                    }
                    if(child.getKey().equals("fornitore")) {
                        fornitore = child.getValue().toString();
                    }
                }
                if(!numeroAggiornamenti.equals("0")){
                    numeroAggiornamentiTicket = numeroAggiornamentiTicket + Integer.parseInt(numeroAggiornamenti);
                    recuperaNomeAzienda(fornitore);
                }
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }

    private void recuperaNomeAzienda(String uidFornitore) {

        Query query;
        query = FirebaseDB.getFornitori().child(uidFornitore).child("nome_azienda");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeAzienda;
                nomeAzienda = dataSnapshot.getValue(String.class);
                nomiAziende.add(nomeAzienda);

                adapterAgg = new AdapterNotificheRapporti(nomiAziende);
                recyclerViewAgg.setAdapter(adapterAgg);
                TnumeroAggiornamenti.setText(numeroAggiornamentiTicket.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}