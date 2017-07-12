package com.gambino_serra.condomanager_amministratore.View.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gambino_serra.condomanager_amministratore.View.Utente.RegisterFornitoreActivity;
import com.gambino_serra.condomanager_amministratore.tesi.R;


/**
 * Created by condomanager_amministratore on 12/02/17.
 */

public class RegisterActivity extends AppCompatActivity {

    String tipoUtente = "A";
    RadioButton amministratorebtn;
    RadioButton fornitorebtn;
    Button btnAvanti;
    RadioGroup rg;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_logo);

        amministratorebtn = (RadioButton) findViewById(R.id.amministratoreBtn);
        fornitorebtn = (RadioButton) findViewById(R.id.fornitoreBtn);
        btnAvanti = (Button) findViewById(R.id.btnAvanti);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            /**
             * Il metodo gestisce il tipo di utente con il quale ci si vuole registare.
             * @param group
             * @param checkedId
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                pos = rg.indexOfChild(findViewById(checkedId));

                switch (pos) {
                    case 0:
                        amministratorebtn.setChecked(true);
                        tipoUtente = "A";
                        break;
                    case 1:
                        fornitorebtn.setChecked(true);
                        tipoUtente = "F";
                        break;
                    default:
                        break;
                }
            }
        });


        btnAvanti.setOnClickListener(new View.OnClickListener() {

            /**
             * Il metodo richiama l'activity richiesta per la registrazione
             * @param v istanza della View
             */
            @Override
            public void onClick(View v) {

                if(tipoUtente.equals("A")){
                    Intent in = new Intent(getApplicationContext(), RegisterAmministratoreActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }else{
                    Intent in = new Intent(getApplicationContext(), RegisterFornitoreActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }

            }
        });


    }
}
