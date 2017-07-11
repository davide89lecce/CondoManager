package com.gambino_serra.condomanager.View.Amministratore.NuovoTicket;

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
import com.gambino_serra.condomanager.View.Amministratore.Segnalazione.SegnalazioneCategoria;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class AdapterListCondominio extends RecyclerView.Adapter<AdapterListCondominio.MyViewHolder> {

    private ArrayList<DataCondominio> dataSet;
    int row;
    private Context context;
    private Activity activity;
    private static final String MY_PREFERENCES = "preferences";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textCondominio;
        TextView textIndirizzo;
        TextView textIdCondominio;
        LinearLayout listCondominio;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textCondominio = (TextView) itemView.findViewById(R.id.textCondominio);
            this.textIndirizzo = (TextView) itemView.findViewById(R.id.textIndirizzo);
            this.textIdCondominio = (TextView) itemView.findViewById(R.id.textIdCondominio);
            this.listCondominio = (LinearLayout) itemView.findViewById(R.id.layout_condominio);
        }
    }

    public AdapterListCondominio(ArrayList<DataCondominio> data, Context context, Activity activity) {
        this.dataSet = data;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_condominio, parent, false);

        view.setOnClickListener(SegnalazioneCategoria.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textCondominio = holder.textCondominio;
        TextView textIndirizzo = holder.textIndirizzo;
        TextView textIdCondominio = holder.textIdCondominio;

        textCondominio.setText(dataSet.get(listPosition).getCondominio());
        textIndirizzo.setText(dataSet.get(listPosition).getIndirizzo());
        textIdCondominio.setText(dataSet.get(listPosition).getIdCondominio());

        holder.listCondominio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row=listPosition;
                notifyDataSetChanged();

                final SharedPreferences sharedPrefs = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("idCondominio", dataSet.get(listPosition).getIdCondominio());
                editor.apply();

            }
        });

        if(row==listPosition){
            holder.listCondominio.setBackgroundColor(Color.parseColor("#CFD8DC"));

        }
        else
        {
            holder.listCondominio.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
