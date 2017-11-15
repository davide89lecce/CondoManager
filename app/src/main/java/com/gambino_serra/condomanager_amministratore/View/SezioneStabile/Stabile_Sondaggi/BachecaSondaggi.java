package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Sondaggi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Sondaggio;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiSondaggi.*;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BachecaSondaggi extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";
    String username;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    //private ArrayList<Segnalazione> data;
    public static View.OnClickListener myOnClickListener;
    Context context;
    String condominoNome;
    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private String idStabile;
    private String stabile;
    Map<String, Object> sondaggioMap;
    ArrayList<Sondaggio> sondaggi;

    Bundle bundle;

    private OnFragmentInteractionListener mListener;

    public BachecaSondaggi(){}

    public static BachecaSondaggi newInstance() {
        BachecaSondaggi fragment = new BachecaSondaggi();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, getActivity().MODE_PRIVATE);

        if (getActivity().getIntent().getExtras() != null) {

            bundle = getActivity().getIntent().getExtras();
            idStabile = bundle.get("idStabile").toString(); // prende l'identificativo per fare il retrieve delle info

            android.content.SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idStabile", idStabile);
            editor.apply();

        } else {

            idStabile = sharedPrefs.getString("idStabile", "").toString();

            bundle = new Bundle();
            bundle.putString("idStabile", idStabile);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sezionestabile_tab_sondaggi, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onStart() {
        super.onStart();

        context = getContext();
        firebaseAuth = FirebaseAuth.getInstance();
        //data = new ArrayList<Segnalazione>();
        sondaggioMap = new HashMap<String, Object>();
        sondaggi = new ArrayList<Sondaggio>();

        myOnClickListener = new MyOnClickListener(context);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view1);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        firebaseDB = FirebaseDB.getCondomini().child(idStabile);

                Query prova;
                prova = FirebaseDB.getSondaggi().orderByChild("stabile").equalTo(idStabile);

                prova.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        sondaggioMap = new HashMap<String,Object>();
                        sondaggioMap.put("id", dataSnapshot.getKey());

                        for ( DataSnapshot child : dataSnapshot.getChildren() ) {
                            sondaggioMap.put(child.getKey(), child.getValue());
                        }

                        //try{

                        final Sondaggio sondaggio = new Sondaggio(
                                sondaggioMap.get("id").toString(),
                                sondaggioMap.get("stabile").toString(),
                                sondaggioMap.get("tipologia").toString(),
                                sondaggioMap.get("oggetto").toString(),
                                sondaggioMap.get("descrizione").toString(),
                                sondaggioMap.get("data").toString(),
                                sondaggioMap.get("stato").toString()
                        );

                        sondaggi.add(sondaggio);

                        adapter = new AdapterBachecaSondaggi(sondaggi);
                        recyclerView.setAdapter(adapter);

                        // TODO: no nella bacheca dovrà visualizzare solo quelli aperti e nello storico solo quelli chiusi
//                        // Controlla se il sondaggio recuperato è aperto
//                        if ( sondaggio.getStato().equals("aperto") ) {
//                            // Controlla se il condomino in questione ha già partecipato al sondaggio
//                            Firebase controllo;
//                            controllo = FirebaseDB.getSondaggi()
//                                    .child(sondaggio.getIdSondaggio())
//                                    .child("partecipanti")
//                                    .child(uidCondomino);
//
//                            controllo.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    //String compilato = dataSnapshot.getValue().toString();
//
//                                    if ( sondaggio.getStato().equals("aperto") && !dataSnapshot.exists()) {
//                                        sondaggi.add(sondaggio);
//
//                                        adapter = new AdapterBachecaSondaggi(sondaggi);
//                                        recyclerView.setAdapter(adapter);
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(FirebaseError firebaseError) {
//                                }
//                            });
//                        }
                        //}
                        //catch (NullPointerException e) {
                        //    Toast.makeText(getActivity().getApplicationContext(), "Non riesco ad aprire l'oggetto "+ e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) { }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * Menu to allow an interaction in this Menu to be communicated
     * to the activity and potentially other fragments contained in that activity.
     */
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
            detailsSegnalazione(v);
        }

        private void detailsSegnalazione(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewIdSondaggio);
            String selectedName = (String) textViewName.getText();
            TextView textViewType = (TextView) viewHolder.itemView.findViewById(R.id.D_TipologiaSondaggio);
            String selectedType = (String) textViewType.getText();

            Bundle bundle = new Bundle();
            bundle.putString("idSondaggio", selectedName);

            switch ( selectedType ){
                case "rating":
                {
                    Intent intent = new Intent(context, DettaglioSondaggioRating.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                }

                case "scelta multipla":
                case "scelta singola":
                {
                    Intent intent = new Intent(context, DettaglioSondaggioSceltaMS.class);
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
