package com.gambino_serra.condomanager.View.Amministratore.TicketRifiutato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.gambino_serra.condomanager.tesi.R;

import java.util.ArrayList;


public class TicketCategoria extends AppCompatActivity{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private ArrayList<DataTicketCategoria> data;
    String username;
    DataTicketCategoria categoria;
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
        setContentView(R.layout.activity_segnalazione_categoria);

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

            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idSegnalazione", idSegnalazione);
            editor.putString("segnalazione", segnalazione);
            editor.putString("condomino", usernameCondomino);
            editor.putString("idCondominio", idCondominio);
            editor.putString("condominio", condominio);
            editor.putString("condominoNome",condomino);
            editor.apply();

        } else {

            idSegnalazione = sharedPrefs.getString("idSegnalazione", "").toString();


            bundle = new Bundle();
            bundle.putString("idSegnalazione", idSegnalazione);
            bundle.putString("segnalazione", segnalazione);
            bundle.putString("condomino", usernameCondomino);
            bundle.putString("idCondominio", idCondominio);
            bundle.putString("condominio", condominio);
            bundle.putString("condominoNome",condomino);

        }

        removedItems = new ArrayList<Integer>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_categoria);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataTicketCategoria>();
        categoria = new DataTicketCategoria("Elettricista","lavori di tipo elettrici ");
        data.add(categoria);
        categoria = new DataTicketCategoria("Fabbro","lavori fabbrili (porte, ringhiere, cancelli)");
        data.add(categoria);
        categoria = new DataTicketCategoria("Idraulico","lavori idraulici (tubature, autoclave, riscaldamento)");
        data.add(categoria);
        categoria = new DataTicketCategoria("Edilizia","lavori di muratura, pavimentazioni/piastrelleria, tetto/solaio, tinteggiatura");
        data.add(categoria);
        categoria = new DataTicketCategoria("Giardiniere","lavori di giardinaggio");
        data.add(categoria);
        categoria = new DataTicketCategoria("Ascensorista","lavori riguardante l'ascensore");
        data.add(categoria);
        categoria = new DataTicketCategoria("Servizi di pulizia","lavori di pulizia interna allo stabile e del cortile condominiale");
        data.add(categoria);
        categoria = new DataTicketCategoria("Falegname","lavori di falegnameria");
        data.add(categoria);
        categoria = new DataTicketCategoria("Vetraio","lavori di vetreria");
        data.add(categoria);
        categoria = new DataTicketCategoria("Antennista","lavori riguardanti l'antenna TV");
        data.add(categoria);
        categoria = new DataTicketCategoria("Automazione","lavori riguardanti automazione per serramenti, controllo accessi, domotica");
        data.add(categoria);
        categoria = new DataTicketCategoria("Sicurezza","lavori riguardanti impianti antintrusione, videosorveglianza, rilevazione incendi, estintori");
        data.add(categoria);

        adapter = new AdapterListTicketCategoria(data,this,this);
        recyclerView.setAdapter(adapter);


        btnAvanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bundle.putString("categoria", sharedPrefs.getString("categoria", "").toString());
                Intent intent = new Intent(getApplicationContext(), TicketFornitore.class);
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



}
