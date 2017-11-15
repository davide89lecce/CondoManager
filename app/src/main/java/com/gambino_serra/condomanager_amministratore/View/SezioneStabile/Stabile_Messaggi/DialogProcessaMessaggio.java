package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Messaggi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoAvviso.DialogNuovoAvviso;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoTicketIntervento.SelezionaCategoriaIntervento;
import com.gambino_serra.condomanager_amministratore.tesi.R;


public class DialogProcessaMessaggio extends DialogFragment {

    private Firebase firebase;

    ConstraintLayout NuovoAvviso;
    ConstraintLayout NuovoTicket;

    public DialogProcessaMessaggio() { }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Bundle bundle = getArguments();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        TextView title =  new TextView(getActivity());
        title.setText("PROCESSA MESSAGGIO");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.color.colorPrimary);
        title.setTextColor(Color.WHITE);
        builder.setCustomTitle(title);

        builder.setView(inflater.inflate(R.layout.dialog_processa_messaggio, null))

                .setNeutralButton("ANNULLA", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        NuovoAvviso = (ConstraintLayout) this.getDialog().findViewById(R.id.CreaNuovoAvviso);
        NuovoTicket = (ConstraintLayout) this.getDialog().findViewById(R.id.CreaNuovoTicketIntervento);

        NuovoAvviso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DialogNuovoAvviso();
                newFragment.show(getFragmentManager(), "NuovoAvviso");
            }
        });

        NuovoTicket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), SelezionaCategoriaIntervento.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}