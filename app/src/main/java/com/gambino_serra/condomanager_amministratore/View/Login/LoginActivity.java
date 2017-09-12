package com.gambino_serra.condomanager_amministratore.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity  {

    //Firebase
    private Firebase DBref;                 //Riferimento al DB
    private FirebaseAuth firebaseAuth;      //Oggetto per l'autenticazione
    private FirebaseUser utente;            //oggetto per definire l'utente del DB
    private Firebase userRef;               //posso conservare altri riferimenti ad oggetto che punto a piacere


    EditText etUsername, etPassword;
    Button btnLogin;
    Button btnRegister;
    String username;
    String password;

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

        firebaseAuth = FirebaseAuth.getInstance();

        Firebase.setAndroidContext(this);

/**
 *  Effettua il LogIn anche se l'app viene chiusa e riaperta
 *  non abbiamo bisogno delle Shared perchè l'utente firebase viene salvato
 *  una volta effettuato il primo accesso
 */
        if (firebaseAuth.getCurrentUser() != null) {
            // PRENDO IL RIFERIMENTO DELL'UTENTE LOGGATO

            //controllo nel caso in cui l'utente sia loggato con un altra app che utilizza lo stesso DB
            checkTipologia( firebaseAuth.getCurrentUser().getUid().toString() );
        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//
//            /**
//             * Il metodo permette di accedere alla schermata di registrazione di un nuovo utente.
//             */
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(getApplicationContext(), RegisterAmministratoreActivity.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        btnLogin.setOnClickListener(new View.OnClickListener() {

        /**
         * Il metodo permette di acquisire i dati inseriti dall'utente, verifica che i campi di testo non siano vuoti ed effettua il login.
         */
        @Override
        public void onClick(View v) {

            username = etUsername.getText().toString().trim();
            password = etPassword.getText().toString().trim();

            showProgressDialog();

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                        {
                        // Ad operazione effettuata, tramite l'if controllo che l'utente restituito non sia null, ovvero che i dati siano validi

                        if (firebaseAuth.getCurrentUser() != null)
                            {
                            // PRENDO IL RIFERIMENTO DELL'UTENTE LOGGATO
                            utente = firebaseAuth.getCurrentUser();
                            checkTipologia(utente.getUid().toString());
                            }
                        else
                            {
                            Toast.makeText(getApplicationContext(), "UTENTE NON VALIDO", Toast.LENGTH_SHORT).show(); }
                            }
                    else
                        {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), "DATI NON CORRETTI", Toast.LENGTH_SHORT).show();
                        }
                 }
            });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        }

    /**
     * Il metodo imposta il messaggio della Dialog.
     */
    @Override
    protected void setMessage() {
        mProgressDialog.setMessage(getString(R.string.login));
        }



//    private void writeSharedPreferences(String username, String password, String tipo_utente){
//
//        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
//        SharedPreferences.Editor editor =sharedPrefs.edit();
//        editor.putString(TIPO_UTENTE,tipo_utente);
//        editor.putString(LOGGED_USER,username);
//        editor.apply();
//        }


    private void checkTipologia(final String UID) {

        //PUNTO NELLA TABELLA "CONDOMINI" ALL'UTENTE LOGGATO

        hideProgressDialog();

        userRef = FirebaseDB.getAmministratori().child(UID);
        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ( dataSnapshot.exists() )
                    {
                    Toast.makeText(getApplicationContext(), "LOGIN EFFETTUATO", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(), MainDrawer.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    }
                else
                    { Toast.makeText(getApplicationContext(), "UTENTE DI ALTRA TIPOLOGIA", Toast.LENGTH_SHORT).show(); }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }
}


/**
//   UTILIZZO TIPICO DI VALUE_EVENT_LISTENER
//        public void searchemail(String email){
//
//            Firebase ref = new Firebase("https://<myfirebase>.firebaseio.com/users");
//            Query queryRef = ref.orderByChild("Email").equalTo(email);
//
//            ValueEventListener listener = new ValueEventListener() {
//
//                @Override
//                public void onDataChanged(DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        for (DataSnapshot child: snapshot.getChildren()) {
//                            homeintent.putExtra("key", child.getKey());
//                            startActivity(homeintent);
//                            break; // exit for loop, we only want one match
//                          }
//                    }
//                    else {
//                        Toast toast = Toast.makeText(this, "email not found", Toast.LENGTH_SHORT);
//                    }
//                }
//            };
//            queryRef.addValueEventListener(listener);
//        }
 */