package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.CambiaFornitoreIntervento;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.Fornitore;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterFornitoreCambiaFornitore extends RecyclerView.Adapter<AdapterFornitoreCambiaFornitore.MyViewHolder> {

    private ArrayList<Fornitore> dataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUidFornitore;
        TextView DettaglioFornTitolare;
        TextView DettaglioFornNome;
        TextView DettaglioFornCategoria;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewUidFornitore = (TextView) itemView.findViewById(R.id.textViewUidFornitore);
            this.DettaglioFornNome = (TextView) itemView.findViewById(R.id.DettaglioFornNome);
            this.DettaglioFornTitolare = (TextView) itemView.findViewById(R.id.DettaglioFornTitolare);
            this.DettaglioFornCategoria = (TextView) itemView.findViewById(R.id.DettaglioFornCategoria);
            }
    }

    public AdapterFornitoreCambiaFornitore(ArrayList<Fornitore> dataset) {
        this.dataset = dataset;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fornitore_bacheca, parent, false);

        //Setta l'onclick sulla recycler view presente nella classe listafornitori
        view.setOnClickListener(FornitoreCambiaFornitore.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView DettaglioFornNome = holder.DettaglioFornNome;
        TextView DettaglioFornTitolare = holder.DettaglioFornTitolare;
        TextView DettaglioFornCategoria = holder.DettaglioFornCategoria;
        TextView textViewUidFornitore = holder.textViewUidFornitore;

        textViewUidFornitore.setText(dataset.get(listPosition).getUid().toString());
        DettaglioFornNome.setText(dataset.get(listPosition).getNome_azienda().toString());
        DettaglioFornTitolare.setText(dataset.get(listPosition).getNome().toString());
        DettaglioFornCategoria.setText(dataset.get(listPosition).getCategoria());
        }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}