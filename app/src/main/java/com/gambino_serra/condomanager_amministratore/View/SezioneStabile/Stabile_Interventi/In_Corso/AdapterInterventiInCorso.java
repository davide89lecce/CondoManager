package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.In_Corso;


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

public class AdapterInterventiInCorso extends RecyclerView.Adapter<AdapterInterventiInCorso.MyViewHolder> {

    private ArrayList<CardTicketIntervento> dataset;

    int row;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mOggetto;
        TextView mStato;
        TextView mDataAgg;
        ImageView mLogoStato;
        ImageView mBackStato;
        ImageView mCircle;
        TextView IdTicket;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mOggetto = (TextView) itemView.findViewById(R.id.Oggetto_Avviso);
            this.mStato = (TextView) itemView.findViewById(R.id.Stato_Interv);
            this.mDataAgg = (TextView) itemView.findViewById(R.id.DataAgg_Interv);
            this.mLogoStato = (ImageView) itemView.findViewById(Logo_Interv);
            this.mBackStato = (ImageView) itemView.findViewById(Back_Logo);
            this.mCircle = (ImageView) itemView.findViewById(circle);
            //Campo nascosto per recuperare il riferimento
            this.IdTicket = (TextView) itemView.findViewById(R.id.IDTicket);
        }
    }

    public AdapterInterventiInCorso (ArrayList<CardTicketIntervento> dataset) {
        this.dataset = dataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sezionestabile_card_intervento, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.In_Corso.InterventiInCorso.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView mOggetto = holder.mOggetto;
        TextView mStato = holder.mStato;
        TextView mDataAgg = holder.mDataAgg;
        ImageView mLogoStato = holder.mLogoStato;
        ImageView mBackStato = holder.mBackStato;
        ImageView mCircle = holder.mCircle;
        TextView IdTicket = holder.IdTicket;


        mOggetto.setText(dataset.get(listPosition).getOggetto());
        //mStato.setText(dataset.get(listPosition).getStato());
        IdTicket.setText(dataset.get(listPosition).getIdTicketIntervento());
        //textViewSegnalazione.setText( dataset.get(listPosition).getOggetto());


        // Se non è stato ancora inserito un aggiornamento, verrà visualizzata la prima descrizione
        // inserita dall'amministratore, altrimenti verranno visualizzati i dati dell'ultimo
        // aggiornamento inserito con relativa data
        if ( dataset.get(listPosition).getAggiornamentoCondomini().equals("-")){
            mDataAgg.setText(dataset.get(listPosition).getDataTicket());
        }else{
            mDataAgg.setText(dataset.get(listPosition).getDataUltimoAggiornamento());
        }


        String stato = dataset.get(listPosition).getStato();

        switch(stato) {

            // intervento richiesto o rifiutato (al condomino interressa solo che sia stato processato
            // dall'amministratore, se un fornitore lo rifiuterà, lui lo vedrà ancora in attesa
            // di essere preso in carico
            case "in attesa":
            case "rifiutato": {
                mBackStato.setColorFilter(Color.parseColor("#68cdfa"));
                mCircle.setColorFilter(Color.parseColor("#68cdfa"));
                mStato.setText("Intervento Richiesto");
                break;
            }

            case "in corso": // intervento in corso
            {
                mBackStato.setColorFilter(Color.parseColor("#ffa726"));
                mCircle.setColorFilter(Color.parseColor("#ffa726"));
                mStato.setText("Intervento in Corso");
                break;
            }
            case "completato":   // intervento concluso
            case "archiviato": {
                mBackStato.setColorFilter(Color.parseColor("#88f741"));
                mCircle.setColorFilter(Color.parseColor("#88f741"));
                mStato.setText("Intervento Completato");
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


