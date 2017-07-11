package com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.gambino_serra.condomanager.Model.HTTPRequest.SingletonVolley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by condomanager on 14/02/17.
 */

public class HTTPRequestCondominio {

    private static final String KEY_DBSERVERNAME = "localhost";
    private static final String KEY_DBUSERNAME = "damicogianluca";
    private static final String KEY_DBPASSWORD = "";
    private static final String KEY_DBNAME = "my_damicogianluca";
    private static final String KEY_DB_REQUEST = "http://damicogianluca.altervista.org/get_condominio.php";
    private static final String KEY_DB_REQUEST2 = "http://damicogianluca.altervista.org/condomini_from_amministratore.php";

    private HTTPRequestCondominio(){}

    public static void getCondominio(final String idCondominio, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCondominio", idCondominio);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void condominiFromAmministratore(final String amministratore, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("amministratore", amministratore);
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

