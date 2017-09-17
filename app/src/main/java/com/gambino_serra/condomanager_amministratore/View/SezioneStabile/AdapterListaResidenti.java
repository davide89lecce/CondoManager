package com.gambino_serra.condomanager_amministratore.View.SezioneStabile;


import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

public class AdapterListaResidenti extends RecyclerView.Adapter<AdapterListaResidenti.MyViewHolder> {

    private ArrayList<String> dataset;

    public AdapterListaResidenti(ArrayList<String> dataset) {
        this.dataset = dataset;
    }


    public AdapterListaResidenti.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.residente_row_lista, parent, false);

        AdapterListaResidenti.MyViewHolder myViewHolder = new AdapterListaResidenti.MyViewHolder(view);
        return myViewHolder;
    }

    public void onBindViewHolder(final AdapterListaResidenti.MyViewHolder holder, final int listPosition) {

        TextView mNome = holder.mNome;

        mNome.setText(dataset.get(listPosition).toString());

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mNome;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mNome = (TextView) itemView.findViewById(R.id.row_nome);
        }
    }



    public int getItemCount() {
        return dataset.size();
    }
}
