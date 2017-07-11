package com.gambino_serra.condomanager.View.Fornitore.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterFornitore extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterFornitore(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RichiesteIntervento tab1 = new RichiesteIntervento();
                return tab1;
            case 1:
                InterventiInProgramma tab2 = new InterventiInProgramma();
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