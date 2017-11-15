package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggionamentiTicket.AggiornamentiTicket;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterNotificheMessaggi extends RecyclerView.Adapter<AdapterNotificheMessaggi.MyViewHolder> {

    private ArrayList<String> dataset;

    public AdapterNotificheMessaggi(ArrayList<String> dataset) {
        this.dataset = dataset;
    }

    @Override
    public AdapterNotificheMessaggi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_messaggi, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AggiornamentiTicket.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        AdapterNotificheMessaggi.MyViewHolder myViewHolder = new AdapterNotificheMessaggi.MyViewHolder(view);
        return myViewHolder;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TnomeStabile;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.TnomeStabile = (TextView) itemView.findViewById(R.id.D_NomeStabile);
        }
    }



    @Override
    public void onBindViewHolder(final AdapterNotificheMessaggi.MyViewHolder holder, final int listPosition) {

        TextView TnomeStabile = holder.TnomeStabile;

        TnomeStabile.setText(" - " + dataset.get(listPosition).toString());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
