package com.gambino_serra.condomanager_amministratore.View.Register;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gambino_serra.condomanager_amministratore.View.Utente.LoginActivity;
import com.gambino_serra.condomanager_amministratore.tesi.R;

/**
 * La classe modella la Dialog per visualizzare un messaggio
 * di avvenuta registrazione.
 */
public class DialogRegistrationSuccess extends DialogFragment {

    public DialogRegistrationSuccess() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.registration_detail);

        builder.setView(inflater.inflate(R.layout.dialog_registration_success, null))

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {
                        Intent in = new Intent(getActivity(), LoginActivity.class);
                        startActivity(in);
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView testo = (TextView) this.getDialog().findViewById(R.id.richiesta);
        testo.setText(R.string.registration_success);

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

