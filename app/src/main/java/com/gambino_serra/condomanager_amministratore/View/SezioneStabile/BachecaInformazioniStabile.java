package com.gambino_serra.condomanager_amministratore.View.SezioneStabile;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Avviso;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BachecaInformazioniStabile extends android.support.v4.app.Fragment {

    private static final String MY_PREFERENCES = "preferences";
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    Context context;

    private Firebase firebaseDB;
    private FirebaseAuth firebaseAuth;
    private SimpleDateFormat today;

    TextView mNomeStabile;
    TextView mIndirizzoStabile;

    private Bundle bundle;
    private String idStabile;
    private String nomeStabile;
    private String indirizzoStabile;
    Map<String, Object> stabileMap;
    ArrayList<String> residenti;


    public static BachecaInformazioniStabile newInstance() {
        BachecaInformazioniStabile fragment = new BachecaInformazioniStabile();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dettaglio_stabile, container, false);

        mNomeStabile = (TextView) view.findViewById(R.id.D_NomeStabile);
        mIndirizzoStabile = (TextView) view.findViewById(R.id.D_IndirizzoStabile);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_residenti);

        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

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
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        String nomeResidente;
        stabileMap = new HashMap<String,Object>();
        residenti = new ArrayList<String>();

        //myOnClickListener = new BachecaAvvisi.MyOnClickListener(context);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        // PER VISUALIZZARE LE INFO SULLO STABILE
        firebaseDB = FirebaseDB.getStabili();

        // punto al condominio selezionato
        Query info;
        info = firebaseDB.orderByKey().equalTo(idStabile);

        info.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                stabileMap = new HashMap<String,Object>();
                for ( DataSnapshot child : dataSnapshot.getChildren()) {
                    stabileMap.put( child.getKey().toString(), child.getValue().toString() );
                }

                String nome = stabileMap.get("nome").toString();
                String indirizzo = stabileMap.get("indirizzo").toString();
                //stampainfo ( stabileMap);
                mNomeStabile.setText( nome );
                mIndirizzoStabile.setText( indirizzo );
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });



        // PER STAMPARE LA LISTA DEI RESIDENTI NELLO STABILE
        Firebase listaCondomini;
        listaCondomini = FirebaseDB.getCondomini().child(idStabile).child("lista_condomini");

        Query prova = listaCondomini.orderByKey();


        // TODO: NON SI ATTIVA
        prova.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //if ( dataSnapshot.getValue().toString().equals("true") )
                        RecuperaNomi (  dataSnapshot.getKey().toString() );
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

    }

    private void stampainfo(Map<String, Object> stabileMap) {

        nomeStabile = stabileMap.get("nome").toString();
        indirizzoStabile = stabileMap.get("indirizzo").toString();

        mNomeStabile.setText( nomeStabile );
        mIndirizzoStabile.setText( indirizzoStabile );
    }


    private void RecuperaNomi ( final String uidCondomino ){


        Firebase firebaseRef = FirebaseDB.getCondomini().child(uidCondomino);

        Query nomeCondomino;
        nomeCondomino = firebaseRef.child("nome");

        nomeCondomino.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                residenti.add( dataSnapshot.getValue(String.class) );
                adapter = new AdapterListaResidenti( residenti );
                recyclerView.setAdapter( adapter );
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        //mNomeStabile.setText( stabile.get("nome").toString() );
        //mIndirizzoStabile.setText( stabile.get("indirizzo").toString() );
    }

}
