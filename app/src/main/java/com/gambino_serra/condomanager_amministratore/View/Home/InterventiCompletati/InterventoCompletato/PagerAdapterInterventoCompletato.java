package com.gambino_serra.condomanager_amministratore.View.Home.InterventiCompletati.InterventoCompletato;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gambino_serra.condomanager_amministratore.View.Home.InterventiCompletati.InterventoCompletato.RapportiIntervento.RapportiInterventoCompletato;

public class PagerAdapterInterventoCompletato extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterInterventoCompletato(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DettaglioInterventoCompletato tab1 = new DettaglioInterventoCompletato();
                return tab1;
            case 1:
                RapportiInterventoCompletato tab2 = new RapportiInterventoCompletato();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}