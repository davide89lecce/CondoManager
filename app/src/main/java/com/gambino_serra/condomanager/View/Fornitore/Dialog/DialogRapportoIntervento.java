package com.gambino_serra.condomanager.View.Fornitore.Dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.gambino_serra.condomanager.Model.HTTPRequest.Fornitore.HTTPRequestIntervento;
import com.gambino_serra.condomanager.tesi.R;
import com.gambino_serra.condomanager.View.Fornitore.InterventoInProgramma.DetailsInterventoInProgramma;

public class DialogRapportoIntervento extends DialogFragment {

    EditText descrizioneInterventoE;
    String descrizioneIntervento;
    String idSegnalazione;

    public DialogRapportoIntervento() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        idSegnalazione = bundle.get("idSegnalazione").toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        TextView title =  new TextView(getActivity());
        title.setText(R.string.title_rapporto_intervento);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.color.primarySegnalazione);
        title.setTextColor(Color.WHITE);
        builder.setCustomTitle(title);

        builder.setView(inflater.inflate(R.layout.dialog_rapporto_intervento, null))

                .setPositiveButton(R.string.rapporto_intervento_conferma, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {

                        descrizioneIntervento = descrizioneInterventoE.getText().toString().replace("'","\\'");
                        HTTPRequestIntervento.insertIntervento(descrizioneIntervento,idSegnalazione, ((DetailsInterventoInProgramma) getActivity()), ((DetailsInterventoInProgramma) getActivity()), ((DetailsInterventoInProgramma) getActivity()));
                    }
                })
                .setNeutralButton(R.string.rapporto_intervento_annulla, new DialogInterface.OnClickListener() {
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
        TextView testo = (TextView) this.getDialog().findViewById(R.id.textRapportoIntervento);
        testo.setText(R.string.rapporto_intervento);

        descrizioneInterventoE = (EditText) this.getDialog().findViewById(R.id.descrizioneIntervento);



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

