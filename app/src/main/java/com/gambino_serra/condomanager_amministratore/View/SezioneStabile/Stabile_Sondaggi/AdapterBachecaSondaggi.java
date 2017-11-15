package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Sondaggi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.Sondaggio;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterBachecaSondaggi extends RecyclerView.Adapter<AdapterBachecaSondaggi.MyViewHolder> {

    private ArrayList<Sondaggio> dataset;

    int row;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mOggetto;
        TextView mTipologia;
        TextView mData;
        TextView HiddenID;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mOggetto = (TextView) itemView.findViewById(R.id.D_TestoSondaggio);
            this.mTipologia = (TextView) itemView.findViewById(R.id.D_TipologiaSondaggio);
            this.mData = (TextView) itemView.findViewById(R.id.DataSondaggio);
            //Campo nascosto per recuperare il riferimento
            this.HiddenID = (TextView) itemView.findViewById(R.id.textViewIdSondaggio);
        }
    }

    public AdapterBachecaSondaggi(ArrayList<Sondaggio> dataset) {
        this.dataset = dataset;
    }

    public AdapterBachecaSondaggi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sezionestabile_card_sondaggio, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(BachecaSondaggi.myOnClickListener);

        AdapterBachecaSondaggi.MyViewHolder myViewHolder = new AdapterBachecaSondaggi.MyViewHolder(view);
        return myViewHolder;
    }

    public void onBindViewHolder(final AdapterBachecaSondaggi.MyViewHolder holder, final int listPosition) {

        TextView mOggetto = holder.mOggetto;
        TextView mTipologia = holder.mTipologia;
        TextView mData = holder.mData;
        TextView HiddeID = holder.HiddenID;

        mOggetto.setText(dataset.get(listPosition).getOggetto());
        mTipologia.setText(dataset.get(listPosition).getTipologia());
        mData.setText(dataset.get(listPosition).getData());
        HiddeID.setText(dataset.get(listPosition).getIdSondaggio());
    }

    public int getItemCount() {
        return dataset.size();
    }

}
