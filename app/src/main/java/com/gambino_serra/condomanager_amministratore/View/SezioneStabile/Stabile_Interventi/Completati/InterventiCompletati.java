package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Completati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.SezioneStabile;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.DettaglioIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InterventiCompletati extends Fragment {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";
    String username;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    Context context;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private Bundle bundle;
    private String idStabile;

    Map<String, Object> ticketInterventoMap;
    ArrayList<CardTicketIntervento> interventi_inCorso;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, getActivity().MODE_PRIVATE);

        if (getActivity().getIntent().getExtras() != null) {

            bundle = getActivity().getIntent().getExtras();
            idStabile = bundle.get("idStabile").toString(); // prende l'identificativo per fare il retrieve delle info
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idStabile", idStabile);
            editor.apply();
        } else {
            idStabile = sharedPrefs.getString("idStabile", "").toString();
            bundle = new Bundle();
            bundle.putString("idStabile", idStabile);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sezionestabile_tab_completati, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        ticketInterventoMap = new HashMap<String, Object>();
        interventi_inCorso = new ArrayList<CardTicketIntervento>();

        myOnClickListener = new InterventiCompletati.MyOnClickListener(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_Completati);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        // Riferimento alla tabella contenente tutti gli Interventi
        firebaseDB = FirebaseDB.getInterventi();


        Query query;
        query = FirebaseDB.getInterventi().orderByChild("stabile").equalTo(idStabile);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //per ogni intervento relativo allo stabile in questione utilizzo un Map temporaneo
                // per immagazzinarne i dettagli
                ticketInterventoMap = new HashMap<String,Object>();
                ticketInterventoMap.put("id", dataSnapshot.getKey());
                // per ogni dettaglio dell'intervento
                for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                    ticketInterventoMap.put(child.getKey(), child.getValue());
                }

                CardTicketIntervento cardTicketIntervento_inCorso = new CardTicketIntervento(
                        ticketInterventoMap.get("id").toString(),
                        ticketInterventoMap.get("stabile").toString(),
                        ticketInterventoMap.get("oggetto").toString(),
                        ticketInterventoMap.get("priorità").toString(),
                        ticketInterventoMap.get("stato").toString(),
                        ticketInterventoMap.get("descrizione_condomini").toString(),
                        ticketInterventoMap.get("aggiornamento_condomini").toString(),
                        ticketInterventoMap.get("data_ticket").toString(),
                        ticketInterventoMap.get("data_ultimo_aggiornamento").toString(),
                        ticketInterventoMap.get("numero_aggiornamenti").toString()
                );

                if (       ( cardTicketIntervento_inCorso.getStato().equals("completato")  )
                        ||  ( cardTicketIntervento_inCorso.getStato().equals("archiviato")  ) )
                {
                    interventi_inCorso.add(cardTicketIntervento_inCorso);
                }

                adapter = new AdapterInterventCompletati(interventi_inCorso);
                recyclerView.setAdapter(adapter);
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
            detailsSegnalazione(v);
        }

        private void detailsSegnalazione(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.IDTicket);
            String selectedName = (String) textViewName.getText();//TODO: controlla textViewName

            Bundle bundle = new Bundle();
            bundle.putString("idTicket", selectedName);

            Intent intent = new Intent(context, DettaglioIntervento.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
