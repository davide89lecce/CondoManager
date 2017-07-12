package com.gambino_serra.condomanager_amministratore.View.Fornitore.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.Model.Entity.Segnalazione;
import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

import static com.gambino_serra.condomanager_amministratore.tesi.R.id.imageView;


public class AdapterCardsInteventiCompletati extends RecyclerView.Adapter<AdapterCardsInteventiCompletati.MyViewHolder> {

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

    public AdapterCardsInteventiCompletati(ArrayList<Segnalazione> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_fornitore_home, parent, false);

        view.setOnClickListener(InterventiCompletati.myOnClickListener);

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
                imageView.setImageResource(R.drawable.checked);
                break;

            case "B":
                imageView.setImageResource(R.drawable.checked);
                break;

            case "C":
                imageView.setImageResource(R.drawable.checked);
                break;

            case "D":
                imageView.setImageResource(R.drawable.checked);
                break;

            case "E":
                imageView.setImageResource(R.drawable.checked);
                break;

            case "F":
                imageView.setImageResource(R.drawable.checked);
                break;
            default:
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
