package com.gambino_serra.condomanager_amministratore.View.Home.BachecaFornitori.AggiuntaFornitore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaFornitori.ListaFornitori;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AggiungiFornitore extends AppCompatActivity {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    TextView Tnome_azienda;
    TextView Tnome;
    TextView Tcategoria;
    TextView Ttelefono;
    TextView Temail;
    TextView Tindirizzo;
    ImageView BTNchiama_fornitore;
    Button btnAggiungiForn;
    Button btnAnnulla;
    String uidFornitore;
    String uidAmministratore;
    String telefono;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    Map<String, Object> dettaglioFornitoreMap;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_fornitore_aggiungi);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uidAmministratore = firebaseUser.getUid().toString();
        firebaseDB = FirebaseDB.getAmministratori().child(uidAmministratore);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        if (getIntent().getExtras() != null)
            {
            bundle = getIntent().getExtras();
            uidFornitore = bundle.get("uidFornitore").toString(); // prende l'identificativo per fare il retrieve delle info
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("uidfornitore", uidFornitore);
            editor.apply();
            }
        else
            {
            uidFornitore = sharedPrefs.getString("uidFornitore", "").toString();
            bundle = new Bundle();
            bundle.putString("uidFornitore", uidFornitore);
            }

        Tnome_azienda = (TextView) findViewById(R.id.FornnomeAzienda);
        Tnome = (TextView) findViewById(R.id.FornTitolare);
        Tcategoria = (TextView) findViewById(R.id.FornSettore);
        Ttelefono = (TextView) findViewById(R.id.FornTelefono);
        Temail = (TextView) findViewById(R.id.FornEmail);
        Tindirizzo = (TextView) findViewById(R.id.FornIndirizzo);
        BTNchiama_fornitore = (ImageView) findViewById(R.id.imageViewFornChiama);
        btnAggiungiForn = (Button) findViewById(R.id.btnAggiungiFornitore);
        btnAnnulla = (Button) findViewById(R.id.btnAnnulla);


        // chiama una query per farsi restituire una """Tabella""" con tutti i figli aventi lo stesso
        // uidFornitore, ovvero solo uno, ma in questo modo possiamo inserire un addChild event listener
        // che considererà come Key gli uid (solo uno) e come Value l'intero sottoalbero
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

                Tnome_azienda.setText(fornitore.getNome_azienda());
                Tnome.setText(fornitore.getNome());
                Tcategoria.setText(fornitore.getCategoria());
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

        BTNchiama_fornitore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                telefono = "1234567890";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefono));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                }
            });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), SelezionaCategoria.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                }
            });

        btnAggiungiForn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firebaseDB.child("rubrica_fornitori").child(uidFornitore).setValue("true");
                Intent back = new Intent(getApplicationContext(), MainDrawer.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                Toast.makeText(getApplicationContext(), "Fornitore aggiunto alla rubrica personale", Toast.LENGTH_SHORT).show();
                }
            });

    }
}