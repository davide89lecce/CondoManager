package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiSondaggi.AggiornamentiSondaggi;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterNotificheSondaggi extends RecyclerView.Adapter<AdapterNotificheSondaggi.MyViewHolder> {

    private ArrayList<String> dataset;

    public AdapterNotificheSondaggi(ArrayList<String> dataset) {
        this.dataset = dataset;
    }

    @Override
    public AdapterNotificheSondaggi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sondaggi, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AggiornamentiSondaggi.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        AdapterNotificheSondaggi.MyViewHolder myViewHolder = new AdapterNotificheSondaggi.MyViewHolder(view);
        return myViewHolder;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ToggettoSondaggio;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.ToggettoSondaggio = (TextView) itemView.findViewById(R.id.D_OggettoSondaggio);
        }
    }



    @Override
    public void onBindViewHolder(final AdapterNotificheSondaggi.MyViewHolder holder, final int listPosition) {

        TextView ToggettoSondaggio = holder.ToggettoSondaggio;

        ToggettoSondaggio.setText(" - " + dataset.get(listPosition).toString());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
