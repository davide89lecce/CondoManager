package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.NuovoAvviso;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class DialogNuovoAvviso extends DialogFragment{

    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";
    static final int TIME_DIALOG_ID1 = 1;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private String uidCondomino;
    private String stabile;
    private String uidAmministratore;
    private String idStabile;
    private String dataScadenza;

    EditText EoggettoAvviso;
    EditText EdescrizioneAvviso;
    ToggleButton EtipologiaAvviso;
    Button datePickerButton;

    String oggettoAvviso;
    String descrizioneAvviso;
    String tipologiaAvviso;

    Integer year;
    Integer month;
    Integer day;

    String username;

    private StorageReference mStorage;
    private StorageReference filepath;
    private Uri urlpath;

    public DialogNuovoAvviso() { }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        username = sharedPrefs.getString(LOGGED_USER, "").toString();

        firebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        TextView title = new TextView(getActivity());
        title.setText(R.string.app_name);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30);
        title.setBackgroundResource(R.color.colorPrimary);
        title.setTextColor(Color.WHITE);
        builder.setCustomTitle(title);

        builder.setView(inflater.inflate(R.layout.dialog_nuovo_avviso, null))

                .setPositiveButton("CONFERMA", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    public void onClick(DialogInterface dialog, int id) {

                        if ( ! oggettoAvviso.isEmpty()  &&
                             ! descrizioneAvviso.isEmpty() &&
                             ! dataScadenza.isEmpty() ) {

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("Avvisi");

                            oggettoAvviso = EoggettoAvviso.getText().toString();
                            descrizioneAvviso = EdescrizioneAvviso.getText().toString();

                            if (EtipologiaAvviso.isChecked()) {
                                tipologiaAvviso = "importante";
                            } else {
                                tipologiaAvviso = "standard";
                            }

                            addAvviso(databaseReference, uidAmministratore, idStabile, oggettoAvviso, descrizioneAvviso, tipologiaAvviso, dataScadenza);
                            Toast.makeText(getActivity(), "Avviso inserito correttamente", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }else{
                            Toast.makeText( getActivity() , "Assicurarsi di aver inserito correttamente tutti i campi", Toast.LENGTH_LONG);
                        }
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

        Bundle bundle = getArguments();

        EoggettoAvviso = (EditText) this.getDialog().findViewById(R.id.editTextOggetto);
        EdescrizioneAvviso = (EditText) this.getDialog().findViewById(R.id.editTextDescrizione);
        EtipologiaAvviso = (ToggleButton) this.getDialog().findViewById(R.id.toggleButtonTipologia);
        datePickerButton = (Button) this.getDialog().findViewById(R.id.datePickerButton);

        //lettura uid condomino -->  codice fiscale stabile, uid amministratore
        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        idStabile = sharedPrefs.getString("idStabile", "");

        if(bundle == null) {
            final Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            year = mCalendar.get(Calendar.YEAR);
            month = mCalendar.get(Calendar.MONTH);
            day = mCalendar.get(Calendar.DAY_OF_MONTH);
            dataScadenza = day.toString() + "/" + month.toString() + "/" + year.toString(); //+ " 00:00";

        }else{
            dataScadenza = bundle.getString("dataScadenza");
        }

        datePickerButton.setText(dataScadenza);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().showDialog(TIME_DIALOG_ID1);
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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


    private void addAvviso(DatabaseReference postRef, final String uidAmministratore, final String idStabile, final String oggettoAvviso, final String descrizioneAvviso, final String tipologiaAvviso, final String dataScadenza) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                //Legge il valore del nodo counter
                Integer counter = mutableData.child("counter").getValue(Integer.class);

                if (counter == null) {
                    return Transaction.success(mutableData);
                }

                //Incrementa counter
                counter = counter + 1;

                //Ricava la data e la formatta nel formato appropriato
                Date newDate = new Date(new Date().getTime());
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
                String stringdate = dt.format(newDate);

                mutableData.child(counter.toString()).child("amministratore").setValue(uidAmministratore);
                mutableData.child(counter.toString()).child("descrizione").setValue(descrizioneAvviso);
                mutableData.child(counter.toString()).child("oggetto").setValue(oggettoAvviso);
                mutableData.child(counter.toString()).child("stabile").setValue(idStabile);
                mutableData.child(counter.toString()).child("tipologia").setValue(tipologiaAvviso);
                mutableData.child(counter.toString()).child("scadenza").setValue(dataScadenza);

                //Setta il counter del nodo Messaggi_condomino
                mutableData.child("counter").setValue(counter);

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
            }
        });
    }

}

