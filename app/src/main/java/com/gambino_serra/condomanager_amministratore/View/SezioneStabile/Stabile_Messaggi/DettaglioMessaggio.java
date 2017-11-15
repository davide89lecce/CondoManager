package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Messaggi;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Messaggio;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Login.BaseActivity;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoTicketIntervento.CreaTicketFinale;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

//import com.firebase.ui.storage.images.FirebaseImageLoader;


public class DettaglioMessaggio extends BaseActivity {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String username = "";
    String idMessaggio;
    String data = "";
    String tipologia = "";
    String descrizione = "";
    String foto = "";
    String mittente = "";

    Messaggio messaggio;

    TextView messaggio_data;
    TextView messaggio_tipologia;
    TextView messaggio_descrizione;
    ImageView messaggio_foto;
    ConstraintLayout btn_processa;

    private FirebaseDB firebaseDB;
    private Firebase dbMessaggi;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorage;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    Map<String, Object> MessaggioMap;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sezionestabile_dettaglio_messaggio);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();


        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            idMessaggio = bundle.get("idMessaggio").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idMessaggio", idMessaggio);
            editor.apply();
        }
        else {
            idMessaggio = sharedPrefs.getString("idMessaggio", "").toString();
            bundle = new Bundle();
            bundle.putString("idMessaggio", idMessaggio);
        }

        messaggio_data = (TextView) findViewById(R.id.messaggio_data);
        messaggio_descrizione = (TextView) findViewById(R.id.messaggio_descrizione);
        messaggio_tipologia = (TextView) findViewById(R.id.messaggio_tipologia);
        messaggio_foto = (ImageView) findViewById(R.id.messaggio_foto);
        btn_processa = (ConstraintLayout) findViewById(R.id.btnProcessa);

        messaggio = new Messaggio();

        // Segna il messaggio come letto
        Firebase messRef = FirebaseDB.getMessaggiCondomino().child(idMessaggio).child("letto");
        messRef.setValue("si");

        Query prova;
        prova = FirebaseDB.getMessaggiCondomino().orderByKey().equalTo(idMessaggio);

        prova.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String temp = dataSnapshot.getKey().toString();

                MessaggioMap = new HashMap<String, Object>();
                MessaggioMap.put("id", dataSnapshot.getKey());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    MessaggioMap.put(child.getKey(), child.getValue());
                }

                RecuperaMittente( MessaggioMap );

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
    protected void onStart() {
        super.onStart();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        btn_processa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Se ho recuperato le info con successo (ovvero evito che si clicchi prima del caricamento)
               if ( ! messaggio.getFoto().isEmpty() ) {
                   // Usiamo le shared a causa della presenza di una dialog tra questa e l'altra activity
                   SharedPreferences.Editor editor = sharedPrefs.edit();
                   editor.putString("foto", messaggio.getFoto());
                   editor.apply();

                   DialogFragment newFragment = new DialogProcessaMessaggio();
                   newFragment.show(getFragmentManager(), "ProcessaMessaggio");
                   overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

               }else{}

                //bundle = new Bundle();
                //bundle.putString("idStabile", messaggio.getIdStabile() );
                //bundle.putString("foto", messaggio.getFoto() );

                //Intent intent = new Intent(getApplicationContext(), .class);
                //intent.putExtras(bundle);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);

            }
        });
    }


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            Uri link = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(MessaggioMap.get("foto").toString());

            Picasso.with(getApplicationContext()).load( link ).fit().centerCrop().into(messaggio_foto);
        }


    }*/




    /**
     * Il metodo imposta il messaggio della NuovoAvviso.
     */
    @Override
    protected void setMessage() {
        mProgressDialog.setMessage("Caricamento Immagine");
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
                messaggio = new Messaggio(
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

                messaggio_tipologia.setText(messaggio.getTipologia());
                messaggio_data.setText(messaggio.getData());
                messaggio_descrizione.setText(messaggio.getMessaggio());


                // Se nel messaggio è effettivamente presente una foto
                if ( MessaggioMap.get("foto").toString() != "-" ) {
                    showProgressDialog();
                    String filephoto = MessaggioMap.get("foto").toString();
                    // Riferimento al file con cartella Photo e successivamente il nome dell'immagine
                    StorageReference photoRef = mStorage.child("Photo").child( filephoto );


                    Picasso.with(getApplicationContext()).load( messaggio.getFoto() ).fit().centerCrop().into(messaggio_foto) ;
                }

                //Toast.makeText(getApplicationContext(), "Non riesco ad aprire l'oggetto " + e.toString(), Toast.LENGTH_LONG).show();
                hideProgressDialog();

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
