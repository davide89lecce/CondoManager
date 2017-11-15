package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggionamentiTicket;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardTicketIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import java.util.ArrayList;

import static com.gambino_serra.condomanager_amministratore.tesi.R.id.Back_Logo;
import static com.gambino_serra.condomanager_amministratore.tesi.R.id.Logo_Interv;
import static com.gambino_serra.condomanager_amministratore.tesi.R.id.circle;

public class AdapterAggiornamentiTicket extends RecyclerView.Adapter<AdapterAggiornamentiTicket.MyViewHolder> {

    private ArrayList<CardTicketIntervento> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TidTicketIntervento;
        TextView Toggetto;
        TextView TstatoIntervento;
        TextView TaggiornamentoIntervento;
        TextView Tdata;
        TextView TnumeroAggiornamenti;
        ImageView mLogoStato;
        ImageView mBackStato;
        ImageView mCircle;

        public MyViewHolder(View itemView) {
            super(itemView);

            TidTicketIntervento = (TextView) itemView.findViewById(R.id.IDTicket);
            Toggetto = (TextView) itemView.findViewById(R.id.Aggiornamento_Interv);
            TstatoIntervento = (TextView) itemView.findViewById(R.id.Stato_Interv);
            TaggiornamentoIntervento = (TextView) itemView.findViewById(R.id.Oggetto_Intervento);
            Tdata = (TextView) itemView.findViewById(R.id.DataAgg_Interv);
            TnumeroAggiornamenti = (TextView) itemView.findViewById(R.id.textNumAggiornamenti);
            mBackStato = (ImageView) itemView.findViewById(Back_Logo);
            mCircle = (ImageView) itemView.findViewById(circle);
            mLogoStato = (ImageView) itemView.findViewById(Logo_Interv);
            }
    }

    public AdapterAggiornamentiTicket(ArrayList<CardTicketIntervento> dataset) {
        this.dataset = dataset;
        }

    @Override
    public AdapterAggiornamentiTicket.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_aggiornamenti_ticket, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(AggiornamentiTicket.myOnClickListener);

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
        ImageView mBackStato = holder.mBackStato;
        ImageView mCircle = holder.mCircle;
        ImageView mLogoStato = holder.mLogoStato;

        TidTicketIntervento.setText(dataset.get(listPosition).getIdTicketIntervento());
        Toggetto.setText(dataset.get(listPosition).getOggetto());
        TstatoIntervento.setText(dataset.get(listPosition).getStato().toString());
        TaggiornamentoIntervento.setText(dataset.get(listPosition).getIdStabile());
        Tdata.setText(dataset.get(listPosition).getDataTicket());
        TnumeroAggiornamenti.setText(dataset.get(listPosition).getNumeroAggiornamenti());


        String stato = dataset.get(listPosition).getStato();

        switch (stato) {

            // intervento richiesto o rifiutato (al condomino interressa solo che sia stato processato
            // dall'amministratore, se un fornitore lo rifiuterà, lui lo vedrà ancora in attesa
            // di essere preso in carico
            case "in attesa": {
                mBackStato.setColorFilter(Color.parseColor("#68cdfa"));
                mCircle.setColorFilter(Color.parseColor("#68cdfa"));
                TstatoIntervento.setText("Intervento Richiesto");
                break;
            }

            case "in corso": // intervento in corso
            {
                mBackStato.setColorFilter(Color.parseColor("#ffa726"));
                mCircle.setColorFilter(Color.parseColor("#ffa726"));
                TstatoIntervento.setText("Intervento in Corso");
                break;
            }
            case "completato":   // intervento concluso
            case "archiviato": {
                mBackStato.setColorFilter(Color.parseColor("#88f741"));
                mCircle.setColorFilter(Color.parseColor("#88f741"));
                TstatoIntervento.setText("Intervento Completato");
                break;
            }
            case "rifiutato": {
                mBackStato.setColorFilter(Color.parseColor("#e73f42"));
                mCircle.setColorFilter(Color.parseColor("#e73f42"));
                TstatoIntervento.setText("Intervento Rifiutato");
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