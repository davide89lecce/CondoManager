package com.gambino_serra.condomanager_amministratore.View.Home.BachecaCondomini;

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
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BachecaRichiesteIntervento extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<CardTicketIntervento> data;
    public static View.OnClickListener myOnClickListener;
    Context context;

    private FirebaseAuth firebaseAuth;
    private String uidFornitore;
    Map<String, Object> ticketInterventoMap;
    ArrayList<CardTicketIntervento> interventi;

    public static BachecaRichiesteIntervento newInstance() {
        BachecaRichiesteIntervento fragment = new BachecaRichiesteIntervento();
        return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_richieste_intervento, container, false);
        }

    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<CardTicketIntervento>();
        ticketInterventoMap = new HashMap<String,Object>();
        interventi = new ArrayList<CardTicketIntervento>();

        myOnClickListener = new MyOnClickListener(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //lettura uid condomino -->  codice fiscale stabile, uid amministratore
        uidFornitore = firebaseAuth.getCurrentUser().getUid().toString();

        Query query;
        query = FirebaseDB.getInterventi().orderByChild("fornitore").equalTo(uidFornitore);


        // la query seleziona solo gli interventi con un determinato fornitore
        //il listener lavora sui figli della query, ovvero su titti gli interventi recuperati
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                //HashMap temporaneo per immagazzinare i dati di un ticket
                ticketInterventoMap = new HashMap<String,Object>();
                ticketInterventoMap.put("id", dataSnapshot.getKey()); //primo campo del MAP

                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Interv
                // recuperiamo i dati per inserirli nel MAP
                for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                    ticketInterventoMap.put(child.getKey(), child.getValue());
                    }
                recuperaDatiStabile (ticketInterventoMap);
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

    private static class MyOnClickListener extends AppCompatActivity implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            detailsIntervento(v);
        }

        private void detailsIntervento(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.D_IDIntervento);
            String selectedName = (String) textViewName.getText();

            Bundle bundle = new Bundle();
            bundle.putString("idIntervento", selectedName);

            Intent intent = new Intent(context, DettaglioRichiestaIntervento.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            }
    }


    public void recuperaDatiStabile(final Map<String, Object> ticketInterventoMap) {

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

                // Avvaloro tutti i dati della card che mi interessano inserendone i relativi dati
                // anche quelli provenienti dallo stabile sovrascrivendo i codici passati in ticketIntervento
                // Avvaloriamo una variabile TicketIntervento appositamente creata in modo da inserire poi questo
                // oggetto all'interno di un Array di interventi che utilizzeremo per popolare la lista Recycle
                try {
                    CardTicketIntervento ticketIntervento = new CardTicketIntervento(
                            ticketInterventoMap.get("id").toString(),
                            ticketInterventoMap.get("stabile").toString(),
                            ticketInterventoMap2.get("nome").toString(),
                            ticketInterventoMap2.get("indirizzo").toString(),
                            ticketInterventoMap.get("oggetto").toString(),
                            ticketInterventoMap.get("priorità").toString(),
                            ticketInterventoMap.get("stato").toString(),
                            ticketInterventoMap.get("data_ticket").toString(),
                            ticketInterventoMap.get("data_ultimo_aggiornamento").toString()
                            );

                    if (ticketIntervento.getStato().equals("in attesa")) {
                        // inserisce l'oggetto ticket nell'array interventi
                        interventi.add(ticketIntervento);
                        }

                    // Utilizziamo l'adapter per popolare la recycler view
                    adapter = new AdapterRichiesteIntervento(interventi);
                    recyclerView.setAdapter(adapter);

                }
                catch (NullPointerException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Non riesco ad aprire l'oggetto " + e.toString(), Toast.LENGTH_LONG).show();
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
}