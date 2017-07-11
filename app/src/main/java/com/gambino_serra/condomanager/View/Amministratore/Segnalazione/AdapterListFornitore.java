package com.gambino_serra.condomanager.View.Amministratore.Segnalazione;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gambino_serra.condomanager.tesi.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class AdapterListFornitore extends RecyclerView.Adapter<AdapterListFornitore.MyViewHolder> {

    private ArrayList<DataFornitore> dataSet;
    int row;
    private Context context;
    private Activity activity;
    private static final String MY_PREFERENCES = "preferences";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textFornitore;
        TextView textIndirizzo;
        TextView textUsernameF;
        LinearLayout listFornitore;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textFornitore = (TextView) itemView.findViewById(R.id.textFornitore);
            this.textIndirizzo = (TextView) itemView.findViewById(R.id.textIndirizzo);
            this.textUsernameF = (TextView) itemView.findViewById(R.id.textUsernameF);
            this.listFornitore = (LinearLayout) itemView.findViewById(R.id.layout_fornitore);
        }
    }

    public AdapterListFornitore(ArrayList<DataFornitore> data, Context context, Activity activity) {
        this.dataSet = data;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fornitore, parent, false);

        view.setOnClickListener(SegnalazioneCategoria.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textFornitore = holder.textFornitore;
        TextView textIndirizzo = holder.textIndirizzo;
        TextView textUsernameF = holder.textUsernameF;

        textFornitore.setText(dataSet.get(listPosition).getFornitore());
        textIndirizzo.setText(dataSet.get(listPosition).getIndirizzo());
        textUsernameF.setText(dataSet.get(listPosition).getUsernameF());

        holder.listFornitore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row=listPosition;
                notifyDataSetChanged();

                final SharedPreferences sharedPrefs = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("fornitore", dataSet.get(listPosition).getUsernameF());
                editor.apply();

            }
        });

        if(row==listPosition){
            holder.listFornitore.setBackgroundColor(Color.parseColor("#CFD8DC"));
        }
        else
        {
            holder.listFornitore.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
