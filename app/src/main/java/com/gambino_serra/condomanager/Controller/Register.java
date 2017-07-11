package com.gambino_serra.condomanager.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.gambino_serra.condomanager.Model.HTTPRequest.Utente.HTTPRequestRegistration;
import com.gambino_serra.condomanager.tesi.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by condomanager on 11/02/17.
 */

public class Register {

    private static final String MY_PREFERENCES = "preferences";
    private static final String TIPO_UTENTE = "tipoUtente";
    private static final String LOGGED_USER = "username";

    private static final String KEY_DBSERVERNAME = "localhost";
    private static final String KEY_DBUSERNAME = "damicogianluca";
    private static final String KEY_DBPASSWORD = "";
    private static final String KEY_DBNAME = "my_damicogianluca";
    private static final String KEY_DB_REQUEST = "http://damicogianluca.altervista.org/register.php";
    private static final String KEY_DB_REQUEST2 = "http://damicogianluca.altervista.org/register_amministratore.php";

    public static void checkValues(EditText etUsername, EditText etPassword, EditText etAmministratore, EditText etEmail, EditText etSede, EditText etStudio, EditText etTelefono, String tipoUtente, Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        int check_values = 0;
        String username = "";
        String password = "";
        String studio = "";
        String amministratore = "";
        String sede = "";
        String telefono = "";
        String email = "";

        String test = "ok";
        String test1 = "ok";
        String testData1 = "";
        String testData2 = "";
        boolean check1 = true;
        int count = 0;
        boolean psw_len = true;

        if (checkInsertValues(etUsername) == 1) {
            username = etUsername.getText().toString();
            testData1 = "kor";
        } else {
            check1 = false;
            testData1 = "kob";
        }

        if (checkInsertValues(etPassword) == 1 && pswlen(etPassword.getText().toString())) {
            password = etPassword.getText().toString();
            testData1 = "kor";
        } else {
            check1 = false;
            testData1 = "kob";
        }

        if (checkInsertValues(etAmministratore) == 1) {
            amministratore = etAmministratore.getText().toString();
            testData1 = "kor";
        } else {
            check1 = false;
            testData1 = "kob";
        }

        if (checkInsertValues(etEmail) == 1) {
            email = etEmail.getText().toString().trim();
            test1 = "kok";
            check1 = false;
        } else {
            check1 = false;
            test = "ko";
        }

        if (checkInsertValues(etSede) == 1) {
            sede = etSede.getText().toString();
            testData1 = "kor";
        } else {
            check1 = false;
            testData1 = "kob";
        }

        if (checkInsertValues(etStudio) == 1) {
            studio = etStudio.getText().toString();
            testData1 = "kor";
        } else {
            check1 = false;
            testData1 = "kob";
        }

        if (checkInsertValues(etTelefono) == 1) {
            telefono = etTelefono.getText().toString();
            testData1 = "kor";
        } else {
            check1 = false;
            testData1 = "kob";
        }


        //Toast

        if (testData1.contains("kob") || testData2.contains("kob")) {
            Toast.makeText(context.getApplicationContext(), R.string.name_surname_insert, Toast.LENGTH_LONG).show();
            testData1 = "ko";
            testData2 = "ko";

        } else if (testData1.contains("kor") && testData2.contains("kor")) {
            if (checkInsertValuesText(username) == true && checkInsertValuesText(studio) == true
                    && checkInsertValuesText(sede) == true && checkInsertValuesText(amministratore) == true) {
                count++;
            } else {
                Toast.makeText(context.getApplicationContext(), R.string.name_surname_invalid, Toast.LENGTH_LONG).show();
            }
        }


        if (check1 == false && test1.contains("kok")) {
            if (!validateEmail(email)) {
                Toast.makeText(context.getApplicationContext(), R.string.mail_invalid, Toast.LENGTH_SHORT).show();
            } else {
                count++;
            }
        }
        if (check1 == false && test.contains("ko")) {
            Toast.makeText(context.getApplicationContext(), R.string.empty_fields, Toast.LENGTH_SHORT).show();
            test = "ok";
        } else if (psw_len == false) {
            Toast.makeText(context.getApplicationContext(), R.string.psw_len_incorrect, Toast.LENGTH_SHORT).show();
            psw_len = true;
        } else {
            count++;
        }
        if (tipoUtente.isEmpty()) {
            Toast.makeText(context.getApplicationContext(), R.string.type_user, Toast.LENGTH_SHORT).show();
        } else {
            count++;
        }

        createUtente(username, password, tipoUtente, context, listener, errorListener);


        // se l'utente ha inserito correttamente i dati nei campi allora
        // si procede con la sua registrazione.
        if (count == 4) {

            //createAccount(email, password, context);


        }

    }


    /**
     * Il metodo controlla se campo testo e' vuoto o meno.
     *
     * @param edit
     * @return check booleano
     */
    public static int checkInsertValues(EditText edit) {
        int check;
        if (edit.getText().toString().isEmpty() || edit == null) {
            check = 0;
        } else {
            check = 1;
        }

        return check;
    }

    /**
     * Il metodo controlla che l'inserimento di caratteri sia valido.
     *
     * @param txt_nome
     * @return boolean
     */
    public static boolean checkInsertValuesText(String txt_nome) {
        Pattern pattern;
        Matcher matcher;
        String nome_text_pattern = "^[A-Za-z ']*$";
        pattern = Pattern.compile(nome_text_pattern, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(txt_nome);
        matcher.matches();
        return matcher.matches();

    }

    /**
     * Il metodo controlla la validita' del campo email.
     *
     * @param email
     * @return boolean
     */
    public static boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Il metodo controlla che la password sia di lunghezza non inferiore
     * ai 6 caratteri (specifiche Firebase).
     *
     * @param checkpsw
     * @return boolean
     */
    public static boolean pswlen(String checkpsw) {

        boolean txtpsw = false;

        if (checkpsw.length() > 5) {
            txtpsw = true;
        }

        return txtpsw;
    }


    public static void createUtente(final String username, final String password, final String tipoUtente, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        HTTPRequestRegistration.RegisterUtente(username, password, tipoUtente, context, listener, errorListener);

    }

    public static void createAmministratore(final String username, final String amministratore, final String email, final String sede, final String studio, final String telefono, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        HTTPRequestRegistration.RegisterAmministratore(username, amministratore, email, sede, studio, telefono, context, listener, errorListener);

    }

    public static void createFornitore(final String username, final String azienda, final String indirizzo, final String telefono, final String email, final String partitaIva, final String responsabile, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        HTTPRequestRegistration.RegisterFornitore(username, azienda, indirizzo, telefono, email, partitaIva, responsabile, context, listener, errorListener);

    }

    /**
     * Il metodo inizializza le SharedPreferences dell'applicazione.
     */
    public static void setPrefs(Context context, String username, String tipoUtente) {

        final SharedPreferences sharedPrefs = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(LOGGED_USER, username);  // usato come identificativo utente.
        editor.putString(TIPO_UTENTE, tipoUtente); // usato come identificativo del tipo di utente.
        editor.apply();
    }


}
