package com.gambino_serra.condomanager_amministratore.View.Home.InterventiInCorso.BachecaInterventiInCorso;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

import static com.gambino_serra.condomanager_amministratore.tesi.R.id.D_Priorità;


public class AdapterInterventiInCorso extends RecyclerView.Adapter<AdapterInterventiInCorso.MyViewHolder> {

    private ArrayList<CardTicketIntervento> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TdataTicket;
        TextView Tindirizzo;
        TextView Toggetto;
        TextView Tstabile;
        TextView Tdata;
        TextView IdTicket;
        ImageView mLogoPriorità;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.Tstabile = (TextView) itemView.findViewById(R.id.D_Condominio);
            this.Tindirizzo = (TextView) itemView.findViewById(R.id.D_Indirizzo);
            this.Toggetto = (TextView) itemView.findViewById(R.id.D_Oggetto);
            this.Tdata = (TextView) itemView.findViewById(R.id.D_Data);
            this.mLogoPriorità = (ImageView) itemView.findViewById(D_Priorità);
            //Campo nascosto per recuperare il riferimento
            this.IdTicket = (TextView) itemView.findViewById(R.id.D_IDIntervento);
        }
    }

    public AdapterInterventiInCorso(ArrayList<CardTicketIntervento> dataset) {
        this.dataset = dataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_intervento_in_corso, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(BachecaInterventiInCorso.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView Tstabile = holder.Tstabile;
        TextView Tindirizzo = holder.Tindirizzo;
        TextView Toggetto = holder.Toggetto;
        TextView TdataTicket = holder.Tdata;
        ImageView mLogoPriorità = holder.mLogoPriorità;
        TextView IdTicket = holder.IdTicket;

        try {
            Tstabile.setText(dataset.get(listPosition).getNomeStabile());
            Tindirizzo.setText(dataset.get(listPosition).getIndirizzoStabile());
            Toggetto.setText(dataset.get(listPosition).getOggetto());
            TdataTicket.setText(dataset.get(listPosition).getDataTicket());
            IdTicket.setText(dataset.get(listPosition).getIdTicketIntervento());
        }catch (NullPointerException e){
            Log.d("HEY", "Mi sono bloccato");
        }

        // Stringa usata per tenere traccia della priorità e lavorare sull'immagine rappresentata
        String priorità = dataset.get(listPosition).getPriorità();

        switch(priorità) {
            case "3" :
                {
                mLogoPriorità.setBackgroundColor(Color.RED);
                break;
                }

            case "2":
                {
                mLogoPriorità.setBackgroundColor(Color.YELLOW);
                break;
                }

            case "1":
                {
                mLogoPriorità.setBackgroundColor(Color.GREEN);
                break;
                }

            default:
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}