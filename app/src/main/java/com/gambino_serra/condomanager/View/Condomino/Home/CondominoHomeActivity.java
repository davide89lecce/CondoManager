package com.gambino_serra.condomanager.View.Condomino.Home;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gambino_serra.condomanager.Model.HTTPRequest.Amministratore.HTTPRequestSegnalazioni;
import com.gambino_serra.condomanager.Model.JsonSerializable.JsonCondominio;
import com.gambino_serra.condomanager.tesi.R;
import com.gambino_serra.condomanager.View.Condomino.Dialog.DialogSegnalazioneInviata;
import com.gambino_serra.condomanager.View.Condomino.NuovaSegnalazione.DialogNuovaSegnalazione;
import com.gambino_serra.condomanager.View.Utente.LoginActivity;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

/**
 * Created by condomanager on 11/02/17.
 */

public class CondominoHomeActivity extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";
    private static final String TIPO_UTENTE = "tipoUtente";

    FloatingActionButton fab;
    String condominio = "";
    String username = "";
    String segnalazione = "";
    String stato = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condomino_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Bacheca\nsegnalazioni"));
        tabLayout.addTab(tabLayout.newTab().setText("Segnalazioni\nrisolte"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterCondomino adapter = new PagerAdapterCondomino
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                DialogFragment newFragment = new DialogNuovaSegnalazione();
                newFragment.show(getFragmentManager(), "NuovaSegnalazione");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

            }
        });

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

        if(!response.equals("null")) {

            if (response.contains("[{\"idCondominio\"")) {

                final ArrayList<JsonCondominio> productList = new JsonConverter<JsonCondominio>().toArrayList(response, JsonCondominio.class);

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

                username = sharedPrefs.getString(LOGGED_USER, "").toString();
                condominio = productList.get(0).idCondominio;
                stato = "A";
                segnalazione = sharedPrefs.getString("descrizioneSegnalazione", "").toString();

                HTTPRequestSegnalazioni.insertSegnalazione(segnalazione, username, condominio, "1" , "", stato, getApplicationContext(),this,this);

            }  else if (response.equals("ok")){

                DialogFragment newFragment = new DialogSegnalazioneInviata();
                newFragment.show(getFragmentManager(), "SegnalazioneInviata");
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }

        }
    }

    /**
     * Il metodo crea il menù sull'ActionBar.
     *
     * @param menu
     * @return booleano
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.botton_menu, menu);
        return true;
    }

    /**
     * Il metodo gestisce il menù sull'ActionBar
     *
     * @param item
     * @return booleano
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean check = false;

        final SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        switch (item.getItemId()) {

            case R.id.botton_logout:

                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(LOGGED_USER, "");
                editor.putString(TIPO_UTENTE, "");
                editor.apply();

                Intent logout;
                logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
                check = true;
                break;
            default:
                check = super.onOptionsItemSelected(item);
        }

        return check;
    }

}
