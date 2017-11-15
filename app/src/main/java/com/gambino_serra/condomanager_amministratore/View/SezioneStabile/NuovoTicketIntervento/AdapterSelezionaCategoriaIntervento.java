package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoTicketIntervento;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.tesi.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class AdapterSelezionaCategoriaIntervento extends RecyclerView.Adapter<AdapterSelezionaCategoriaIntervento.MyViewHolder> {

    private ArrayList<DataCategoriaIntevento> dataSet;
    int row;
    private Context context;
    private Activity activity;
    private static final String MY_PREFERENCES = "preferences";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textCategoria;
        RelativeLayout listCategoria;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textCategoria = (TextView) itemView.findViewById(R.id.textCategoria);
            this.listCategoria = (RelativeLayout) itemView.findViewById(R.id.layout_categoria);
        }
    }

    public AdapterSelezionaCategoriaIntervento(ArrayList<DataCategoriaIntevento> data, Context context, Activity activity) {
        this.dataSet = data;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categoria, parent, false);

       // view.setOnClickListener(SegnalazioneCategoria.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textCategoria = holder.textCategoria;

        textCategoria.setText(dataSet.get(listPosition).getName());


        holder.listCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row=listPosition;
                notifyDataSetChanged();

                final SharedPreferences sharedPrefs = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("categoria", dataSet.get(listPosition).getName());
                editor.apply();
                Log.d("ciao",sharedPrefs.getString("categoria", ""));
            }
        });

        if(row==listPosition){
            holder.listCategoria.setBackgroundColor(Color.parseColor("#CFD8DC"));
            TextView textDescrizioneCategoria = (TextView) activity.findViewById(R.id.textDescrizioneCategoria);
            textDescrizioneCategoria.setText(dataSet.get(listPosition).getDescrizione());
        }
        else
        {
            holder.listCategoria.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
