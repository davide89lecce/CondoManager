package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.RapportiIntervento;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.Model.Entity.CardRapportoIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Dialog.DialogVisualizzaFotoRapporto;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;


public class AdapterRapportiIntervento extends RecyclerView.Adapter<AdapterRapportiIntervento.MyViewHolder> {

    private ArrayList<CardRapportoIntervento> dataset;
    private Activity activity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TidRapportoIntervento;
        TextView Tdata;
        TextView TnotaAmministratore;
        TextView TurlFoto;
        static ImageButton TfotoRapporto;
        ImageView nuovo;
        TextView tnuovo;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.Tdata = (TextView) itemView.findViewById(R.id.D_Data);
            this.TidRapportoIntervento = (TextView) itemView.findViewById(R.id.D_IDrapporto);
            this.TnotaAmministratore = (TextView) itemView.findViewById(R.id.D_NotaAmministratore);
            this.TfotoRapporto = (ImageButton)  itemView.findViewById(R.id.btnVisualizzaFotoRapporto);
            //Campo nascosto per recuperare il riferimento
            this.TurlFoto = (TextView) itemView.findViewById(R.id.D_IDfoto);
            this.nuovo = (ImageView) itemView.findViewById(R.id.imageView12);
            this.tnuovo = (TextView) itemView.findViewById(R.id.textViewNuovo);
        }

    }

    public AdapterRapportiIntervento(ArrayList<CardRapportoIntervento> dataset, Activity activity) {
        this.dataset = dataset;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rapporto_intervento, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView Tdata = holder.Tdata;
        TextView TnotaAmministratore = holder.TnotaAmministratore;
        TextView TidRapportoIntervento = holder.TidRapportoIntervento;
        final TextView TurlFoto = holder.TurlFoto;
        final ImageButton TfotoRapporto = holder.TfotoRapporto;
        ImageView nuovo = holder.nuovo;
        TextView tnuovo = holder.tnuovo;

        MyViewHolder.TfotoRapporto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto", dataset.get(listPosition).getFoto());

                DialogVisualizzaFotoRapporto newFragment = new DialogVisualizzaFotoRapporto();
                newFragment.setArguments(bundle);
                newFragment.show(activity.getFragmentManager(), "DialogVisualizzaFotoRapporto");
                }
            });

        try
            {
            Tdata.setText(dataset.get(listPosition).getData());
            TnotaAmministratore.setText(dataset.get(listPosition).getNotaAmministratore());
            TidRapportoIntervento.setText(dataset.get(listPosition).getIdRapportoIntervento());
            TurlFoto.setText(dataset.get(listPosition).getFoto());

            if ( dataset.get(listPosition).getLetto().equals("si") ) {
                nuovo.setVisibility(View.GONE);
                tnuovo.setVisibility(View.GONE);
            }else{
                //Infine dopo averlo visualizzato, lo setta come letto ed azzera i rapporti nuovi nel ticket
                Firebase setZero = FirebaseDB.getInterventi()
                                            .child(dataset.get(listPosition).getTicketIntervento())
                                            .child("numero_aggiornamenti");
                setZero.setValue(0);

                Firebase setLetto = FirebaseDB.getRapporti()
                                            .child( dataset.get(listPosition).getIdRapportoIntervento())
                                            .child("letto");
                setLetto.setValue("si");
            }
        }

        catch (NullPointerException e){ }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
        }

}