package com.gambino_serra.condomanager_amministratore.View.Condomino.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterCondomino extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterCondomino(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BachecaSegnalazioni tab1 = new BachecaSegnalazioni();
                return tab1;
            case 1:
                SegnalazioniRisolte tab2 = new SegnalazioniRisolte();
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