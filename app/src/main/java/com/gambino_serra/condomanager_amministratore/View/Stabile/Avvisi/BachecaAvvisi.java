/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.gambino_serra.condomanager_amministratore.View.Stabile.Avvisi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.util.ArrayList;
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
    private String idStabile;

    private Bundle bundle;

    private String uidCondomino;
    private String stabile;
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
            bundle.putString("uidFornitore", idStabile);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_bacheca_avvisi, container, false);
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


        //lettura uid condomino -->  codice fiscale stabile, uid amministratore
        uidCondomino = idStabile;
        //firebaseAuth.getCurrentUser().getUid().toString();
        firebaseDB = FirebaseDB.getAvvisi();

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

                        try{

                            Avviso avviso = new Avviso(
                                    avvisoMap.get("id").toString(),
                                    avvisoMap.get("amministratore").toString(),
                                    avvisoMap.get("stabile").toString(),
                                    avvisoMap.get("oggetto").toString(),
                                    avvisoMap.get("descrizione").toString(),
                                    avvisoMap.get("scadenza").toString()
                            );


                            // TODO : if ( avvisoMap.get("scadenza").toDate  <  oggy  )
                            avvisi.add(avviso);
                        }
                        catch (NullPointerException e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Non riesco ad aprire l'oggetto "+ e.toString(), Toast.LENGTH_LONG).show();
                        }


                        adapter = new AdapterBachecaAvvisi(avvisi);
                        recyclerView.setAdapter(adapter);
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


}
