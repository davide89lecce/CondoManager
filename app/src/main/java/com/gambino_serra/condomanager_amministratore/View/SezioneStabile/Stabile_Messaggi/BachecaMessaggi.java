package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Messaggi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Messaggio;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BachecaMessaggi extends Fragment {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";
    String username;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.Adapter adapter2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerViewNEW;
    public static View.OnClickListener myOnClickListener;
    public static View.OnClickListener myOnClickListener2;
    Context context;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private Bundle bundle;
    private String idStabile;
    String mittente;

    Map<String, Object> messaggioMap;
    ArrayList<Messaggio> messaggi;
    ArrayList<Messaggio> messaggiNuovi;


    public static BachecaMessaggi newInstance() {
        BachecaMessaggi fragment = new BachecaMessaggi();
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
        return inflater.inflate(R.layout.sezionestabile_tab_messaggi, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        messaggioMap = new HashMap<String,Object>();
        messaggi = new ArrayList<Messaggio>();
        messaggiNuovi = new ArrayList<Messaggio>();

        myOnClickListener = new MyOnClickListener(context);
        myOnClickListener2 = new MyOnClickListener2(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_Messaggi);
        recyclerView.setHasFixedSize(true);
        recyclerViewNEW = (RecyclerView) getActivity().findViewById(R.id.my_recycler_MessaggiNuovi);
        recyclerViewNEW.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewNEW.setLayoutManager(layoutManager2);
        recyclerViewNEW.setItemAnimator(new DefaultItemAnimator());


        // Riferimento alla tabella contenente tutti i Messaggi
        firebaseDB = FirebaseDB.getMessaggiCondomino();
        // Query per identificare tutti i messaggi appartenenti allo stabile desiderato
        Query prova;

        prova = FirebaseDB.getMessaggiCondomino().orderByChild("stabile").equalTo(idStabile);

        prova.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                messaggioMap = new HashMap<String,Object>();
                messaggioMap.put("idMessaggio", dataSnapshot.getKey());

                for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                    messaggioMap.put(child.getKey(), child.getValue());
                }

                RecuperaMittente( messaggioMap );

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



    private String DataFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String timestamp = sdf.format(new Date());
        return timestamp;
    }


    private void RecuperaMittente ( final Map<String,Object> M ){

        Query nomeCondomino;
        nomeCondomino = FirebaseDB.getCondomini().child( M.get("uidCondomino").toString()).child("nome");
        //Firebase nomeMittente = FirebaseDB.getCondomini().child(messaggioMap.get("condomino").toString()).child("nome");
        nomeCondomino.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mittente = dataSnapshot.getValue(String.class);

                //try{
                Messaggio messaggio = new Messaggio(
                        M.get("id").toString(),
                        mittente,
                        M.get("stabile").toString(),
                        M.get("tipologia").toString(),
                        M.get("messaggio").toString(),
                        M.get("data").toString(),
                        M.get("url").toString(),
                        "ciao","ciao", //TODO
                        M.get("letto").toString()
                );

                // AGGIUNGE L'AVVISO NELLA STRUTTURA CONTENENTE TUTTI GLI AVVISI
                // TODO : ordina per data

                //}
                //catch (NullPointerException e) {
                //  Toast.makeText(getActivity().getApplicationContext(), "Non riesco ad aprire l'oggetto "+ e.toString(), Toast.LENGTH_LONG).show();
                //}

                if ( messaggio.getLetto().equals("no") ) {
                    // AGGIUNGE LA STRUTTURA "AVVISI" NELL'ADAPTER PER VISUALIZZARLO NELLA RECYCLER
                    messaggiNuovi.add(messaggio);
                    adapter2 = new AdapterBachecaMessaggiNonLetti(messaggiNuovi, getActivity());
                    recyclerViewNEW.setAdapter(adapter2);
                }else{
                    messaggi.add(messaggio);
                    adapter = new AdapterBachecaMessaggiLetti(messaggi, getActivity());
                    recyclerView.setAdapter(adapter);
                }

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

    private static class MyOnClickListener2 extends AppCompatActivity implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener2(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            detailsSegnalazione(v);
        }

        private void detailsSegnalazione(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerViewNEW.findViewHolderForPosition(selectedItemPosition);
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