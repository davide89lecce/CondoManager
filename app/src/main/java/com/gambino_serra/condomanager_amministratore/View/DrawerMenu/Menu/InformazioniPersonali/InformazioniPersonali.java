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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;


public class InformazioniPersonali extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView Tnome_azienda;
    TextView Tnome;
    TextView Tcategoria;
    TextView Ttelefono;
    TextView Tpartita_iva;
    TextView Temail;
    TextView Tindirizzo;
    String uidFornitore;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    Map<String, Object> dettaglioFornitoreMap;

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
         Tnome = (TextView) view.findViewById(R.id.FornNome);
         Tnome_azienda = (TextView) view.findViewById(R.id.FornNomeAzienda);
         Tcategoria = (TextView) view.findViewById(R.id.FornSettore);
         Tpartita_iva = (TextView) view.findViewById(R.id.FornPartIVA);
         Ttelefono = (TextView) view.findViewById(R.id.FornTelefono);
         Temail = (TextView) view.findViewById(R.id.FornEmail);
         Tindirizzo = (TextView) view.findViewById(R.id.FornIndirizzo);
        return view;
        }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uidFornitore = firebaseUser.getUid().toString();

        Query prova;
        prova = FirebaseDB.getFornitori().orderByKey().equalTo(uidFornitore);
        prova.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                dettaglioFornitoreMap = new HashMap<String, Object>();
                dettaglioFornitoreMap.put("uidFornitore", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    dettaglioFornitoreMap.put(child.getKey(), child.getValue());
                    }

                Fornitore fornitore = new Fornitore(
                        dettaglioFornitoreMap.get("uidFornitore").toString(),
                        dettaglioFornitoreMap.get("nome").toString(),
                        dettaglioFornitoreMap.get("nome_azienda").toString(),
                        dettaglioFornitoreMap.get("categoria").toString(),
                        dettaglioFornitoreMap.get("partita_iva").toString(),
                        dettaglioFornitoreMap.get("telefono").toString(),
                        dettaglioFornitoreMap.get("indirizzo").toString(),
                        dettaglioFornitoreMap.get("email").toString());

                Tnome.setText(fornitore.getNome());
                Tnome_azienda.setText(fornitore.getNome_azienda());
                Tcategoria.setText(fornitore.getCategoria());
                Tpartita_iva.setText(fornitore.getPartita_iva());
                Ttelefono.setText(fornitore.getTelefono());
                Temail.setText(fornitore.getEmail());
                Tindirizzo.setText(fornitore.getIndirizzo());
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
