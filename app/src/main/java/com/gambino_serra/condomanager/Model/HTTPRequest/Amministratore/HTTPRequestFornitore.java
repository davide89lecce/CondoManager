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

public class HTTPRequestFornitore {

    private static final String KEY_DBSERVERNAME = "localhost";
    private static final String KEY_DBUSERNAME = "damicogianluca";
    private static final String KEY_DBPASSWORD = "";
    private static final String KEY_DBNAME = "my_damicogianluca";
    private static final String KEY_DB_REQUEST = "http://damicogianluca.altervista.org/get_fornitore.php";
    private static final String KEY_DB_REQUEST2 = "http://damicogianluca.altervista.org/get_fornitore_from_amministratore.php";
    private static final String KEY_DB_REQUEST3 = "http://damicogianluca.altervista.org/get_fornitore_from_impianto.php";
    private static final String KEY_DB_REQUEST4 = "http://damicogianluca.altervista.org/get_fornitore_from_categoria.php";

    private HTTPRequestFornitore(){}

    public static void getFornitore(final String username, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getFornitoreFromAmministratore(final String username, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getFornitoreFromImpianto(final String idImpianto, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST3;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idImpianto", idImpianto);
                params.put("dbservername", KEY_DBSERVERNAME);
                params.put("dbusername", KEY_DBUSERNAME);
                params.put("dbpassword", KEY_DBPASSWORD);
                params.put("dbname", KEY_DBNAME);
                return params;
            }
        };
        SingletonVolley.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getFornitoreFromCategoria(final String categoria, final Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String url = KEY_DB_REQUEST4;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("categoria", categoria);
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
