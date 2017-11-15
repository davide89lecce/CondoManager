package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiMessaggi;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.Messaggio;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AdapterNotificheRapporti;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterAggiornamentiMessaggi extends RecyclerView.Adapter<AdapterAggiornamentiMessaggi.MyViewHolder>{


    private ArrayList<Messaggio> dataset;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mIDMessaggio;
        TextView mTipologia;
        TextView mStabile;
        TextView mCondomino;
        TextView mData;
        ImageView mLogoTipo;
        ImageView mBackTipo;
        ImageView mCircle;
        ImageView nuovo;

        public MyViewHolder(View itemView) {
            super(itemView);

            mIDMessaggio = (TextView) itemView.findViewById(R.id.textViewIdSegnalazione);
            mTipologia = (TextView) itemView.findViewById(R.id.TipologiaMessaggio);
            mStabile = (TextView) itemView.findViewById(R.id.D_Stabile);
            mCondomino = (TextView) itemView.findViewById(R.id.CondominoMittente);
            mData = (TextView) itemView.findViewById(R.id.DataMessaggio);
            mLogoTipo = (ImageView) itemView.findViewById(R.id.imageViewMessaggio);
            mBackTipo = (ImageView) itemView.findViewById(R.id.imageView);
            mCircle = (ImageView) itemView.findViewById(R.id.imageView2);
            nuovo = (ImageView) itemView.findViewById(R.id.imageView12);
        }
    }

    public AdapterAggiornamentiMessaggi(ArrayList<Messaggio> dataset , Context context ) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public AdapterAggiornamentiMessaggi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_nuovo_messaggio, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe Messaggi
        view.setOnClickListener(AggiornamentiMessaggi.myOnClickListener);

        AdapterAggiornamentiMessaggi.MyViewHolder myViewHolder = new AdapterAggiornamentiMessaggi.MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final AdapterAggiornamentiMessaggi.MyViewHolder holder, final int listPosition) {

        TextView mIDMessaggio = holder.mIDMessaggio;
        TextView mTipologia = holder.mTipologia;
        TextView mStabile = holder.mStabile;
        TextView mCondomino = holder.mCondomino;
        TextView mData = holder.mData;
        ImageView mLogoTipo = holder.mLogoTipo;
        ImageView mBackTipo = holder.mBackTipo;
        ImageView mCircle = holder.mCircle;
        ImageView nuovo = holder.nuovo;



        mIDMessaggio.setText(dataset.get(listPosition).getId());
        mTipologia.setText(dataset.get(listPosition).getTipologia());
        mStabile.setText(dataset.get(listPosition).getNomeStabile());
        mCondomino.setText(dataset.get(listPosition).getNomeCondomino());
        mData.setText(dataset.get(listPosition).getData());



        int messColor = context.getResources().getColor(R.color.colorMess);
        int segnColor = context.getResources().getColor(R.color.colorSegnalaz);
        Drawable blue_msg  = context.getResources().getDrawable(R.drawable.blue_msg);
        Drawable red_msg  = context.getResources().getDrawable(R.drawable.red_msg);

        String tipo = dataset.get(listPosition).getTipologia();

        if ( dataset.get(listPosition).getLetto().equals("si") )
            nuovo.setVisibility(View.GONE);


        String tipologia = dataset.get(listPosition).getTipologia();

        switch(tipo) {

            case "Messaggio" :
            {
                mBackTipo.setColorFilter( messColor );
                mCircle.setColorFilter( messColor);
                mLogoTipo.setImageDrawable(blue_msg);
                break;
            }

            case "Segnalazione":
            {
                mBackTipo.setColorFilter( segnColor );
                mCircle.setColorFilter( segnColor );
                mLogoTipo.setImageDrawable(red_msg);
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
