package com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.AggiornamentiSondaggi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.gambino_serra.condomanager_amministratore.tesi.R;

public class DettaglioSondaggioSceltaSingola extends AppCompatActivity {

    TextView tStabile;
    TextView tData;
    TextView tOggetto;
    TextView tDescrizione;
    TextView tNumCondomini;
    TextView tNumPartecipanti;
    TextView tOpzione1;
    TextView tOpzione2;
    TextView tOpzione3;
    TextView tNumOpzione1;
    TextView tNumOpzione2;
    TextView tNumOpzione3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettaglio_sondaggio_scelta_singola);

        tStabile = (TextView) findViewById(R.id.D_SondStabile);
        tData = (TextView) findViewById(R.id.D_SondData);
        tOggetto = (TextView) findViewById(R.id.D_SondOggetto);
        tDescrizione = (TextView) findViewById(R.id.D_SondDescrizione);
        tNumCondomini = (TextView) findViewById(R.id.sondNumCondomini);
        tNumPartecipanti = (TextView) findViewById(R.id.sondNumPartecipanti);
        tOpzione1 = (TextView) findViewById(R.id.Opzione1);
        tOpzione2 = (TextView) findViewById(R.id.Opzione2);
        tOpzione3 = (TextView) findViewById(R.id.Opzione3);
        tNumOpzione1 = (TextView) findViewById(R.id.numOpzione1);
        tNumOpzione2 = (TextView) findViewById(R.id.numOpzione2);
        tNumOpzione3 = (TextView) findViewById(R.id.numOpzione3);


    }
}