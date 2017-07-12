package com.gambino_serra.condomanager_amministratore.View.Amministratore.NuovoTicket;

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
import com.gambino_serra.condomanager_amministratore.Model.Entity.Condomino;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Amministratore.HTTPRequestCondomino;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonCondomino;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;


public class TicketCondomino extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private ArrayList<DataCondomino> data;
    String username;
    FloatingActionButton fab;
    public int row;
    LinearLayout btnAvanti;
    LinearLayout btnIndietro;
    Bundle bundle;
    String idCondominio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_condomino);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        btnAvanti = (LinearLayout) findViewById(R.id.btnAvanti);
        btnIndietro = (LinearLayout) findViewById(R.id.btnIndietro);

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            idCondominio = bundle.get("idCondominio").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idCondominio", idCondominio);
            editor.apply();

        } else {

            idCondominio = sharedPrefs.getString("idCondominio", "").toString();

            bundle = new Bundle();
            bundle.putString("idCondominio", idCondominio);
        }

        removedItems = new ArrayList<Integer>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_condomino);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataCondomino>();

        HTTPRequestCondomino.getCondominoFromCondominio(idCondominio,this,this,this);

        btnAvanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bundle.putString("condomino", sharedPrefs.getString("condomino", "").toString());
                Intent intent = new Intent(getApplicationContext(), TicketCategoria.class);
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

            final ArrayList<JsonCondomino> productList = new JsonConverter<JsonCondomino>().toArrayList(response, JsonCondomino.class);

            for (int i = 0; i < productList.size(); i++) {

                Condomino c = new Condomino(productList.get(i).username, productList.get(i).nome, productList.get(i).cognome,
                        productList.get(i).condominio);

                DataCondomino condomino = new DataCondomino(c.getUsername(), c.getNome() + " " + c.getCognome());
                data.add(condomino);

            }

            adapter = new AdapterListCondomino(data,this,this);
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
