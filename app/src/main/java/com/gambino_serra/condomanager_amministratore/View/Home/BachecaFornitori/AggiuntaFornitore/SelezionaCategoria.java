package com.gambino_serra.condomanager_amministratore.View.Home.BachecaFornitori.AggiuntaFornitore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;


public class SelezionaCategoria extends AppCompatActivity{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private ArrayList<DataCategoria> data;
    String username;
    DataCategoria categoria;
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
        setContentView(R.layout.seleziona_categoria_fornitore);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        btnAvanti = (LinearLayout) findViewById(R.id.btnAvanti);
        btnIndietro = (LinearLayout) findViewById(R.id.btnIndietro);


        removedItems = new ArrayList<Integer>();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_categoria);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataCategoria>();
        categoria = new DataCategoria("elettricista","lavori di tipo elettrici ");
        data.add(categoria);
        categoria = new DataCategoria("fabbro","lavori fabbrili (porte, ringhiere, cancelli)");
        data.add(categoria);
        categoria = new DataCategoria("idraulico","lavori idraulici (tubature, autoclave, riscaldamento)");
        data.add(categoria);
        categoria = new DataCategoria("edilizia","lavori di muratura, pavimentazioni/piastrelleria, tetto/solaio, tinteggiatura");
        data.add(categoria);
        categoria = new DataCategoria("giardiniere","lavori di giardinaggio");
        data.add(categoria);
        categoria = new DataCategoria("ascensorista","lavori riguardante l'ascensore");
        data.add(categoria);
        categoria = new DataCategoria("servizi di pulizia","lavori di pulizia interna allo stabile e del cortile condominiale");
        data.add(categoria);
        categoria = new DataCategoria("falegname","lavori di falegnameria");
        data.add(categoria);
        categoria = new DataCategoria("vetraio","lavori di vetreria");
        data.add(categoria);
        categoria = new DataCategoria("antennista","lavori riguardanti l'antenna TV");
        data.add(categoria);
        categoria = new DataCategoria("automazione","lavori riguardanti automazione per serramenti, controllo accessi, domotica");
        data.add(categoria);
        categoria = new DataCategoria("sicurezza","lavori riguardanti impianti antintrusione, videosorveglianza, rilevazione incendi, estintori");
        data.add(categoria);

        adapter = new AdapterSelezionaCategoria(data,this,this);
        recyclerView.setAdapter(adapter);


        btnAvanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                bundle = new Bundle();
                Log.d("ciao2",sharedPrefs.getString("categoria", ""));
                bundle.putString("categoria", sharedPrefs.getString("categoria", ""));
                Intent intent = new Intent(getApplicationContext(), MappaFornitori.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
