package com.gambino_serra.condomanager_amministratore.View.Utente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.gambino_serra.condomanager_amministratore.View.Amministratore.Home.AmministratoreHomeActivity;
import com.gambino_serra.condomanager_amministratore.View.Condomino.Home.CondominoHomeActivity;
import com.gambino_serra.condomanager_amministratore.View.Fornitore.Home.FornitoreHomeActivity;

import static com.gambino_serra.condomanager_amministratore.Controller.Login.checkFields;
import static com.gambino_serra.condomanager_amministratore.Controller.Login.checkLogin;
import static com.gambino_serra.condomanager_amministratore.Controller.Login.startRegisterActivity;

public class LoginActivity extends BaseActivity
        implements Response.Listener<String>, Response.ErrorListener {

    EditText etUsername, etPassword;
    Button btnLogin;
    Button btnRegister;
    String username;
    String password;
    Response.Listener<String> listener = this;
    Response.ErrorListener errorListener = this;

    private static final String MY_PREFERENCES = "preferences";
    private static final String TIPO_UTENTE = "tipoUtente";
    private static final String LOGGED_USER = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        userState();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            /**
             * Il metodo permette di acquisire i dati inseriti dall'utente, verifica che i campi
             * di testo non siano vuoti ed effettua il login.
             *
             * @param v istanza della View
             */
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                showProgressDialog();
                checkFields(getApplicationContext(), listener, errorListener, username, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            /**
             * Il metodo permette di accedere alla schermata di registrazione
             * di un nuovo utente.
             * @param v istanza della View
             */
            @Override
            public void onClick(View v) {
                startRegisterActivity(getApplicationContext());
            }
        });

    }

    /**
     * Il metodo imposta il messaggio della Dialog.
     */
    @Override
    protected void setMessage() {
        mProgressDialog.setMessage(getString(R.string.login));
    }

    /**
     * Il metodo e' invocato alla risposta (dati ricevuti da database altervista) della richiesta di autenticazione.
     * @param response
     */
    @Override
    public void onResponse(String response) {

        hideProgressDialog();
        checkLogin(response, getApplicationContext(),username);
    }

    /**
     * Il metodo viene invocato in caso di problemi nella ricezione della risposta.
     * @param error
     */
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    /**
     *  Il metodo verifica che le SharedPreferences contengano dati, nel caso contrario l'utente risulterà non connesso.
     */
    public void userState(){

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        if (!sharedPrefs.getAll().isEmpty()) {
            getStatusAndGoHome();
        }
    }

    /**
     * Il metodo verifica il tipo di utente e lo indirizza nella sua Home Activity.
     */
    private void getStatusAndGoHome() {

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        //SharedPreferences.Editor editor =sharedPrefs.edit();
        //editor.putString(TIPO_UTENTE,"A");
        //editor.putString(LOGGED_USER,"condomanager_amministratore");
        //editor.apply();
        if (sharedPrefs.getString(TIPO_UTENTE, "").equals("A")) {
            Intent in = new Intent(this, AmministratoreHomeActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        } else if (sharedPrefs.getString(TIPO_UTENTE, "").equals("F")) {
            Intent in = new Intent(this, FornitoreHomeActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        } else if (sharedPrefs.getString(TIPO_UTENTE, "").equals("C")) {
            Intent in = new Intent(this, CondominoHomeActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
        }
    }
}
