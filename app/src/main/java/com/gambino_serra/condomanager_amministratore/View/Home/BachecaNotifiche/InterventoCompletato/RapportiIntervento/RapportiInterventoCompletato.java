package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.InterventoCompletato.RapportiIntervento;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardRapportoIntervento;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class RapportiInterventoCompletato extends Fragment {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String username = "";
    String idIntervento = "";

    // Oggetti di Layout NUOVI
    TextView TAmministratore;
    TextView TdataTicket;
    TextView Toggetto;
    TextView Trichiesta;
    TextView Tstabile;
    TextView Tindirizzo;
    ImageView Tfoto;
    ConstraintLayout Chiudi;
    ConstraintLayout Aggiungi;
    TextView TidTicketIntervento;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<TicketIntervento> data;
    Context context;

    private FirebaseAuth firebaseAuth;
    private String uidFornitore;
    Map<String, Object> rapportoInterventoMap;
    ArrayList<CardRapportoIntervento> rapporti;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rapporti_intervento, container, false);
        return view;
        }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();

        context = getContext();

        rapporti = new ArrayList<CardRapportoIntervento>();
        firebaseAuth = FirebaseAuth.getInstance();

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        idIntervento = sharedPrefs.getString("idIntervento", "").toString();

        bundle = new Bundle();
        bundle.putString("idIntervento", idIntervento);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        uidFornitore = firebaseAuth.getCurrentUser().getUid().toString();

        Aggiungi = (ConstraintLayout) getActivity().findViewById(R.id.btnAggiungiRapporto);
        Chiudi = (ConstraintLayout) getActivity().findViewById(R.id.btnChiudiIntervento);
        TidTicketIntervento = (TextView) getActivity().findViewById(R.id.Hidden_ID);

        Chiudi.setVisibility(View.INVISIBLE);
        Aggiungi.setVisibility(View.INVISIBLE);

        Query query;
        query = FirebaseDB.getRapporti().orderByChild("ticket_intervento").equalTo(idIntervento);

        // la query seleziona solo gli interventi con un determinato fornitore
        //il listener lavora sui figli della query, ovvero su titti gli interventi recuperati
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //HashMap temporaneo per immagazzinare i dati di un ticket
                rapportoInterventoMap = new HashMap<String,Object>();
                rapportoInterventoMap.put("id", dataSnapshot.getKey()); //primo campo del MAP

                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Interv
                // recuperiamo i dati per inserirli nel MAP
                for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                    rapportoInterventoMap.put(child.getKey(), child.getValue());
                    }

                    CardRapportoIntervento rapportoIntervento = new CardRapportoIntervento(
                            rapportoInterventoMap.get("id").toString(),
                            rapportoInterventoMap.get("data").toString(),
                            rapportoInterventoMap.get("nota_amministratore").toString(),
                            rapportoInterventoMap.get("nota_fornitore").toString(),
                            rapportoInterventoMap.get("ticket_intervento").toString()
                            );

                    rapporti.add(rapportoIntervento);

                    // Utilizziamo l'adapter per popolare la recycler view
                    adapter = new AdapterRapportiIntervento(rapporti);
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
}