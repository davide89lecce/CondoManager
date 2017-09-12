package com.gambino_serra.condomanager_amministratore.View.Dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.tesi.R;

public class DialogConfermaAccettaIntervento extends DialogFragment {

    private Firebase firebase;

    public DialogConfermaAccettaIntervento() { }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final String idTicket= bundle.getString("idTicket");
        firebase = FirebaseDB.getInterventi();



        TextView title =  new TextView(getActivity());
        title.setText("ACCETTAZIONE RICHIESTA D'INTERVENTO");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.color.primarySegnalazione);
        title.setTextColor(Color.WHITE);
        builder.setCustomTitle(title);

        builder.setView(inflater.inflate(R.layout.dialog_conferma_accetta_intervento, null))

                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {

                        firebase.child(idTicket).child("stato").setValue("in corso");

                        Intent intent = new Intent(getActivity(), MainDrawer.class);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        }
                })

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

