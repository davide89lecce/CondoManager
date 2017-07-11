package com.gambino_serra.condomanager.View.Amministratore.Segnalazione;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestSegnalazioni;
import com.gambino_serra.condomanager.tesi.R;
import com.gambino_serra.condomanager.View.Amministratore.Dialog.DialogConfermaInvioTicket;

public class SegnalazioneDescrizione extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String amministratore = "";
    String fornitore = "";
    String stato = "";
    LinearLayout btnInvia;
    LinearLayout btnIndietro;
    Context context;
    Response.Listener<String> listener = this;
    Response.ErrorListener errorListener = this;
    Bundle bundle;
    String idSegnalazione;
    String segnalazione;
    String usernameCondomino;
    String idCondominio;
    String condominio;
    String condomino;
    EditText segnalazioneE;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnalazione_descrizione);
        context = getApplicationContext();

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        amministratore = sharedPrefs.getString(LOGGED_USER, "").toString();

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            idSegnalazione = bundle.get("idSegnalazione").toString();
            segnalazione = bundle.get("segnalazione").toString();
            usernameCondomino = bundle.get("condomino").toString();
            idCondominio = bundle.get("idCondominio").toString();
            condominio = bundle.get("condominio").toString();
            condomino = bundle.get("condominoNome").toString();
            fornitore = bundle.get("fornitore").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idSegnalazione", idSegnalazione);
            editor.putString("segnalazione", segnalazione);
            editor.putString("condomino", usernameCondomino);
            editor.putString("idCondominio", idCondominio);
            editor.putString("condominio", condominio);
            editor.putString("condominoNome",condomino);
            editor.putString("fornitore",fornitore);
            editor.apply();

        } else {

            idSegnalazione = sharedPrefs.getString("idSegnalazione", "").toString();
            segnalazione = sharedPrefs.getString("segnalazione", "").toString();
            usernameCondomino = sharedPrefs.getString("condomino", "").toString();
            idCondominio = sharedPrefs.getString("idCondominio", "").toString();
            condominio = sharedPrefs.getString("condominio", "").toString();
            condomino = sharedPrefs.getString("condominoNome", "").toString();

            bundle = new Bundle();
            bundle.putString("idSegnalazione", idSegnalazione);
            bundle.putString("segnalazione", segnalazione);
            bundle.putString("condomino", usernameCondomino);
            bundle.putString("idCondominio", idCondominio);
            bundle.putString("condominio", condominio);
            bundle.putString("condominoNome",condomino);
            bundle.putString("fornitore",fornitore);

        }

        segnalazioneE = (EditText) findViewById(R.id.segnalazioneD);
        btnInvia = (LinearLayout) findViewById(R.id.btnInvia);
        btnIndietro = (LinearLayout) findViewById(R.id.btnIndietro);

        segnalazioneE.setText(segnalazione);

        btnInvia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stato = "B";
                segnalazione = segnalazioneE.getText().toString().replace("'","\\'");
                usernameCondomino = usernameCondomino.replace("'","\\'");
                fornitore = fornitore.replace("'","\\'");
                HTTPRequestSegnalazioni.updateSegnalazione(idSegnalazione, segnalazione, usernameCondomino, idCondominio, "1", fornitore, stato, context, listener, errorListener);
            }
        });

        btnIndietro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

        if(response.equals("ok")) {

            DialogFragment newFragment = new DialogConfermaInvioTicket();
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "ConfermaSegnalazione");
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

        }
    }

}