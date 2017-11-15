package com.gambino_serra.condomanager_amministratore.View.SezioneStabile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
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
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Stabile;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
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
    ImageView Mappa;
    String lat= "";
    String lon= "";

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
        return view;
        }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, getActivity().MODE_PRIVATE);

        if (getActivity().getIntent().getExtras() != null)
            {
            bundle = getActivity().getIntent().getExtras();
            idStabile = bundle.get("idStabile").toString(); // prende l'identificativo per fare il retrieve delle info

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idStabile", idStabile);
            editor.apply();
            }
        else
            {
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
        stabileMap = new HashMap<String,Object>();
        residenti = new ArrayList<String>();

        mNomeStabile = (TextView) getActivity().findViewById(R.id.D_NomeStabile);
        mIndirizzoStabile = (TextView) getActivity().findViewById(R.id.D_IndirizzoStabile);
        Mappa = (ImageView) getActivity().findViewById(R.id.btnMappa);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView_residenti);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Mappa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+lat+","+lon));
                startActivity(intent);
                }
            });

        // PER VISUALIZZARE LE INFO SULLO STABILE
        firebaseDB = FirebaseDB.getStabili();

        // punto al condominio selezionato
        Query info;
        info = firebaseDB.orderByKey().equalTo(idStabile);

        info.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                stabileMap = new HashMap<String,Object>();
                stabileMap.put("idStabile", dataSnapshot.getKey().toString());

                for ( DataSnapshot child : dataSnapshot.getChildren()) {
                    stabileMap.put( child.getKey().toString(), child.getValue().toString() );
                    }

                Stabile stabile = new Stabile(
                        stabileMap.get("idStabile").toString(),
                        stabileMap.get("nome").toString(),
                        stabileMap.get("indirizzo").toString(),
                        stabileMap.get("latitudine").toString(),
                        stabileMap.get("longitudine").toString()
                        );

                mNomeStabile.setText( stabile.getNomeStabile() );
                mIndirizzoStabile.setText( stabile.getIndirizzo() );

                lat = stabile.getLatitudine();
                lon = stabile.getLongitudine();
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
        listaCondomini = FirebaseDB.getStabili().child(idStabile).child("lista_condomini");

        Query prova = listaCondomini.orderByKey();

        prova.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
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
            public void onCancelled(FirebaseError firebaseError) { }

        });

    }
}