package com.gambino_serra.condomanager_amministratore.View.Stabile.Interventi.old;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by condomanager_condomino on 06/03/17.
 */

public class old_DettaglioIntervento extends AppCompatActivity {
//
//    private static final String MY_PREFERENCES = "preferences";
//    private static final String LOGGED_USER = "username";
//
//    String username = "";
//    String idSegnalazione = "";
//    String data = "";
//    String segnalazione = "";
//    String usernameCondomino = "";
//    String condominio = "";
//    String telefonoAmministratore = "";
//    String fornitore = "";
//    String stato = "";
//    String idCondominio = "";
//    String impiantoNome = "";
//    String azienda = "";
//    String condomino = "";
//
//    TextView dataT;
//    TextView segnalazioneT;
//    TextView condominoT;
//    TextView impiantoT;
//    TextView fornitoreT;
//    TextView statoT;
//    TextView condominioT;
//    LinearLayout btnChiama;
//    TextView descrizioneStatoT;
//    ImageView imageStatoI;
//
//    private Firebase firebaseDB;
//    private FirebaseUser firebaseUser;
//    private FirebaseAuth firebaseAuth;
//    private DatabaseReference databaseReference;
//    private FirebaseDatabase firebaseDatabase;
//
//    Map<String, Object> ticketInterventoMap;
//
//    Bundle bundle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dettaglio_intervento);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
//        username = sharedPrefs.getString(LOGGED_USER, "").toString();
//
//        if (getIntent().getExtras() != null) {
//
//            bundle = getIntent().getExtras();
//            idSegnalazione = bundle.get("idSegnalazione").toString();
//
//            SharedPreferences.Editor editor = sharedPrefs.edit();
//            editor.putString("idSegnalazione", idSegnalazione);
//            editor.apply();
//
//        } else {
//
//
//            idSegnalazione = sharedPrefs.getString("idSegnalazione", "").toString();
//
//            bundle = new Bundle();
//            bundle.putString("idSegnalazione", idSegnalazione);
//
//        }
//
//        dataT = (TextView) findViewById(R.id.dataD);
//        segnalazioneT = (TextView) findViewById(R.id.segnalazioneD);
//        condominoT = (TextView) findViewById(R.id.condominoD);
//        //impiantoT = (TextView) findViewById(R.id.impiantoD);
//        fornitoreT = (TextView) findViewById(R.id.fornitoreD);
//        //statoT = (TextView) findViewById(R.id.statoD);
//        condominioT = (TextView) findViewById(R.id.condominioD);
//        btnChiama = (LinearLayout) findViewById(R.id.btnChiama);
//        descrizioneStatoT = (TextView) findViewById(R.id.descrizioneStatoD);
//        imageStatoI = (ImageView) findViewById(R.id.imageStatoD);
//
//        Query prova;
//        prova = FirebaseDB.getInterventi().orderByKey().equalTo(idSegnalazione);
//
//        prova.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
//
//                String temp = dataSnapshot.getKey().toString();
//
//                ticketInterventoMap = new HashMap<String, Object>();
//                ticketInterventoMap.put("id", dataSnapshot.getKey());
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    ticketInterventoMap.put(child.getKey(), child.getValue());
//                }
//
//                TicketIntervento ticketIntervento = new TicketIntervento(
//                        ticketInterventoMap.get("id").toString(),
//                        ticketInterventoMap.get("amministratore").toString(),
//                        ticketInterventoMap.get("data_ticket").toString(),
//                        ticketInterventoMap.get("data_ultimo_aggiornamento").toString(),
//                        ticketInterventoMap.get("fornitore").toString(),
//                        ticketInterventoMap.get("messaggio_condomino").toString(),
//                        ticketInterventoMap.get("aggiornamento_condomini").toString(),
//                        ticketInterventoMap.get("descrizione_condomini").toString(),
//                        ticketInterventoMap.get("oggetto").toString(),
//                        ticketInterventoMap.get("rapporti_intervento").toString(),
//                        ticketInterventoMap.get("richiesta").toString(),
//                        ticketInterventoMap.get("stabile").toString(),
//                        ticketInterventoMap.get("stato").toString() ,
//                        ticketInterventoMap.get("priorità").toString(),
//                        "ciao","ciao","ciao","ciao");//TODO
//
//                if(ticketIntervento.getStato().equals("A")){
//                    descrizioneStatoT.setText("Questa richiesta è in attesa di essere presa in carico");
//                    //   imageStatoI.setImageResource(R.drawable.sand_clock2);
//                }else if(ticketIntervento.getStato().equals("B") || stato.equals("C") || stato.equals("D")){
//                    descrizioneStatoT.setText("Questa richiesta è in corso d'opera");
//                    imageStatoI.setImageResource(R.drawable.wrench);
//                }else if(ticketIntervento.getStato().equals("E") || stato.equals("F")){
//                    descrizioneStatoT.setText("I lavori per questo intervento sono stati conclusi");
//                    imageStatoI.setImageResource(R.drawable.checked);
//                }else if(ticketIntervento.getStato().equals("G")){
//                    descrizioneStatoT.setText("Questa richiesta è stata rifiutata.\nContattare l'amministratore per maggiori dettagli");
//                    imageStatoI.setImageResource(R.drawable.error);
//                }
//
//                dataT.setText(ticketIntervento.getDataTicket());
//                segnalazioneT.setText(ticketIntervento.getOggetto());
//                condominioT.setText(ticketIntervento.getDataUltimoAggiornamento());
//                descrizioneStatoT.setText(ticketIntervento.getMessaggioCondomino());
//                /*
//                final SharedPreferences sharedPrefs = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPrefs.edit();
//                editor.putString("data", ticketIntervento.getDataTicket());
//                editor.putString("segnalazione", ticketIntervento.getOggetto());
//                editor.putString("condomino", usernameCondomino);
//                editor.putString("idCondominio", idCondominio);
//                editor.putString("fornitore", fornitore);
//                editor.putString("stato", stato);
//                editor.apply();
//                */
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//             }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//
//        });
//
//        btnChiama.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                DialogFragment newFragment = new DialogChiamaAmministratore();
//                bundle.putString("telefono",telefonoAmministratore);
//                newFragment.setArguments(bundle);
//                newFragment.show(getFragmentManager(), "ChiamaAmministratore");
//                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
//            }
//        });
//
//
//    }
//

}