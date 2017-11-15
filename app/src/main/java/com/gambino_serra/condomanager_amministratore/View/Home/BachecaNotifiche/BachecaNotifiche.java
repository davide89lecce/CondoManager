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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Messaggio;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggionamentiTicket.AggiornamentiTicket;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiMessaggi.AggiornamentiMessaggi;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiSondaggi.AggiornamentiSondaggi;
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
    private static RecyclerView.Adapter adapterSond;
    private RecyclerView.LayoutManager layoutManagerSond;
    private static RecyclerView recyclerViewSond;

    // SEZIONE NOTIFICHE RAPPORTI
    private static RecyclerView.Adapter adapterAgg;
    private RecyclerView.LayoutManager layoutManagerAgg;
    private static RecyclerView recyclerViewAgg;


    //public static View.OnClickListener myOnClickListener;
    private Firebase firebaseDB;
    Context context;
    private ConstraintLayout notificheTicket;
    private ConstraintLayout notificheMessaggi;
    private ConstraintLayout notificheSondaggi;


    TextView TnumeroAggiornamenti;
    TextView TnumeroMessaggi;
    TextView TnumeroSondaggi;

    private FirebaseAuth firebaseAuth;
    private String uidAmministratore;

    Map<String, Object> ticketInterventoMap;
    Map<String, Object> MessaggioMap;
    Map<String, Object> sondaggioMap;
    ArrayList<CardTicketIntervento> interventi;
    ArrayList<CardTicketIntervento> sondaggi;
    ArrayList<CardTicketIntervento> messaggi;

    // STRUTTURE DI APPOGGIO CHE VERRANNO STAMPATE NELLE OPPORTUNE RECYCLE
    ArrayList<String> nomiAziende;
    ArrayList<String> nomiStabili;
    ArrayList<String> nomiSondaggi;

    Integer numeroAggiornamentiTicket = 0;
    Integer numeroNuoviMessaggi = 0;
    Integer numeroSondaggiAperti = 0;

    public static BachecaNotifiche newInstance() {
        BachecaNotifiche fragment = new BachecaNotifiche();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();

        ticketInterventoMap = new HashMap<String,Object>();
        interventi = new ArrayList<CardTicketIntervento>();
        nomiAziende = new ArrayList<String>();

        sondaggioMap = new HashMap<String,Object>();
        //sondaggi = new ArrayList<Sondaggio>();
        nomiSondaggi = new ArrayList<String>();

        MessaggioMap = new HashMap<String,Object>();
        //messaggi = new ArrayList<Messaggio>();
        nomiStabili = new ArrayList<String>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bacheca_notifiche, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        // Sezione Aggiornamenti
        notificheTicket = (ConstraintLayout) getActivity().findViewById(R.id.notificheTicket);
        TnumeroAggiornamenti = (TextView) getActivity().findViewById(R.id.textNumAggiornamenti);
        recyclerViewAgg = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_rapporti);
        recyclerViewAgg.setHasFixedSize(true);
        layoutManagerAgg = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewAgg.setLayoutManager(layoutManagerAgg);
        recyclerViewAgg.setItemAnimator(new DefaultItemAnimator());

        // Sezione Sondaggi
        notificheSondaggi = (ConstraintLayout) getActivity().findViewById(R.id.notificheSondaggi);
        TnumeroSondaggi = (TextView) getActivity().findViewById(R.id.textNumSondaggi);
        recyclerViewSond = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_sondaggi);
        recyclerViewSond.setHasFixedSize(true);
        layoutManagerSond = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewSond.setLayoutManager(layoutManagerSond);
        recyclerViewSond.setItemAnimator(new DefaultItemAnimator());

        // Sezione Messaggi
        notificheMessaggi = (ConstraintLayout) getActivity().findViewById(R.id.notificheMessaggi);
        TnumeroMessaggi = (TextView) getActivity().findViewById(R.id.textNumMessaggi);
        recyclerViewMess = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view_messaggi);
        recyclerViewMess.setHasFixedSize(true);
        layoutManagerMess = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewMess.setLayoutManager(layoutManagerMess);
        recyclerViewMess.setItemAnimator(new DefaultItemAnimator());



        // INIZIALIZZO AD OGNI onStart PER EVITARE CHE RIAMANGA IN MEMORIA ED AUMENTI
        numeroAggiornamentiTicket = 0;
        numeroNuoviMessaggi = 0;
        numeroSondaggiAperti = 0;


        //myOnClickListener = new MyOnClickListener(context);


        notificheMessaggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( numeroNuoviMessaggi != 0) {
                    Intent intent = new Intent(context, AggiornamentiMessaggi.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{}
            }
        });


        notificheSondaggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( numeroSondaggiAperti != 0) {
                    Intent intent = new Intent(context, AggiornamentiSondaggi.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{}
            }
        });


        notificheTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( numeroAggiornamentiTicket != 0) {
                    Intent intent = new Intent(context, AggiornamentiTicket.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{}
            }
        });



        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();


        Query queryRapporti;
        queryRapporti = FirebaseDB.getInterventi().orderByChild("amministratore").equalTo(uidAmministratore);

        // la query seleziona solo gli interventi per un determinato amministratore
        //il listener lavora sui figli della query, ovvero su tutti gli interventi recuperati
        queryRapporti.addChildEventListener(new ChildEventListener() {
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





        Query queryMessaggi;
        queryMessaggi = FirebaseDB.getMessaggiCondomino().orderByChild("uidAmministratore").equalTo(uidAmministratore);

        // la query seleziona solo i messaggi inviati a un determinato amministratore
        //il listener lavora sui figli della query, ovvero su titti i messaggi recuperati
        queryMessaggi.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String stabile = "";
                String messaggioLetto = "";

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().toString().equals("letto")){
                        messaggioLetto = child.getValue().toString();
                    }
                    if(child.getKey().equals("stabile")) {
                        stabile = child.getValue().toString();
                    }
                }
                if( messaggioLetto.equals("no") ){
                    numeroNuoviMessaggi = numeroNuoviMessaggi + 1 ;
                    recuperaNomeStabile(stabile);
                }
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


        Query querySondaggi;
        querySondaggi = FirebaseDB.getSondaggi().orderByChild("amministratore").equalTo(uidAmministratore);

        // la query seleziona solo i messaggi inviati a un determinato amministratore
        //il listener lavora sui figli della query, ovvero su titti i messaggi recuperati
        querySondaggi.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String oggetto = "";
                String sondaggioAperto = "";

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().toString().equals("stato")){
                        sondaggioAperto = child.getValue().toString();
                    }
                    if(child.getKey().equals("oggetto")) {
                        oggetto = child.getValue().toString();
                    }
                }
                if( sondaggioAperto.equals("aperto") ){
                    numeroSondaggiAperti = numeroSondaggiAperti + 1 ;

                    nomiSondaggi.add(oggetto);
                    adapterSond = new AdapterNotificheSondaggi(nomiSondaggi);
                    recyclerViewSond.setAdapter(adapterSond);
                    TnumeroSondaggi.setText(numeroSondaggiAperti.toString());
                }
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

    private void recuperaNomeStabile(String idStabile) {
        Query query;
        query = FirebaseDB.getStabili().child(idStabile).child("nome");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nomeStabile;
                nomeStabile = dataSnapshot.getValue(String.class);
                nomiStabili.add(nomeStabile);

                adapterMess = new AdapterNotificheMessaggi(nomiStabili);
                recyclerViewMess.setAdapter(adapterMess);
                TnumeroMessaggi.setText(numeroNuoviMessaggi.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
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