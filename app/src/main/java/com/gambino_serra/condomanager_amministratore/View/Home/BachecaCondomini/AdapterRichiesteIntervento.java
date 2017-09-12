package com.gambino_serra.condomanager_amministratore.View.Home.BachecaCondomini;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterRichiesteIntervento extends RecyclerView.Adapter<AdapterRichiesteIntervento.MyViewHolder> {

    private ArrayList<CardTicketIntervento> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TdataTicket;
        TextView Toggetto;
        TextView Tstabile;
        TextView Tindirizzo;
        TextView IdTicket;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.Tstabile = (TextView) itemView.findViewById(R.id.D_Condominio);
            this.Tindirizzo = (TextView) itemView.findViewById(R.id.D_Indirizzo);
            this.Toggetto = (TextView) itemView.findViewById(R.id.D_Oggetto);
            this.TdataTicket = (TextView) itemView.findViewById(R.id.D_Data);
            this.IdTicket = (TextView) itemView.findViewById(R.id.D_IDIntervento);
            }
    }

    public AdapterRichiesteIntervento(ArrayList<CardTicketIntervento> dataset) {
        this.dataset = dataset;
        }

    @Override
    public AdapterRichiesteIntervento.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_richiesta_intervento, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(BachecaRichiesteIntervento.myOnClickListener);

        AdapterRichiesteIntervento.MyViewHolder myViewHolder = new AdapterRichiesteIntervento.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRichiesteIntervento.MyViewHolder holder, final int listPosition) {

        TextView Tstabile = holder.Tstabile;
        TextView Tindirizzo = holder.Tindirizzo;
        TextView Toggetto = holder.Toggetto;
        TextView TdataTicket = holder.TdataTicket;
        TextView IdTicket = holder.IdTicket;

        Tstabile.setText(dataset.get(listPosition).getNomeStabile());
        Tindirizzo.setText(dataset.get(listPosition).getIndirizzoStabile());
        Toggetto.setText(dataset.get(listPosition).getOggetto());
        TdataTicket.setText(dataset.get(listPosition).getDataTicket());
        IdTicket.setText(dataset.get(listPosition).getIdTicketIntervento());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}