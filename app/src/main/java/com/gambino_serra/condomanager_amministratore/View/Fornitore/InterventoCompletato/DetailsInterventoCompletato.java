package com.gambino_serra.condomanager_amministratore.View.Fornitore.InterventoCompletato;

import android.app.DialogFragment;
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
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestFornitore;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestIntervento;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Fornitore.HTTPRequestSegnalazioni;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonAmministratore;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonCondominio;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonCondomino;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonIntervento;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonSegnalazione;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.gambino_serra.condomanager_amministratore.View.Condomino.Dialog.DialogChiamaAmministratore;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class DetailsInterventoCompletato extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String amministratore = "";
    String idSegnalazione = "";
    String data = "";
    String segnalazione = "";
    String usernameCondomino = "";
    String condominio = "";
    String impianto = "";
    String fornitore = "";
    String stato = "";
    String idCondominio = "";
    String impiantoNome = "";
    String azienda = "";
    String condomino = "";
    String indirizzo = "";
    String telefono = "";
    String dataIntervento = "";
    String intervento = "";

    TextView dataT;
    TextView segnalazioneT;
    TextView condominoT;
    TextView impiantoT;
    TextView fornitoreT;
    TextView statoT;
    TextView condominioT;
    LinearLayout btnArchivia;
    LinearLayout btnChiama;
    TextView amministratoreT;
    TextView indirizzoT;
    TextView dataInterventoT;
    TextView interventoT;
    TextView descrizioneStatoT;
    ImageView imageStatoI;

    Response.Listener<String> listener = this;
    Response.ErrorListener errorListener = this;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervento_completato);

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
        //impiantoT = (TextView) findViewById(R.id.impiantoD);
        fornitoreT = (TextView) findViewById(R.id.fornitoreD);
        //statoT = (TextView) findViewById(R.id.statoD);
        condominioT = (TextView) findViewById(R.id.condominioD);
        amministratoreT = (TextView) findViewById(R.id.amministratoreD);
        indirizzoT = (TextView) findViewById(R.id.indirizzoD);
        btnArchivia = (LinearLayout) findViewById(R.id.btnArchivia);
        btnChiama = (LinearLayout) findViewById(R.id.btnChiama);
        dataInterventoT = (TextView) findViewById(R.id.dataInterventoD);
        interventoT = (TextView) findViewById(R.id.interventoD);
        descrizioneStatoT = (TextView) findViewById(R.id.descrizioneStatoD);
        imageStatoI = (ImageView) findViewById(R.id.imageStatoD);

        HTTPRequestSegnalazioni.getSegnalazione(idSegnalazione, getApplicationContext(), this, this);
        HTTPRequestIntervento.getIntervento(idSegnalazione,getApplicationContext(),listener,errorListener);

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

        btnArchivia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //stato = "F";
                //HTTPRequestSegnalazioni.updateSegnalazione(idSegnalazione, segnalazione, usernameCondomino, idCondominio, "1", fornitore, stato, getApplicationContext(), listener, errorListener);

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
                fornitore = productList.get(0).fornitore;
                stato = productList.get(0).stato;

                dataT.setText(data);
                segnalazioneT.setText(segnalazione);
                condominioT.setText(condominio);

                HTTPRequestCondomino.getCondomino(usernameCondomino, getApplicationContext(), this, this);
                HTTPRequestCondominio.getCondominio(idCondominio, getApplicationContext(), this, this);
                HTTPRequestFornitore.getFornitore(fornitore,getApplicationContext(),listener,errorListener);

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
                editor.putString("fornitore", fornitore);
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

            }else if (response.contains("[{\"idIntervento\"")) {

                final ArrayList<JsonIntervento> productList = new JsonConverter<JsonIntervento>().toArrayList(response, JsonIntervento.class);

                dataIntervento = productList.get(0).data;
                intervento = productList.get(0).intervento;
                dataInterventoT.setText(dataIntervento);
                interventoT.setText(intervento);

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("dataIntervento", dataIntervento);
                editor.putString("intervento", intervento);
                editor.apply();

            } else if (response.equals("ok")) {

                //DialogFragment newFragment = new DialogConfermaArchiviazione();
                //newFragment.setArguments(bundle);
                //newFragment.show(getFragmentManager(), "ConfermaArchiviazione");
                //overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }

        }
    }


}