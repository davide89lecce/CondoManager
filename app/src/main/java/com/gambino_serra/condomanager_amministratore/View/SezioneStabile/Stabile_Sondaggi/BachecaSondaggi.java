package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Sondaggi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gambino_serra.condomanager_amministratore.View.Home.BachecaStabili.BachecaStabili;
import com.gambino_serra.condomanager_amministratore.tesi.R;


public class BachecaSondaggi extends Fragment{


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static View.OnClickListener myOnClickListener;
    Context context;

    public static BachecaSondaggi newInstance() {
        BachecaSondaggi fragment = new BachecaSondaggi();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sezionestabile_tab_sondaggi, container, false);
    }

}
