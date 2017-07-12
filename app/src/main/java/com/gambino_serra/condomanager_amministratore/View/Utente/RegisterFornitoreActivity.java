package com.gambino_serra.condomanager_amministratore.View.Utente;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import static com.gambino_serra.condomanager_amministratore.Controller.Register.createFornitore;
import static com.gambino_serra.condomanager_amministratore.Controller.Register.createUtente;
import static com.gambino_serra.condomanager_amministratore.Controller.Register.setPrefs;

//import org.jetbrains.annotations.NotNull;

/**
 * La classe gestisce la registrazione dell'utente.
 */
public class RegisterFornitoreActivity extends BaseActivity
        implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = "RegisterAmministratoreActivity";
    private static final String MY_PREFERENCES = "kiuPreferences";
    private static final String EMAIL = "email";
    private static final String TIPO_UTENTE = "tipoUtente";
    private static final String LOGGED_USER = "logged_user";
    private static final String CONV_NAME = "_conv_name";

    Response.Listener<String> listener = this;
    Response.ErrorListener errorListener = this;

    EditText etUsername;
    EditText etPassword;
    EditText etAzienda;
    EditText etIndirizzo;
    EditText etTelefono;
    EditText etEmail;
    EditText etPartitaIVA;
    EditText etResponsabile;
    Button btnConferma;


    String username;
    String password;
    String azienda;
    String indirizzo;
    String telefono;
    String email;
    String partitaIVA;
    String responsabile;
    String tipoUtente = "F";

    String test = "ok";
    String test1 = "ok";
    String testData1 = "";
    String testData2 = "";
    int check_values = 0;
    boolean psw_len = true;
    private boolean check1 = true;
    private int count = 0;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornitore_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        etAzienda = (EditText) findViewById(R.id.azienda);
        etIndirizzo = (EditText) findViewById(R.id.indirizzo);
        etTelefono = (EditText) findViewById(R.id.telefono);
        etEmail = (EditText) findViewById(R.id.email);
        etPartitaIVA = (EditText) findViewById(R.id.partitaIVA);
        etResponsabile = (EditText) findViewById(R.id.responsabile);
        btnConferma = (Button) findViewById(R.id.btnConferma);



        btnConferma.setOnClickListener(new View.OnClickListener() {

            /**
             * Il metodo gestisce le fasi di registrazione di un nuovo utente.
             * Si occupa inoltre del controllo di consistenza dei dati inseriti,
             * segnalando tramite messagi sulla UI gli eventuali errori sollevati.
             *
             * @param v istanza della View
             */
            @Override
            public void onClick(View v) {

              //  checkValues(etUsername, etPassword, etAmministratore, etEmail, etSede, etStudio, etTelefono, tipoUtente, getApplicationContext(), listener, errorListener);
                createUtente(etUsername.getText().toString(),etPassword.getText().toString(),tipoUtente,getApplicationContext(),listener,errorListener);

            }
        });

    }

    @Override
    protected void setMessage() {

    }

    /**
     * Il metodo e' invocato alla risposta (dati ricevuti da database altervista) della richiesta di registrazione.
     * @param response
     */
    @Override
    public void onResponse(String response) {
        if (!response.contains("ok")) {

            hideProgressDialog();
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), R.string.registration_problem, Snackbar.LENGTH_LONG);
            View view1 = snack.getView();
            TextView tv = (TextView) view1.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snack.show();

        }else if(response.contains("ok_utente")) {

            createFornitore(etUsername.getText().toString(), etAzienda.getText().toString(), etIndirizzo.getText().toString(), etTelefono.getText().toString(), etEmail.getText().toString(), etPartitaIVA.getText().toString(),
                    etResponsabile.getText().toString(), getApplicationContext(), listener, errorListener);

        }else if(response.contains("ok_fornitore")){

            DialogFragment newFragment = new DialogRegistrationSuccess();
            newFragment.show(getFragmentManager(), "DialogRegistrationSuccess");

            setPrefs(getApplicationContext(),etUsername.getText().toString(),tipoUtente);
        }
    }

    /**
     * Il metodo viene invocato in caso di problemi nella ricezione della risposta.
     * @param error
     */
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), R.string.connection_failed, Toast.LENGTH_SHORT).show();
        hideProgressDialog();
    }

}