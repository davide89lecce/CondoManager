package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiSondaggi;


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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Sondaggio;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AggiornamentiSondaggi extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<Sondaggio> data;
    public static View.OnClickListener myOnClickListener;
    private Firebase firebaseDB;
    Context context;


    private FirebaseAuth firebaseAuth;
    private String uidAmministratore;
    Map<String, Object> sondaggioMap;
    ArrayList<Sondaggio> sondaggi;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aggiornamenti_notifiche_sondaggi);

        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();

        context = getApplicationContext();

        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<Sondaggio>();
        sondaggioMap = new HashMap<String, Object>();
        sondaggi = new ArrayList<Sondaggio>();


        myOnClickListener = new MyOnClickListener(context);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_notifiche_sondaggi);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        //lettura di TUTTI i sondaggi dell'amministratore
        Query query;
        query = FirebaseDB.getSondaggi().orderByChild("amministratore").equalTo(uidAmministratore);

        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                recupareDatiSondaggio( dataSnapshot.getKey().toString() );

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



    private void recupareDatiSondaggio(String idSondaggio) {

        Query sondaggioRef;
        sondaggioRef = FirebaseDB.getSondaggi().orderByKey().equalTo(idSondaggio);
        sondaggioRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                sondaggioMap = new HashMap<String, Object>();
                sondaggioMap.put("idSondaggio", dataSnapshot.getKey() );

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    sondaggioMap.put(child.getKey(), child.getValue());
                }

                final Sondaggio sondaggio = new Sondaggio(
                        sondaggioMap.get("idSondaggio").toString(),
                        sondaggioMap.get("stabile").toString(),
                        sondaggioMap.get("tipologia").toString(),
                        sondaggioMap.get("oggetto").toString(),
                        sondaggioMap.get("descrizione").toString(),
                        sondaggioMap.get("data").toString(),
                        sondaggioMap.get("stato").toString()
                );

                Firebase stabileRef = FirebaseDB.getStabili().child(sondaggio.getStabile()).child("nome");
                stabileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sondaggio.setStabile(dataSnapshot.getValue().toString());

                        if ( sondaggio.getStato().equals("aperto")) {
                            sondaggi.add(sondaggio);
                            adapter = new AdapterAggiornamentiSondaggi(sondaggi);
                            recyclerView.setAdapter(adapter);
                        }

                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });
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
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewIdSondaggio);
            String selectedName = (String) textViewName.getText();
            TextView textViewStabile = (TextView) viewHolder.itemView.findViewById(R.id.D_StabileSondaggio);
            String nomeStabile = (String) textViewStabile.getText();
            TextView textViewType = (TextView) viewHolder.itemView.findViewById(R.id.D_TipologiaSondaggio);
            String selectedType = (String) textViewType.getText();

            Bundle bundle = new Bundle();
            bundle.putString("idSondaggio", selectedName);
            bundle.putString("nomeStabile", nomeStabile);


            switch ( selectedType ){
                case "rating":
                {
                    Intent intent = new Intent( context , DettaglioSondaggioRating.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                }

                case "scelta multipla":
                case "scelta singola":
                {
                    Intent intent = new Intent( context, DettaglioSondaggioSceltaMS.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                }

                default:
            }

        }
    }

}