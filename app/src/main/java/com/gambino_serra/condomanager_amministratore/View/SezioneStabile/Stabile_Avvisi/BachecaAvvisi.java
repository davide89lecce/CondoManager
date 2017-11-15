package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Avvisi;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Avviso;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BachecaAvvisi extends Fragment {
    private static final String MY_PREFERENCES = "preferences";
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    Context context;

    private Firebase firebaseDB;
    private FirebaseAuth firebaseAuth;
    private SimpleDateFormat today;

    private Bundle bundle;
    private String idStabile;
    Map<String, Object> avvisoMap;
    ArrayList<Avviso> avvisi;


    public static BachecaAvvisi newInstance() {
        BachecaAvvisi fragment = new BachecaAvvisi();
        return fragment;
    }

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
        return inflater.inflate(R.layout.sezionestabile_tab_avvisi, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        avvisoMap = new HashMap<String,Object>();
        avvisi = new ArrayList<Avviso>();

        //myOnClickListener = new BachecaAvvisi.MyOnClickListener(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        // Riferimento alla tabella contenente tutti gli Avvisi
        firebaseDB = FirebaseDB.getAvvisi();
        // Query per identificare tutti gli avvisi appartenenti allo stabile desiderato
        Query prova;

        prova = FirebaseDB.getAvvisi().orderByChild("stabile").equalTo(idStabile);

        prova.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                avvisoMap = new HashMap<String,Object>();
                avvisoMap.put("id", dataSnapshot.getKey());

                for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                    avvisoMap.put(child.getKey(), child.getValue());
                }

                //try{
                    Avviso avviso = new Avviso(
                            avvisoMap.get("id").toString(),
                            avvisoMap.get("amministratore").toString(),
                            avvisoMap.get("stabile").toString(),
                            avvisoMap.get("oggetto").toString(),
                            avvisoMap.get("descrizione").toString(),
                            avvisoMap.get("scadenza").toString(),
                            avvisoMap.get("tipologia").toString()
                    );

                //Ricava la data attuale e la formatta nel formato appropriato
                Date dataAttuale = new Date(new Date().getTime());

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date dataAvviso = new Date();
                try{
                    //Parse di dataAvviso in Date
                    dataAvviso = dt.parse(avviso.getDataScadenza());
                 }catch (java.text.ParseException e) {
                    e.printStackTrace();
                 }

                //Confronto di data scadenza avviso con data attuale
                 if(dataAttuale.getTime() < dataAvviso.getTime()){
                     // AGGIUNGE L'AVVISO NELLA STRUTTURA CONTENENTE TUTTI GLI AVVISI
                     avvisi.add(avviso);
                 }

                // AGGIUNGE LA STRUTTURA "AVVISI" NELL'ADAPTER PER VISUALIZZARLO NELLA RECYCLER
                //Effettua l'ordinamento degli avvisi per importanza
                Collections.sort(avvisi, new Comparator<Avviso>() {
                    @Override
                    public int compare(Avviso avviso1, Avviso avviso2) {
                        return avviso1.compareTo(avviso2);
                    }
                });


                adapter = new AdapterBachecaAvvisi(avvisi);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {  }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) {  }

        });
    }
}
