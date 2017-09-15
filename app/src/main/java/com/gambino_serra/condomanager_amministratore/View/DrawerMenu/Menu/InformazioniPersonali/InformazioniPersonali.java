package com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.InformazioniPersonali;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Amministratore;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;


public class InformazioniPersonali extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView Tnome;
    TextView Tstudio;
    TextView Ttelefono;
    TextView Temail;
    TextView Tsede;
    String uidAmministratore;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    Map<String, Object> dettaglioAmministratoreMap;

    public InformazioniPersonali() {}

    /**
     * Use this factory method to create a new instance of this Menu using the provided parameters.
     */
    public static com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.InformazioniPersonali.InformazioniPersonali newInstance(String param1, String param2) {
        com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.InformazioniPersonali.InformazioniPersonali fragment = new com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.InformazioniPersonali.InformazioniPersonali();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this Menu
        View view = inflater.inflate(R.layout.bacheca_informazioni_personali,container,false);
         Tnome = (TextView) view.findViewById(R.id.AmmNome);
         Tstudio = (TextView) view.findViewById(R.id.AmmStudio);
         Tsede = (TextView) view.findViewById(R.id.AmmSede);
         Ttelefono = (TextView) view.findViewById(R.id.AmmTelefono);
         Temail = (TextView) view.findViewById(R.id.AmmEmail);
        return view;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uidAmministratore = firebaseUser.getUid().toString();

        Query prova;
        prova = FirebaseDB.getAmministratori().orderByKey().equalTo(uidAmministratore);
        prova.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                dettaglioAmministratoreMap = new HashMap<String, Object>();
                dettaglioAmministratoreMap.put("uidAmministratore", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    dettaglioAmministratoreMap.put(child.getKey(), child.getValue());
                    }

                Amministratore amministratore = new Amministratore(
                        dettaglioAmministratoreMap.get("uidAmministratore").toString(),
                        dettaglioAmministratoreMap.get("nome").toString(),
                        dettaglioAmministratoreMap.get("codicefiscale").toString(),
                        dettaglioAmministratoreMap.get("studio").toString(),
                        dettaglioAmministratoreMap.get("sede").toString(),
                        dettaglioAmministratoreMap.get("email").toString() ,
                        dettaglioAmministratoreMap.get("telefono").toString()
                        );

                Tnome.setText(amministratore.getNome());
                Tstudio.setText(amministratore.getStudio());
                Tsede.setText(amministratore.getSede());
                Ttelefono.setText(amministratore.getTelefono());
                Temail.setText(amministratore.getEmail());
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
}