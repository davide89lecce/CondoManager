package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggionamentiTicket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterAggiornamentiTicket extends RecyclerView.Adapter<AdapterAggiornamentiTicket.MyViewHolder> {

    private ArrayList<CardTicketIntervento> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TidTicketIntervento;
        TextView Toggetto;
        TextView TstatoIntervento;
        TextView TaggiornamentoIntervento;
        TextView Tdata;
        TextView TnumeroAggiornamenti;

        public MyViewHolder(View itemView) {
            super(itemView);

            TidTicketIntervento = (TextView) itemView.findViewById(R.id.IDTicket);
            Toggetto = (TextView) itemView.findViewById(R.id.Oggetto_Intervento);
            TstatoIntervento = (TextView) itemView.findViewById(R.id.Stato_Interv);
            TaggiornamentoIntervento = (TextView) itemView.findViewById(R.id.Aggiornamento_Interv);
            Tdata = (TextView) itemView.findViewById(R.id.DataAgg_Interv);
            TnumeroAggiornamenti = (TextView) itemView.findViewById(R.id.textNumAggiornamenti);
            }
    }

    public AdapterAggiornamentiTicket(ArrayList<CardTicketIntervento> dataset) {
        this.dataset = dataset;
        }

    @Override
    public AdapterAggiornamentiTicket.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_aggiornamenti_ticket, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        //view.setOnClickListener(BachecaStabili.myOnClickListener);

        AdapterAggiornamentiTicket.MyViewHolder myViewHolder = new AdapterAggiornamentiTicket.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterAggiornamentiTicket.MyViewHolder holder, final int listPosition) {

        TextView TidTicketIntervento = holder.TidTicketIntervento;
        TextView Toggetto = holder.Toggetto;
        TextView TstatoIntervento = holder.TstatoIntervento;
        TextView TaggiornamentoIntervento = holder.TaggiornamentoIntervento;
        TextView Tdata = holder.Tdata;
        TextView TnumeroAggiornamenti = holder.TnumeroAggiornamenti;

        TidTicketIntervento.setText(dataset.get(listPosition).getDataTicket());
        Toggetto.setText(dataset.get(listPosition).getOggetto());
        TstatoIntervento.setText(dataset.get(listPosition).getStato().toString());
        TaggiornamentoIntervento.setText(dataset.get(listPosition).getAggiornamentoCondomini());
        Tdata.setText(dataset.get(listPosition).getDataUltimoAggiornamento());
        TnumeroAggiornamenti.setText(dataset.get(listPosition).getNumeroAggiornamenti());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}