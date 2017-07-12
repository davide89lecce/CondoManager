package com.gambino_serra.condomanager_amministratore.View.Amministratore.TicketRifiutato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.Model.HTTPRequest.Amministratore.HTTPRequestFornitore;
import com.gambino_serra.condomanager_amministratore.Model.JsonSerializable.JsonFornitore;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class TicketFornitore extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private ArrayList<DataTicketFornitore> data;
    String username;
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
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnalazione_fornitore);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        btnAvanti = (LinearLayout) findViewById(R.id.btnAvanti);
        btnIndietro = (LinearLayout) findViewById(R.id.btnIndietro);

        if (getIntent().getExtras() != null) {

            bundle = getIntent().getExtras();
            idSegnalazione = bundle.get("idSegnalazione").toString();
            segnalazione = bundle.get("segnalazione").toString();
            usernameCondomino = bundle.get("condomino").toString();
            idCondominio = bundle.get("idCondominio").toString();
            condominio = bundle.get("condominio").toString();
            condomino = bundle.get("condominoNome").toString();
            categoria = bundle.get("categoria").toString();

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idSegnalazione", idSegnalazione);
            editor.putString("segnalazione", segnalazione);
            editor.putString("condomino", usernameCondomino);
            editor.putString("idCondominio", idCondominio);
            editor.putString("condominio", condominio);
            editor.putString("condominoNome",condomino);
            editor.putString("categoria",categoria);
            editor.apply();

        } else {

            idSegnalazione = sharedPrefs.getString("idSegnalazione", "").toString();
            segnalazione = sharedPrefs.getString("segnalazione", "").toString();
            usernameCondomino = sharedPrefs.getString("condomino", "").toString();
            idCondominio = sharedPrefs.getString("idCondominio", "").toString();
            condominio = sharedPrefs.getString("condominio", "").toString();
            condomino = sharedPrefs.getString("condominoNome", "").toString();
            categoria = sharedPrefs.getString("categoria", "").toString();

            bundle = new Bundle();
            bundle.putString("idSegnalazione", idSegnalazione);
            bundle.putString("segnalazione", segnalazione);
            bundle.putString("condomino", usernameCondomino);
            bundle.putString("idCondominio", idCondominio);
            bundle.putString("condominio", condominio);
            bundle.putString("condominoNome",condomino);
            bundle.putString("categoria",categoria);

        }

        removedItems = new ArrayList<Integer>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_fornitore);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataTicketFornitore>();

        //Per lettura corretta nel database
        if(categoria.equals("Servizi di pulizia")){
            categoria = "Servizi_di_pulizia";
        }
        HTTPRequestFornitore.getFornitoreFromCategoria(categoria, this, this, this);

        btnAvanti.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {

                bundle.putString("fornitore", sharedPrefs.getString("fornitore", "").toString());
                Intent intent = new Intent(getApplicationContext(), TicketDescrizione.class);
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

            final ArrayList<JsonFornitore> productList = new JsonConverter<JsonFornitore>().toArrayList(response, JsonFornitore.class);

            for (int i = 0; i < productList.size(); i++) {

                    Fornitore f = new Fornitore(productList.get(i).username, productList.get(i).azienda, productList.get(i).indirizzo,
                            productList.get(i).telefono, productList.get(i).email, productList.get(i).partitaIva, productList.get(i).responsabile);

                    DataTicketFornitore fornitore = new DataTicketFornitore(f.getAzienda(),f.getIndirizzo(), f.getUsername());
                    data.add(fornitore);

            }

            adapter = new AdapterListTicketFornitore(data,this,this);
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
