package com.gambino_serra.condomanager_amministratore.View.Home.BachecaStabili;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.ListStabile;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BachecaStabili extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private ArrayList<ListStabile> data;
    public static View.OnClickListener myOnClickListener;
    Context context;

    private FirebaseAuth firebaseAuth;
    private String uidAmmministratore;
    Map<String, Object> stabileMap;
    ArrayList<ListStabile> stabili;
    ImageView BottoneMappa;

    public static BachecaStabili newInstance() {
        BachecaStabili fragment = new BachecaStabili();
        return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_stabili, container, false);
        }

    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<ListStabile>();
        stabileMap = new HashMap<String,Object>();
        stabili = new ArrayList<ListStabile>();

        myOnClickListener = new MyOnClickListener(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //lettura uid condomino -->  codice fiscale stabile, uid amministratore
        uidAmmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        Query query;
        query = FirebaseDB.getStabili().orderByChild("amministratore").equalTo(uidAmmministratore);


        // la query seleziona solo gli interventi con un determinato fornitore
        //il listener lavora sui figli della query, ovvero su titti gli interventi recuperati
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                //HashMap temporaneo per immagazzinare i dati di un ticket
                stabileMap = new HashMap<String, Object>();
                stabileMap.put("id", dataSnapshot.getKey()); //primo campo del MAP

                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Interv
                // recuperiamo i dati per inserirli nel MAP
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    stabileMap.put(child.getKey(), child.getValue());
                }
                // recuperaDatiStabile (stabileMap);

                try {
                    ListStabile listStabile = new ListStabile(
                            stabileMap.get("id").toString(),
                            stabileMap.get("nome").toString(),
                            stabileMap.get("indirizzo").toString()
                    );

                    stabili.add(listStabile);

                    // Utilizziamo l'adapter per popolare la recycler view
                    adapter = new AdapterBachecaStabili(stabili);
                    recyclerView.setAdapter(adapter);

                } catch (NullPointerException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Non riesco ad aprire l'oggetto " + e.toString(), Toast.LENGTH_LONG).show();
                }
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

        BottoneMappa = (ImageView) getActivity().findViewById(R.id.BottoneMappa);
        BottoneMappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mappa = new Intent(getActivity(), MappaStabili.class);
                startActivity(mappa);
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
            detailsStabile(v);
        }

        private void detailsStabile(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.D_IDIntervento);
            String selectedName = (String) textViewName.getText();

            Bundle bundle = new Bundle();
            bundle.putString("idStabile", selectedName);

            Intent intent = new Intent(context, DettaglioStabile.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            }
    }


}