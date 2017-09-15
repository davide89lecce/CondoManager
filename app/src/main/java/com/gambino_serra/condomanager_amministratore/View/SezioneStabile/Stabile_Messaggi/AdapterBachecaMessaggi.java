package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Messaggi;


import android.content.Context;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.Avviso;
import com.gambino_serra.condomanager_amministratore.Model.Entity.Messaggio;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

import static com.gambino_serra.condomanager_amministratore.tesi.R.id.parent;

public class AdapterBachecaMessaggi extends RecyclerView.Adapter<AdapterBachecaMessaggi.MyViewHolder> {

    private ArrayList<Messaggio> dataset;

    Context context;
    int row;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView DataMessaggio;
        TextView TestoMessaggio;
        TextView TipologiaMessaggio;
        ImageView imageViewMessaggio;
        TextView textViewIdSegnalazione;
        TextView mMittente;
        ImageView mTipologia;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewMessaggio = (ImageView) itemView.findViewById(R.id.logoMessaggio);
            this.TestoMessaggio = (TextView) itemView.findViewById(R.id.TestoMessaggio);
            this.TipologiaMessaggio = (TextView) itemView.findViewById(R.id.TipologiaMessaggio);
            this.DataMessaggio = (TextView) itemView.findViewById(R.id.DataMessaggio);
            this.textViewIdSegnalazione = (TextView) itemView.findViewById(R.id.textViewIdSegnalazione);
            this.mTipologia = (ImageView) itemView.findViewById(R.id.imageViewTipologia);
            this.mMittente = (TextView) itemView.findViewById(R.id.CondominoMittente);
        }
    }

    public AdapterBachecaMessaggi(ArrayList<Messaggio> dataset) {
        this.dataset = dataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sezionestabile_card_messaggio, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Interventi
        view.setOnClickListener(BachecaMessaggi.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView TipologiaMessaggio = holder.TipologiaMessaggio;
        TextView TestoMessaggio = holder.TestoMessaggio;
        TextView DataMessaggio = holder.DataMessaggio;
        ImageView imageViewMessaggio = holder.imageViewMessaggio;
        TextView textViewIdSegnalazione = holder.textViewIdSegnalazione;
        ImageView mTipologia = holder.mTipologia;
        TextView mMittente = holder.mMittente;

        TipologiaMessaggio.setText( dataset.get(listPosition).getTipologia());
        TestoMessaggio.setText( dataset.get(listPosition).getMessaggio());
        DataMessaggio.setText(dataset.get(listPosition).getData());
        textViewIdSegnalazione.setText(dataset.get(listPosition).getId());
        mMittente.setText(dataset.get(listPosition).getIdCondomino());

        String tipologia = dataset.get(listPosition).getTipologia();

        switch(tipologia) {

            case "messaggio" :
            {
                //TODO: ricava contesto
                //mTipologia.setBackgroundColor( ContextCompat.getColor( context , R.color.colorMess) );
                break;
            }

            case "segnalazione":
            {
                //mTipologia.setBackgroundColor(  R.color.colorMess );
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
