package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.DettaglioIntervento.DettaglioIntervento;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.RapportiIntervento.RapportiIntervento;

public class PagerAdapterIntervento extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterIntervento(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DettaglioIntervento tab1 = new DettaglioIntervento();
                return tab1;
            case 1:
                RapportiIntervento tab2 = new RapportiIntervento();
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