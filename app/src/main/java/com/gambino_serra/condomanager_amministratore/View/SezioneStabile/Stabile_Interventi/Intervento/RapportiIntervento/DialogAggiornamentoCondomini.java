package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.RapportiIntervento;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import static android.content.Context.MODE_PRIVATE;

public class DialogAggiornamentoCondomini extends DialogFragment {

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private String uidCondomino;
    private String stabile;
    private String uidAmministratore;
    private String idStabile;

    EditText Edescrizione;
    EditText EdescrizioneAvviso;
    ToggleButton EtipologiaAvviso;

    String oggettoAvviso;
    String descrizioneAvviso;
    String tipologiaAvviso;

    String username;

    private StorageReference mStorage;
    private StorageReference filepath;
    private Uri urlpath;

    public DialogAggiornamentoCondomini() { }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        firebaseAuth = FirebaseAuth.getInstance();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        TextView title = new TextView(getActivity());
        title.setText("Aggiornamento condòmini");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.color.colorPrimary);
        title.setTextColor(Color.WHITE);
        builder.setCustomTitle(title);

        builder.setView(inflater.inflate(R.layout.dialog_aggiornamento_condomini, null))

                .setPositiveButton("CONFERMA", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {

                        Bundle bundle = getArguments();
                        String idIntervento = bundle.getString("idIntervento");

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        firebaseDatabase.getReference("Ticket_intervento").child(idIntervento)
                                .child("aggiornamento_condomini").setValue(Edescrizione.getText().toString());
                        Toast.makeText(getActivity(), "L'aggiornamento è ora visibile ai condomini", Toast.LENGTH_LONG).show();
                        dismiss();
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

        Edescrizione = (EditText) this.getDialog().findViewById(R.id.editTextOggetto);

        //lettura uid condomino -->  codice fiscale stabile, uid amministratore
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

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