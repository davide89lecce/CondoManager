package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiMessaggi;


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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Messaggio;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiMessaggi.DettaglioMessaggio;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AggiornamentiMessaggi extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<Messaggio> data;
    public static View.OnClickListener myOnClickListener;
    private Firebase firebaseDB;
    Context context;


    private FirebaseAuth firebaseAuth;
    private String uidAmministratore;
    Map<String, Object> messaggioMap;
    ArrayList<Messaggio> messaggi;

    ArrayList<String> rapportiAziende;
    Integer numeroAggiornamentiTicket = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aggiornamenti_notifiche_messaggi);

        firebaseAuth = FirebaseAuth.getInstance();
    }




    @Override
    public void onStart() {
        super.onStart();

        context = getApplicationContext();

        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<Messaggio>();
        messaggioMap = new HashMap<String, Object>();
        messaggi = new ArrayList<Messaggio>();

        // Avvaloro i nuovi rierimenti al layout


        myOnClickListener = new MyOnClickListener(context);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_notifiche_messaggi);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        //lettura di TUTTI i fornitori

        Query query;
        query = FirebaseDB.getMessaggiCondomino().orderByChild("uidAmministratore").equalTo(uidAmministratore);

        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                messaggioMap = new HashMap<String, Object>();
                messaggioMap.put("idMessaggio", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    messaggioMap.put(child.getKey(), child.getValue());
                }

                recuperaDettagliMessaggio(messaggioMap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }




    public void recuperaDettagliMessaggio(final Map<String, Object> messaggioMap) {

        final Map<String, Object> messaggioMap2 = new HashMap<String, Object>();


        Firebase nomeStabile = FirebaseDB.getStabili()
                .child(messaggioMap.get("stabile").toString())
                .child("nome");
        nomeStabile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messaggioMap2.put("nomeStabile", dataSnapshot.getValue(String.class));

                Firebase nomeCondomino = FirebaseDB.getCondomini()
                        .child(messaggioMap.get("uidCondomino").toString())
                        .child("nome");

                nomeCondomino.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        messaggioMap2.put("nomeCondomino", dataSnapshot.getValue(String.class));

                        Messaggio msg = new Messaggio(
                                messaggioMap.get("idMessaggio").toString(),
                                messaggioMap.get("uidCondomino").toString(),
                                messaggioMap.get("stabile").toString(),
                                messaggioMap.get("tipologia").toString(),
                                messaggioMap.get("messaggio").toString(),
                                messaggioMap.get("data").toString(),
                                messaggioMap.get("foto").toString(),
                                messaggioMap2.get("nomeCondomino").toString(),
                                messaggioMap2.get("nomeStabile").toString(),
                                messaggioMap.get("letto").toString()
                        );

                        if ( msg.getLetto().equals("no")) {
                            messaggi.add(msg);
                            adapter = new AdapterAggiornamentiMessaggi(messaggi, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }
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
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewIdSegnalazione);
            String selectedName = (String) textViewName.getText();

            Bundle bundle = new Bundle();
            bundle.putString("idMessaggio", selectedName);

            Intent intent = new Intent(context, DettaglioMessaggio.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}