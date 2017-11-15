package com.gambino_serra.condomanager_amministratore.View.Home.BachecaFornitori;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaFornitori.AggiuntaFornitore.SelezionaCategoria;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ListaFornitori extends Fragment {
    // the Menu initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    private FloatingActionButton BottoneMappa;

    Map<String, Object> FornitoreMap;
    ArrayList<Fornitore> fornitori;

    private com.gambino_serra.condomanager_amministratore.View.Home.BachecaFornitori.ListaFornitori.OnFragmentInteractionListener mListener;

    public ListaFornitori() {}

    /**
     * Use this factory method to create a new instance of this Menu using the provided parameters.
     */
    public static ListaFornitori newInstance() {
        ListaFornitori fragment = new ListaFornitori();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

    /**
     * Dato che siamo in un fragment, non possiamo usare un semplice setContent view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this Menu
        return inflater.inflate(R.layout.bacheca_lista_fornitori, container, false);
        }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            }
        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        }

    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uidAmministratore = firebaseUser.getUid().toString();
        data = new ArrayList<Fornitore>();
        FornitoreMap = new HashMap<String,Object>();
        fornitori = new ArrayList<Fornitore>();

        BottoneMappa = (FloatingActionButton) getActivity().findViewById(R.id.BottoneMappa);
        BottoneMappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mappa = new Intent(getActivity(), SelezionaCategoria.class);
                startActivity(mappa);
            }
        });


        myOnClickListener = new ListaFornitori.MyOnClickListener(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //lettura uid amministratore
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();
        firebaseDB = FirebaseDB.getAmministratori().child(uidAmministratore);



        //lettura di TUTTI i fornitori

        Firebase rubrica = FirebaseDB.getAmministratori().child(uidAmministratore).child("rubrica_fornitori");

        rubrica.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // Passo l'UID alla funzione che punterà alla tabella fornitori per recuperare gli altri dati
                prendiDettagliFornitore( dataSnapshot.getKey().toString() );

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


    public void prendiDettagliFornitore( String uid ) {
        // in ingresso l'UID del fornitore del quale ci interessano i dettagli

        Query query2;
        query2 = FirebaseDB.getFornitori().orderByKey().equalTo( uid );
        // la query sarà fatta sull'uid passato

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //HashMap temporaneo per immagazzinare i dati del fornitore
                // Inizializziamo un contenitore per i dati del fornitore che ci interessa
                FornitoreMap = new HashMap<String,Object>();

                // Il primo dato da inserire nel map è l'UID che ricavo dalla rubrica
                FornitoreMap.put("uid", dataSnapshot.getKey().toString());


                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Fornitore
                // recuperiamo i dati per inserirli nel MAP
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    FornitoreMap.put(child.getKey(), child.getValue());
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

                fornitori.add(fornitore);
                adapter = new AdapterListaFornitori(fornitori);
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



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        }


    private static class MyOnClickListener extends AppCompatActivity implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
            }

        @Override
        public void onClick(View v) {
            detailsFornitore(v);
            }

        private void detailsFornitore(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewUidFornitore);
            String selectedName = (String) textViewName.getText();

            Bundle bundle = new Bundle();
            bundle.putString("uidFornitore", selectedName);

            Intent intent = new Intent(context, DettaglioFornitore.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}