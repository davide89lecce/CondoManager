package com.gambino_serra.condomanager_amministratore.View.Fornitore.InterventoInProgramma;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestAmministratore;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestCondominio;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestCondomino;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestSegnalazioni;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonAmministratore;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonCondominio;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonCondomino;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonSegnalazione;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.gambino_serra.condomanager_amministratore.View.Condomino.Dialog.DialogChiamaAmministratore;
import com.gambino_serra.condomanager_amministratore.View.Fornitore.Dialog.DialogRapportoIntervento;
import com.gambino_serra.condomanager_amministratore.View.Fornitore.Home.FornitoreHomeActivity;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class DetailsInterventoInProgramma extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String amministratore = "";
    String idSegnalazione = "";
    String data = "";
    String segnalazione = "";
    String usernameCondomino = "";
    String condominio = "";
    String fornitore = "";
    String stato = "";
    String idCondominio = "";
    String condomino = "";
    String indirizzo = "";
    String telefono = "";

    TextView dataT;
    TextView segnalazioneT;
    TextView condominoT;
    TextView fornitoreT;
    TextView condominioT;
    LinearLayout chiudiIntervento;
    LinearLayout btnChiama;
    TextView amministratoreT;
    TextView indirizzoT;
    TextView descrizioneStatoT;
    ImageView imageStatoI;

    Response.Listener<String> listener = this;
    Response.ErrorListener errorListener = this;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervento_in_programma);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        fornitore = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            idSegnalazione = bundle.get("idSegnalazione").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idSegnalazione", idSegnalazione);
            editor.apply();

        } else {

            idSegnalazione = sharedPrefs.getString("idSegnalazione", "").toString();

            bundle = new Bundle();
            bundle.putString("idSegnalazione", idSegnalazione);

        }

        dataT = (TextView) findViewById(R.id.dataD);
        segnalazioneT = (TextView) findViewById(R.id.segnalazioneD);
        condominoT = (TextView) findViewById(R.id.condominoD);
        fornitoreT = (TextView) findViewById(R.id.fornitoreD);
        condominioT = (TextView) findViewById(R.id.condominioD);
        amministratoreT = (TextView) findViewById(R.id.amministratoreD);
        indirizzoT = (TextView) findViewById(R.id.indirizzoD);
        chiudiIntervento = (LinearLayout) findViewById(R.id.accettaIntervento);
        btnChiama = (LinearLayout) findViewById(R.id.btnChiama);
        descrizioneStatoT = (TextView) findViewById(R.id.descrizioneStatoD);
        imageStatoI = (ImageView) findViewById(R.id.imageStatoD);

        HTTPRequestSegnalazioni.getSegnalazione(idSegnalazione, getApplicationContext(), this, this);

        btnChiama.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DialogChiamaAmministratore();
                bundle.putString("telefono",telefono);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "ChiamaAmministratore");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

            }
        });

        chiudiIntervento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bundle = new Bundle();
                bundle.putString("idSegnalazione", idSegnalazione);

                DialogFragment newFragment = new DialogRapportoIntervento();
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "RapportoIntervento");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);


            }
        });


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

        if(!response.equals("null")) {

            if (response.contains("[{\"idSegnalazione\"")) {

                final ArrayList<JsonSegnalazione> productList = new JsonConverter<JsonSegnalazione>().toArrayList(response, JsonSegnalazione.class);


                data = productList.get(0).data;
                segnalazione = productList.get(0).segnalazione;
                usernameCondomino = productList.get(0).condomino;
                idCondominio = productList.get(0).idCondominio.toString();
                stato = productList.get(0).stato;

                dataT.setText(data);
                segnalazioneT.setText(segnalazione);
                condominioT.setText(condominio);

                HTTPRequestCondomino.getCondomino(usernameCondomino, getApplicationContext(), this, this);
                HTTPRequestCondominio.getCondominio(idCondominio, getApplicationContext(), this, this);

                if(stato.equals("B")){
                    descrizioneStatoT.setText("Richiesta d'intervento in attesa di essere convalidata");
                    imageStatoI.setImageResource(R.drawable.edificio);
                }else if(stato.equals("C")){
                    descrizioneStatoT.setText("Intervento in programma");
                    imageStatoI.setImageResource(R.drawable.wrench);
                }else if(stato.equals("E") || stato.equals("F")){
                    descrizioneStatoT.setText("Lavoro terminato.\nSelezionare \"ARCHIVIA\" per archiviare l\'intervento");
                    imageStatoI.setImageResource(R.drawable.checked);
                }

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("data", data);
                editor.putString("segnalazione", segnalazione);
                editor.putString("condomino", usernameCondomino);
                editor.putString("idCondominio", idCondominio);
                editor.putString("stato", stato);
                editor.apply();

            } else if (response.contains("[{\"idCondominio\"")) {

                final ArrayList<JsonCondominio> productList = new JsonConverter<JsonCondominio>().toArrayList(response, JsonCondominio.class);

                condominio = productList.get(0).condominio;
                condominioT.setText(condominio);
                indirizzo = productList.get(0).indirizzo;
                indirizzoT.setText(indirizzo);

                HTTPRequestAmministratore.getAmministratore(productList.get(0).ammministratore, getApplicationContext(), this, this);

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("condominio", condominio);
                editor.putString("indirizzo", indirizzo);
                editor.apply();

            } else if (response.contains("[{\"usernameC\"")) {

                final ArrayList<JsonCondomino> productList = new JsonConverter<JsonCondomino>().toArrayList(response, JsonCondomino.class);

                condominoT.setText(productList.get(0).cognome + " " + productList.get(0).nome);
                condomino = productList.get(0).cognome + " " + productList.get(0).nome;

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("condominoNome",condomino);
                editor.apply();

            } else if (response.contains("[{\"usernameA\"")) {

                final ArrayList<JsonAmministratore> productList = new JsonConverter<JsonAmministratore>().toArrayList(response, JsonAmministratore.class);

                amministratore = productList.get(0).studio;
                amministratoreT.setText(amministratore);
                telefono = productList.get(0).telefono;

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("amministratore",condomino);
                editor.putString("telefono",condomino);
                editor.apply();

            } else if (response.contains("interventoInserito")) {

                segnalazione = segnalazione.replace("'","\\'");
                usernameCondomino = usernameCondomino.replace("'","\\'");
                fornitore = fornitore.replace("'","\\'");
                stato = "E";
                HTTPRequestSegnalazioni.updateSegnalazione(idSegnalazione, segnalazione, usernameCondomino, idCondominio, "1", fornitore, stato, getApplicationContext(), listener, errorListener);

            } else if (response.contains("ok")) {

                Intent intent = new Intent(this, FornitoreHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }

        }
    }




}