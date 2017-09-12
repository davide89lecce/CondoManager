package com.gambino_serra.condomanager_amministratore.View.Stabile.Interventi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterBachecaInterventi extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterBachecaInterventi(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InterventiInAttesa tab1 = new InterventiInAttesa();
                return tab1;
            case 1:
                InterventiInCorso tab2 = new InterventiInCorso();
                return tab2;
            case 2:
                InterventiCompletati tab3 = new InterventiCompletati();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}