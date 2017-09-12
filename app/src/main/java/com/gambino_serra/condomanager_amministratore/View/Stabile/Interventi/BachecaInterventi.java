package com.gambino_serra.condomanager_amministratore.View.Stabile.Interventi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gambino_serra.condomanager_amministratore.tesi.R;


public class BachecaInterventi extends Fragment {

    public static BachecaInterventi newInstance() {
        BachecaInterventi fragment = new BachecaInterventi();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_bacheca_interventi, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

        @Override
        public void onStart() {
            super.onStart();

            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
            tabLayout.removeAllTabs();
            tabLayout.addTab(tabLayout.newTab().setText("In attesa"));
            tabLayout.addTab(tabLayout.newTab().setText("In corso"));
            tabLayout.addTab(tabLayout.newTab().setText("Completati"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
            final PagerAdapterBachecaInterventi adapter = new PagerAdapterBachecaInterventi
                    (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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

    }

}