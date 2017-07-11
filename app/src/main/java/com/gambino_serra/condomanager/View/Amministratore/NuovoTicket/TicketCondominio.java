package com.gambino_serra.condomanager.View.Amministratore.NuovoTicket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager.Model.Entity.Condominio;
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestCondominio;
import com.gambino_serra.condomanager.Model.JsonSerializable.JsonCondominio;
import com.gambino_serra.condomanager.tesi.R;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;


public class TicketCondominio extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private ArrayList<DataCondominio> data;
    String username;
    FloatingActionButton fab;
    public int row;
    LinearLayout btnAvanti;
    LinearLayout btnIndietro;
    Bundle bundle;
    String idSegnalazione;
    String segnalazione;
    String usernameCondomino;
    String idCondominio;
    String condominio;
    String condomino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_condominio);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        btnAvanti = (LinearLayout) findViewById(R.id.btnAvanti);
        btnIndietro = (LinearLayout) findViewById(R.id.btnIndietro);

        bundle = new Bundle();

        removedItems = new ArrayList<Integer>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_condominio);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataCondominio>();

        HTTPRequestCondominio.condominiFromAmministratore(username,this,this,this);

        btnAvanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bundle.putString("idCondominio", sharedPrefs.getString("idCondominio", "").toString());
                Intent intent = new Intent(getApplicationContext(), TicketCondomino.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    /**
     * Il metodo e' invocato alla risposta (dati ricevuti da database altervista) della richiesta delle segnalazioni.
     * @param response
     */
    @Override
    public void onResponse(String response) {

        if (!response.equals("null")) {

            final ArrayList<JsonCondominio> productList = new JsonConverter<JsonCondominio>().toArrayList(response, JsonCondominio.class);

            for (int i = 0; i < productList.size(); i++) {

                Condominio c = new Condominio(productList.get(i).idCondominio, productList.get(i).condominio, productList.get(i).indirizzo,
                        productList.get(i).ammministratore);

                DataCondominio condominio = new DataCondominio(c.getIdCondominio(), c.getCondominio(),c.getIndirizzo(), c.getAmministratore());
                data.add(condominio);

            }

            adapter = new AdapterListCondominio(data,this,this);
            recyclerView.setAdapter(adapter);

        }
    }

    /**
     * Il metodo viene invocato in caso di problemi nella ricezione della risposta.
     * @param error
     */
    @Override
    public void onErrorResponse(VolleyError error) {}

}
