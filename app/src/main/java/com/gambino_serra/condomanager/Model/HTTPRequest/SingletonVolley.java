package com.gambino_serra.condomanager.Model.HTTPRequest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * La classe modella l'entita tramite la quale Volley gestisce la sincronizzaione dei thread delle richieste
 * nella cache gestita come una coda con priorità.
 */
public class SingletonVolley {

    private static SingletonVolley mInstance;

    private RequestQueue mRequestQueue;

    private static Context mCtx;

    private SingletonVolley(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingletonVolley getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonVolley(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}