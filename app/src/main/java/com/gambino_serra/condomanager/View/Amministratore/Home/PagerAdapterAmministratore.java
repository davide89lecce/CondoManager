package com.gambino_serra.condomanager.View.Amministratore.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterAmministratore extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterAmministratore(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Segnalazioni tab1 = new Segnalazioni();
                return tab1;
            case 1:
                TicketInCorso tab2 = new TicketInCorso();
                return tab2;
            case 2:
                TicketCompletati tab3 = new TicketCompletati();
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