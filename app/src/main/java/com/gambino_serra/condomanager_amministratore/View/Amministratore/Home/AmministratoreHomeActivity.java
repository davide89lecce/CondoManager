package com.gambino_serra.condomanager_amministratore.View.Amministratore.Home;

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

import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.gambino_serra.condomanager_amministratore.View.Amministratore.NuovoTicket.TicketCondominio;
import com.gambino_serra.condomanager_amministratore.View.Utente.LoginActivity;


public class AmministratoreHomeActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";
    private static final String TIPO_UTENTE = "tipoUtente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amministratore_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Segnalazioni"));
        tabLayout.addTab(tabLayout.newTab().setText("Ticket in Corso"));
        tabLayout.addTab(tabLayout.newTab().setText("Ticket completati"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterAmministratore adapter = new PagerAdapterAmministratore
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
                Intent nuovoTicket = new Intent(getApplicationContext(), TicketCondominio.class);
                startActivity(nuovoTicket);
            }
        });
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