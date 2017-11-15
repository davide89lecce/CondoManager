package com.gambino_serra.condomanager_amministratore.View.Dialog;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import static android.content.Context.MODE_PRIVATE;

public class DialogVisualizzaFotoRapporto extends DialogFragment {

    private static final String MY_PREFERENCES = "preferences";
    private String foto;
    private Bundle bundle;
    private FirebaseAuth firebaseAuth;
    private InputStream inputStream;

    public DialogVisualizzaFotoRapporto() { }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        firebaseAuth = FirebaseAuth.getInstance();


        builder.setView(inflater.inflate(R.layout.dialog_visualizza_foto_rapporto, null))

                .setPositiveButton("Chiudi", new DialogInterface.OnClickListener() {
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

        ImageView fotoRapporto = (ImageView) this.getDialog().findViewById(R.id.foto_rapporto);

        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);

        if (getActivity().getIntent().getExtras() != null)
            {
            bundle = getArguments();
            foto = bundle.get("foto").toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("foto", foto);
            editor.apply();
            }
        else
            {
            foto = sharedPrefs.getString("idIntervento", "").toString();
            bundle = new Bundle();
            bundle.putString("foto", foto);
            }

            if(!foto.equals("-")) {
                Picasso.with((getActivity().getApplicationContext())).load(foto).fit().centerCrop().into(fotoRapporto);
            }
        fotoRapporto.getLayoutParams().width = 720;
        fotoRapporto.getLayoutParams().height = 480;
        fotoRapporto.requestLayout();
    }
}

