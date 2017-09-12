package com.gambino_serra.condomanager_amministratore.View.Stabile.Interventi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

import static com.gambino_serra.condomanager_amministratore.tesi.R.id.Logo_Interv;


public class AdapterInterventiInCorso extends RecyclerView.Adapter<AdapterInterventiInCorso.MyViewHolder> {

    private ArrayList<TicketIntervento> dataset;

    int row;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mOggetto;
        TextView mStato;
        TextView mDataAgg;
        TextView mAgg;
        ImageView mLogoStato;
        TextView IdTicket;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mOggetto = (TextView) itemView.findViewById(R.id.Oggetto_Avviso);
            this.mStato = (TextView) itemView.findViewById(R.id.Stato_Interv);
            this.mDataAgg = (TextView) itemView.findViewById(R.id.DataAgg_Interv);
            this.mAgg = (TextView) itemView.findViewById(R.id.Aggiornamento_Interv);
            this.mLogoStato = (ImageView) itemView.findViewById(Logo_Interv);
            //Campo nascosto per recuperare il riferimento
            this.IdTicket = (TextView) itemView.findViewById(R.id.IDTicket);
        }
    }

    public AdapterInterventiInCorso(ArrayList<TicketIntervento> dataset) {
        this.dataset = dataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_intervento_bacheca, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(com.gambino_serra.condomanager_amministratore.View.Stabile.Interventi.InterventiInCorso.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView mOggetto = holder.mOggetto;
        TextView mStato = holder.mStato;
        TextView mDataAgg = holder.mDataAgg;
        TextView mAgg = holder.mAgg;
        ImageView mLogoStato = holder.mLogoStato;
        TextView IdTicket = holder.IdTicket;


        mOggetto.setText(dataset.get(listPosition).getOggetto());
        //mStato.setText(dataset.get(listPosition).getStato());
        mDataAgg.setText(dataset.get(listPosition).getDataUltimoAggiornamento());
        mAgg.setText(dataset.get(listPosition).getAggiornamentoCondomini());
        IdTicket.setText(dataset.get(listPosition).getIdTicketIntervento());
        //textViewSegnalazione.setText( dataset.get(listPosition).getOggetto());


        String stato = dataset.get(listPosition).getStato();

        switch(stato) {

            // intervento richiesto o rifiutato (al condomino interressa solo che sia stato processato
            // dall'amministratore, se un fornitore lo rifiuterà, lui lo vedrà ancora in attesa
            // di essere preso in carico
            case "in attesa" :
            case "rifiutato" :
                {
                mLogoStato.setImageResource(R.drawable.tool_blue);
                    mStato.setText("Intervento Richiesto");
                break;
                }

            case "in corso": // intervento in corso
                {
                mLogoStato.setImageResource(R.drawable.tool_orange);
                    mStato.setText("Intervento in Corso");
                break;
                }
            case "completato":   // intervento concluso
            case "archiviato" :
                {
                mLogoStato.setImageResource(R.drawable.tool_green);
                    mStato.setText("Intervento Completato");
                break;
                }
                //TODO : case "archiviato":

            default:
        }

    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
