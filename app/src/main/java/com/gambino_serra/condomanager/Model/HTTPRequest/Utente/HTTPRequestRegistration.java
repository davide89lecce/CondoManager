package com.gambino_serra.condomanager.Model.HTTPRequest.Utente;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.gambino_serra.condomanager.Model.HTTPRequest.SingletonVolley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by condomanager on 12/02/17.
 */

public class HTTPRequestRegistration {

    private static final String KEY_DBSERVERNAME = "localhost";
    private static final String KEY_DBUSERNAME = "damicogianluca";
    private static final String KEY_DBPASSWORD = "";
    private static final String KEY_DBNAME = "my_damicogianluca";
    private static final String KEY_DB_REQUEST = "http://damicogianluca.altervista.org/register.php";
    private static final String KEY_DB_REQUEST2 = "http://damicogianluca.altervista.org/register_amministratore.php";
    private static final String KEY_DB_REQUEST3 = "http://damicogianluca.altervista.org/register_fornitore.php";

    private HTTPRequestRegistration(){}

    public static void RegisterUtente(final String username, final String password, final String tipoUtente, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.toString().replace("'", ""));
                params.put("password", password.toString().replace("'", ""));
                params.put("tipologia", tipoUtente);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void RegisterAmministratore(final String username, final String amministratore, final String email, final String sede, final String studio, final String telefono, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.toString().replace("'", ""));
                params.put("email", email.toString());
                params.put("amministratore", amministratore.toString().replace("'", ""));
                params.put("sede", sede.toString().replace("'", ""));
                params.put("studio", studio.toString().replace("'", ""));
                params.put("telefono", telefono.toString().replace("'", ""));
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void RegisterFornitore(final String username, final String azienda, final String indirizzo, final String telefono, final String email, final String partitaIva, final String responsabile, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST3;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.toString().replace("'", ""));
                params.put("email", email.toString());
                params.put("azienda", azienda.toString().replace("'", ""));
                params.put("indirizzo", indirizzo.toString().replace("'", ""));
                params.put("telefono", telefono.toString().replace("'", ""));
                params.put("partitaIva", partitaIva.toString().replace("'", ""));
                params.put("responsabile", responsabile.toString().replace("'", ""));
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

}
