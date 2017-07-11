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


public class AdapterListCondomino extends RecyclerView.Adapter<AdapterListCondomino.MyViewHolder> {

    private ArrayList<DataCondomino> dataSet;
    int row;
    private Context context;
    private Activity activity;
    private static final String MY_PREFERENCES = "preferences";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textUsernameC;
        TextView textCondomino;

        LinearLayout listCondomino;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textUsernameC = (TextView) itemView.findViewById(R.id.textUsernameC);
            this.textCondomino = (TextView) itemView.findViewById(R.id.textCondomino);
            this.listCondomino = (LinearLayout) itemView.findViewById(R.id.layout_condomino);
        }
    }

    public AdapterListCondomino(ArrayList<DataCondomino> data, Context context, Activity activity) {
        this.dataSet = data;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_condomino, parent, false);

        view.setOnClickListener(SegnalazioneCategoria.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textUsernameC = holder.textUsernameC;
        TextView textCondomino = holder.textCondomino;

        textUsernameC.setText(dataSet.get(listPosition).getUsernameC());
        textCondomino.setText(dataSet.get(listPosition).getCondomino());


        holder.listCondomino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row=listPosition;
                notifyDataSetChanged();

                final SharedPreferences sharedPrefs = context.getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("condomino", dataSet.get(listPosition).getUsernameC());
                editor.apply();

            }
        });

        if(row==listPosition){
            holder.listCondomino.setBackgroundColor(Color.parseColor("#CFD8DC"));

        }
        else
        {
            holder.listCondomino.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
