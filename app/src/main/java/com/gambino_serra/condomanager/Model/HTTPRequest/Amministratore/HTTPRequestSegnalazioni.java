package com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.gambino_serra.condomanager.Model.HTTPRequest.SingletonVolley;

import java.util.HashMap;
import java.util.Map;


public class HTTPRequestSegnalazioni {

    private static final String KEY_DBSERVERNAME = "localhost";
    private static final String KEY_DBUSERNAME = "damicogianluca";
    private static final String KEY_DBPASSWORD = "";
    private static final String KEY_DBNAME = "my_damicogianluca";
    private static final String KEY_DB_REQUEST = "http://damicogianluca.altervista.org/get_segnalazioni_from_amministratore.php";
    private static final String KEY_DB_REQUEST2 = "http://damicogianluca.altervista.org/update_segnalazione.php";
    private static final String KEY_DB_REQUEST3 = "http://damicogianluca.altervista.org/insert_segnalazione.php";
    private static final String KEY_DB_REQUEST4 = "http://damicogianluca.altervista.org/get_segnalazione.php";

    private HTTPRequestSegnalazioni(){}

    public static void Segnalazioni(final String username, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.replace("'", ""));
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void updateSegnalazione(final String idSegnalazione, final String segnalazione, final String condomino, final String condominio, final String impianto, final String fornitore, final String stato, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSegnalazione", idSegnalazione);
                params.put("segnalazione", segnalazione);
                params.put("condomino", condomino);
                params.put("condominio", condominio);
                params.put("impianto", impianto);
                params.put("fornitore", fornitore);
                params.put("stato", stato);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void insertSegnalazione(final String segnalazione, final String condomino, final String condominio, final String impianto, final String fornitore, final String stato, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST3;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("segnalazione", segnalazione);
                params.put("condomino", condomino);
                params.put("condominio", condominio);
                params.put("impianto", impianto);
                params.put("fornitore", fornitore);
                params.put("stato", stato);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getSegnalazione(final String idSegnalazione, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST4;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSegnalazione", idSegnalazione);
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
