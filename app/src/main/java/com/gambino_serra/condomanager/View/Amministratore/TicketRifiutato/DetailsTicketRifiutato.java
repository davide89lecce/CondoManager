package com.gambino_serra.condomanager.View.Amministratore.TicketRifiutato;

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
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestCondominio;
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestCondomino;
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestFornitore;
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestSegnalazioni;
import com.gambino_serra.condomanager.Model.JsonSerializable.JsonCondominio;
import com.gambino_serra.condomanager.Model.JsonSerializable.JsonCondomino;
import com.gambino_serra.condomanager.Model.JsonSerializable.JsonFornitore;
import com.gambino_serra.condomanager.Model.JsonSerializable.JsonSegnalazione;
import com.gambino_serra.condomanager.tesi.R;
import com.gambino_serra.condomanager.View.Amministratore.Dialog.DialogChiamaFornitore;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

/**
 * Created by condomanager on 06/03/17.
 */

public class DetailsTicketRifiutato extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String amministratore = "";
    String idSegnalazione = "";
    String data = "";
    String segnalazione = "";
    String usernameCondomino = "";
    String condominio = "";
    String telefonoFornitore = "";
    String fornitore = "";
    String stato = "";
    String idCondominio = "";
    String condomino = "";

    TextView dataT;
    TextView segnalazioneT;
    TextView condominoT;
    TextView fornitoreT;
    TextView condominioT;
    LinearLayout btnCambiaFornitore;
    LinearLayout btnChiama;
    TextView descrizioneStatoT;
    ImageView imageStatoI;

    Response.Listener<String> listener = this;
    Response.ErrorListener errorListener = this;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_rifiutato);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        amministratore = sharedPrefs.getString(LOGGED_USER, "").toString();

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
        btnChiama = (LinearLayout) findViewById(R.id.btnChiama);
        btnCambiaFornitore = (LinearLayout) findViewById(R.id.btnCambiaFornitore);
        descrizioneStatoT = (TextView) findViewById(R.id.descrizioneStatoD);
        imageStatoI = (ImageView) findViewById(R.id.imageStatoD);

        HTTPRequestSegnalazioni.getSegnalazione(idSegnalazione, getApplicationContext(), this, this);

        btnChiama.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DialogChiamaFornitore();
                bundle.putString("telefono",telefonoFornitore);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "ChiamaFornitore");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });

        btnCambiaFornitore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bundle = new Bundle();
                bundle.putString("data", data);
                bundle.putString("idSegnalazione", idSegnalazione);
                bundle.putString("segnalazione", segnalazione);
                bundle.putString("condomino", usernameCondomino);
                bundle.putString("idCondominio", idCondominio);
                bundle.putString("condominio", condominio);
                bundle.putString("condominoNome",condomino);

                Intent in = new Intent(getApplicationContext(), TicketCategoria.class);
                in.putExtras(bundle);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

                if(stato.equals("A")){
                    descrizioneStatoT.setText("Questa segnalazione è in attesa di essere convalidata.\nSelezionare \"INVIA\" per inviarla al fornitore");
                    imageStatoI.setImageResource(R.drawable.user);
                }else if(stato.equals("B")){
                    descrizioneStatoT.setText("Questa richiesta è in attesa \ndi essere presa in carico dal fornitore");
                    imageStatoI.setImageResource(R.drawable.sand_clock2);
                }else if(stato.equals("C")){
                    descrizioneStatoT.setText("Questa richiesta è in corso d'opera");
                    imageStatoI.setImageResource(R.drawable.wrench);
                }else if(stato.equals("D")){
                    descrizioneStatoT.setText("Questa richiesta è stata rifiutata.\nSelezionare cambia fornitore per \ninviarla ad un altro fornitore");
                    imageStatoI.setImageResource(R.drawable.error);
                }else if(stato.equals("E") || stato.equals("G")){
                    descrizioneStatoT.setText("I lavori per questo intervento sono stati conclusi");
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
                condominioT.setText(productList.get(0).condominio);

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("condominio", condominio);
                editor.apply();

            } else if (response.contains("[{\"usernameC\"")) {

                final ArrayList<JsonCondomino> productList = new JsonConverter<JsonCondomino>().toArrayList(response, JsonCondomino.class);

                condominoT.setText(productList.get(0).cognome + " " + productList.get(0).nome);
                condomino = productList.get(0).cognome + " " + productList.get(0).nome;

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("condominoNome",condomino);
                editor.apply();

            } else if (response.contains("[{\"usernameF\"")) {

                final ArrayList<JsonFornitore> productList = new JsonConverter<JsonFornitore>().toArrayList(response, JsonFornitore.class);

                fornitoreT.setText(productList.get(0).azienda);
                telefonoFornitore = productList.get(0).telefono;
            }
        }
    }


}