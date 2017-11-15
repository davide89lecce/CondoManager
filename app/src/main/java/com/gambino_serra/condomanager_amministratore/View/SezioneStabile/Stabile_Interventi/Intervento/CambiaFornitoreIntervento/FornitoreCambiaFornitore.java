package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.CambiaFornitoreIntervento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FornitoreCambiaFornitore extends AppCompatActivity {
    // the Menu initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MY_PREFERENCES = "preferences";
    private String mParam1;
    private String mParam2;
    private String uidAmministratore;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<Fornitore> data;
    public static View.OnClickListener myOnClickListener;
    Context context;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ImageView BottoneMappa;

    View listaCategorie;
    static String categoria;
    static String idStabile;
    static String foto;
    static String idIntervento;
    Bundle bundle;
    Map<String, Object> FornitoreMap;
    ArrayList<Fornitore> fornitori;

    //private FornitoreCambiaFornitore.OnFragmentInteractionListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleziona_fornitore_intervento);

        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uidAmministratore = firebaseUser.getUid().toString();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);


        if (getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            idStabile = bundle.get("idStabile").toString();
            foto = bundle.get("foto").toString();
            idIntervento = bundle.get("idInterventoRifiutato").toString();
            categoria = bundle.get("categoria").toString(); // prende l'identificativo per fare il retrieve delle info
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idStabile", idStabile);
            editor.putString("foto", foto);
            editor.putString("categoria", categoria);
            editor.putString("idInterventoRifiutato", idIntervento);
            editor.apply();
        } else {
            idStabile = sharedPrefs.getString("idStabile", "").toString();
            foto = sharedPrefs.getString("foto", "").toString();
            categoria = sharedPrefs.getString("categoria", "").toString();
            idIntervento = sharedPrefs.getString("idInterventoRifiutato", "").toString();
            bundle = new Bundle();
            bundle.putString("idStabile", idStabile);
            bundle.putString("categoria", categoria);
            bundle.putString("idInterventoRifiutato", idIntervento);
        }


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        data = new ArrayList<Fornitore>();
        FornitoreMap = new HashMap<String,Object>();
        fornitori = new ArrayList<Fornitore>();
    }

    @Override
    public void onStart() {
        super.onStart();

        myOnClickListener = new FornitoreCambiaFornitore.MyOnClickListener(context);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //lettura uid amministratore
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();
        firebaseDB = FirebaseDB.getAmministratori().child(uidAmministratore);



        //lettura di TUTTI i fornitori nella rubrica
        Query query = firebaseDB.child("rubrica_fornitori").orderByKey();

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // Inizializziamo un contenitore per i dati del fornitore che ci interessa
                //FornitoreMap = new HashMap<String,Object>();
                // Il primo dato da inserire nel map è l'UID che ricavo dalla rubrica
               //FornitoreMap.put("uidRubrica", dataSnapshot.getKey().toString());

                // Passo l'UID alla funzione che punterà alla tabella fornitori per recuperare gli altri dati
                prendiDettagliFornitore( dataSnapshot.getKey().toString() );
                        //FornitoreMap.get("uidRubrica").toString() );

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


    public void prendiDettagliFornitore(String idFornitore) {
        // in ingresso l'UID del fornitore del quale ci interessano i dettagli

        Query query2;
        query2 = FirebaseDB.getFornitori().orderByKey().equalTo( idFornitore );
        // la query sarà fatta sull'uid passato

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //HashMap temporaneo per immagazzinare i dati del fornitore
                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Fornitore
                // recuperiamo i dati per inserirli nel MAP
                FornitoreMap.put("uid", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    FornitoreMap.put(child.getKey().toString(), child.getValue().toString());
                }

                Fornitore fornitore = new Fornitore(
                        FornitoreMap.get("uid").toString(),
                        FornitoreMap.get("nome").toString(),
                        FornitoreMap.get("nome_azienda").toString(),
                        FornitoreMap.get("categoria").toString(),
                        FornitoreMap.get("partita_iva").toString(),
                        FornitoreMap.get("telefono").toString(),
                        FornitoreMap.get("indirizzo").toString(),
                        FornitoreMap.get("email").toString()
                );

                if ( categoria.equals(fornitore.getCategoria()) ) {
                    fornitori.add(fornitore);
                    adapter = new AdapterFornitoreCambiaFornitore(fornitori);
                    recyclerView.setAdapter(adapter);
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




    private static class MyOnClickListener extends AppCompatActivity implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            inserisciDatiFinali(v);
        }

        private void inserisciDatiFinali(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewUidFornitore);
            String selectedName = (String) textViewName.getText();

            //TODO salva anche nelle share non si sa mai
            Bundle bundle = new Bundle();
            bundle.putString("idStabile", idStabile);
            bundle.putString("foto", foto);
            bundle.putString("categoria", categoria);
            bundle.putString("uidFornitore", selectedName);
            bundle.putString("idInterventoRifiutato", idIntervento);

            Log.d("Ciao", " " + idIntervento);

            Intent intent = new Intent(context, TicketCambiaFornitore.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}