package com.gambino_serra.condomanager_amministratore.View.Home.BachecaStabili;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.Stabile;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterBachecaStabili extends RecyclerView.Adapter<AdapterBachecaStabili.MyViewHolder> {

    private ArrayList<Stabile> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Tstabile;
        TextView Tindirizzo;
        TextView TIdStabile;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.Tstabile = (TextView) itemView.findViewById(R.id.D_Stabile);
            this.Tindirizzo = (TextView) itemView.findViewById(R.id.D_Indirizzo);
            this.TIdStabile = (TextView) itemView.findViewById(R.id.Hidden_ID);
            }
    }

    public AdapterBachecaStabili(ArrayList<Stabile> dataset) {
        this.dataset = dataset;
        }

    @Override
    public AdapterBachecaStabili.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stabile, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(BachecaStabili.myOnClickListener);

        AdapterBachecaStabili.MyViewHolder myViewHolder = new AdapterBachecaStabili.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterBachecaStabili.MyViewHolder holder, final int listPosition) {

        TextView Tstabile = holder.Tstabile;
        TextView Tindirizzo = holder.Tindirizzo;
        TextView TIdStabile = holder.TIdStabile;

        Tstabile.setText(dataset.get(listPosition).getNomeStabile());
        Tindirizzo.setText(dataset.get(listPosition).getIndirizzo());
        TIdStabile.setText(dataset.get(listPosition).getIdStabile());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}