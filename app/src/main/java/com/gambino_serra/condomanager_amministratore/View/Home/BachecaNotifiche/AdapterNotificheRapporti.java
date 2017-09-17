package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterNotificheRapporti extends RecyclerView.Adapter<AdapterNotificheRapporti.MyViewHolder> {

    private ArrayList<String> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TnomeAzienda;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.TnomeAzienda = (TextView) itemView.findViewById(R.id.D_NomeAzienda);
            }
    }

    public AdapterNotificheRapporti(ArrayList<String> dataset) {
        this.dataset = dataset;
        }

    @Override
    public AdapterNotificheRapporti.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rapporti, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        //view.setOnClickListener(BachecaStabili.myOnClickListener);

        AdapterNotificheRapporti.MyViewHolder myViewHolder = new AdapterNotificheRapporti.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterNotificheRapporti.MyViewHolder holder, final int listPosition) {

        TextView TnomeAzienda = holder.TnomeAzienda;

        TnomeAzienda.setText(" - " + dataset.get(listPosition).toString());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}