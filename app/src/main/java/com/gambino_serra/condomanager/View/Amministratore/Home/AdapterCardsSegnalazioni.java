package com.gambino_serra.condomanager.View.Amministratore.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gambino_serra.condomanager.Model.Entity.Segnalazione;
import com.gambino_serra.condomanager.tesi.R;

import java.util.ArrayList;

import static com.gambino_serra.condomanager.tesi.R.id.imageView;


public class AdapterCardsSegnalazioni extends RecyclerView.Adapter<AdapterCardsSegnalazioni.MyViewHolder> {

    private ArrayList<Segnalazione> dataSet;

    int row;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCondominio;
        TextView textViewSegnalazione;
        ImageView imageViewIcon;
        TextView textViewIdSegnalazione;
        TextView textViewData;
        TextView textStato;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCondominio = (TextView) itemView.findViewById(R.id.textViewCondominio);
            this.textViewSegnalazione = (TextView) itemView.findViewById(R.id.textViewSegnalazione);
            this.imageViewIcon = (ImageView) itemView.findViewById(imageView);
            this.textViewIdSegnalazione = (TextView) itemView.findViewById(R.id.textViewIdSegnalazione);
            this.textViewData = (TextView) itemView.findViewById(R.id.textViewData);
            this.textStato = (TextView) itemView.findViewById(R.id.textStato);

        }
    }

    public AdapterCardsSegnalazioni(ArrayList<Segnalazione> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_amministratore_home, parent, false);

        view.setOnClickListener(Segnalazioni.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewCondominio = holder.textViewCondominio;
        TextView textViewSegnalazione = holder.textViewSegnalazione;
        ImageView imageView = holder.imageViewIcon;
        TextView textViewIdSegnalazione = holder.textViewIdSegnalazione;
        TextView textViewData = holder.textViewData;
        TextView textStato = holder.textStato;

        textViewCondominio.setText(dataSet.get(listPosition).getCondominio());
        textViewSegnalazione.setText(dataSet.get(listPosition).getSegnalazione());
        textViewIdSegnalazione.setText(dataSet.get(listPosition).getIdSegnalazione().toString());
        textViewData.setText(dataSet.get(listPosition).getData().substring(0,10));
        textStato.setText(dataSet.get(listPosition).getStato());

        String stato = dataSet.get(listPosition).getStato();

        switch(stato) {

            case "A":
                imageView.setImageResource(R.drawable.user);
                break;

            case "B":
                imageView.setImageResource(R.drawable.sand_clock2);
                break;

            case "C":
                imageView.setImageResource(R.drawable.wrench);
                break;

            case "D":
                imageView.setImageResource(R.drawable.error);
                break;

            case "E":
                imageView.setImageResource(R.drawable.checked);
                break;

            case "F":
                imageView.setImageResource(R.drawable.froyo);
                break;
            default:
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
