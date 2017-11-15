package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.gambino_serra.condomanager_amministratore.tesi.R;

public class Intervento extends AppCompatActivity {

    private static final String MY_PREFERENCES = "preferences";

    String idIntervento = "";
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intervento);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Dettaglio intervento"));
        tabLayout.addTab(tabLayout.newTab().setText("Rapporti intervento"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        if (getIntent().getExtras() != null)
            {
            bundle = getIntent().getExtras();
            idIntervento = bundle.get("idIntervento").toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("idIntervento", idIntervento);
            editor.apply();
            }
        else
            {
            //se non trova idIntervento nel bundle lo recupera dalle shared
            idIntervento = sharedPrefs.getString("idIntervento", "").toString();
            bundle = new Bundle();
            bundle.putString("idIntervento", idIntervento);
            }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterIntervento adapter = new PagerAdapterIntervento(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

        });

        // se si arriva dalla schermata delle notifiche, allora si visualizzeranno
        // direttamente i rapporti di intervento
        viewPager.setCurrentItem(1);
    }
}