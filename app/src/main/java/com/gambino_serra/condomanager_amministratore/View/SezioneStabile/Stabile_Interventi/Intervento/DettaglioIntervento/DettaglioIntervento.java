package com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.DettaglioIntervento;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.TicketIntervento;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.Dialog.DialogVisualizzaFotoRapporto;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.CambiaFornitoreIntervento.CategoriaCambiaFornitore;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.Stabile_Interventi.Intervento.RapportiIntervento.DialogAggiornamentoCondomini;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;

public class DettaglioIntervento extends android.support.v4.app.Fragment {
    private static final String MY_PREFERENCES = "preferences";
    private static final String LOGGED_USER = "username";

    String idIntervento = "";
    String urlFoto = "-";

    TextView Tstato;
    TextView TidTicketIntervento;
    TextView Tfornitore;
    TextView Tazienda;
    TextView TdataTicket;
    TextView Toggetto;
    TextView Trichiesta;
    TextView Tstabile;
    TextView Tindirizzo;
    TextView Tcategoria;
    TextView Tdescrizione;
    TextView Taggiornamento;
    ImageView Tfoto;
    ImageView ChiamaAmministratore;
    ImageView mLogoStato;
    TextView mStato;
    ConstraintLayout cambiaFornitore;
    ImageButton insAggiornamento;
    ImageButton visualizzaFoto;

    CardView Forn1;
    CardView Forn2;
    CardView Forn3;
    ImageView hsForn;

    CardView Stab1;
    CardView Stab2;
    CardView Stab3;
    ImageView hsStab;

    private Firebase firebaseDB;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    Map<String, Object> ticketInterventoMap;

    Bundle bundle;

    // Animazioni
    private Animation animShow, animHide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dettaglio_intervento2, container, false);
        return view;
        }


    private void initAnimation()
    {
        animShow = AnimationUtils.loadAnimation( getActivity(), R.anim.view_show); //view.startAnimation( animShow );
        animHide = AnimationUtils.loadAnimation( getActivity(), R.anim.view_hide); //view.startAnimation( animHide );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth = FirebaseAuth.getInstance();
        final SharedPreferences sharedPrefs = getActivity().getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        idIntervento = sharedPrefs.getString("idIntervento", "").toString();
        bundle = new Bundle();
        bundle.putString("idIntervento", idIntervento);

        // Avvaloro i nuovi riferimenti al layout
        Tstato = (TextView) getActivity().findViewById(R.id.D_Stato);
        TdataTicket = (TextView) getActivity().findViewById(R.id.D_Data);
        Toggetto = (TextView) getActivity().findViewById(R.id.D_Oggetto);
        Tazienda = (TextView) getActivity().findViewById(R.id.D_Azienda);
        Tfornitore = (TextView) getActivity().findViewById(R.id.D_Fornitore);
        Tcategoria = (TextView) getActivity().findViewById(R.id.D_Categoria);
        Trichiesta = (TextView) getActivity().findViewById(R.id.D_Richiesta);
        Tfoto = (ImageView) getActivity().findViewById(R.id.D_Foto);
        Tstabile = (TextView) getActivity().findViewById(R.id.D_Stabile);
        Tindirizzo = (TextView) getActivity().findViewById(R.id.D_Indirizzo);
        Tdescrizione = (TextView) getActivity().findViewById(R.id.D_Descrizione);
        Taggiornamento = (TextView) getActivity().findViewById(R.id.D_Aggiornamento);
        insAggiornamento = (ImageButton) getActivity().findViewById(R.id.editButton);
        visualizzaFoto = (ImageButton) getActivity().findViewById(R.id.btnVisualizzaFotoRichiesta);


        Forn1 = (CardView) getActivity().findViewById(R.id.cardView2);
        Forn2 = (CardView) getActivity().findViewById(R.id.cardView29);
        Forn3 = (CardView) getActivity().findViewById(R.id.cardView25);
        hsForn = (ImageView) getActivity().findViewById(R.id.up_down_F);

        Stab1 = (CardView) getActivity().findViewById(R.id.cardView32);
        Stab2 = (CardView) getActivity().findViewById(R.id.cardView9);
        Stab3 = (CardView) getActivity().findViewById(R.id.cardView5);
        hsStab = (ImageView) getActivity().findViewById(R.id.up_down_S);


        TidTicketIntervento = (TextView) getActivity().findViewById(R.id.Hidden_ID);
        ChiamaAmministratore = (ImageView) getActivity().findViewById(R.id.imageViewChiamaFornitore);
        mLogoStato = (ImageView) getActivity().findViewById(R.id.D_LogoStato);
        mStato = (TextView) getActivity().findViewById(R.id.D_Stato);
        cambiaFornitore = (ConstraintLayout) getActivity().findViewById(R.id.btnCambiaFornitore);
        cambiaFornitore.setVisibility(View.GONE);
        cambiaFornitore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoriaCambiaFornitore.class);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("foto", "-");  // se effettua la creazione di un ticket da zero, non potrà allegare foto del condomino
                editor.apply();

                Log.d("Ciao", " " + bundle.getString("idInterventoRifiutato"));

                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        insAggiornamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("idIntervento", idIntervento);
                DialogFragment newFragment = new DialogAggiornamentoCondomini();
                newFragment.setArguments(bundle);
                newFragment.show(getActivity().getFragmentManager(), "AggiornamentoCondomini");
                getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });

        ticketInterventoMap = new HashMap<String, Object>();
        // Avvalora il primo oggetto del map con l'ID dell'intervento recuperato
        ticketInterventoMap.put("idIntervento", idIntervento);


        ChiamaAmministratore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String telefono = "1234567890";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefono));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                }
            });



        Forn1.setVisibility(View.GONE);
        Forn2.setVisibility(View.GONE);
        Forn3.setVisibility(View.GONE);
        Stab1.setVisibility(View.GONE);
        Stab2.setVisibility(View.GONE);
        Stab3.setVisibility(View.GONE);

        initAnimation();

        hsForn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Forn1.isShown() )
                {
                    //Forn1.startAnimation( animHide );
                    Forn1.setVisibility(View.GONE);
                    //Forn2.startAnimation( animHide );
                    Forn2.setVisibility(View.GONE);
                    //Forn3.startAnimation( animHide );
                    Forn3.setVisibility(View.GONE);
                    hsForn.setImageDrawable(getResources().getDrawable(R.drawable.down));
                }else{
                    Forn1.setVisibility(View.VISIBLE);
                    //Forn1.startAnimation( animShow );
                    Forn2.setVisibility(View.VISIBLE);
                    //Forn2.startAnimation( animShow );
                    Forn3.setVisibility(View.VISIBLE);
                    //Forn3.startAnimation( animShow );
                    hsForn.setImageDrawable(getResources().getDrawable(R.drawable.up));
                }
            }
        });

        hsStab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Stab1.isShown() )
                {
                    //Stab1.startAnimation( animHide );
                    Stab1.setVisibility(View.GONE);
                    //Stab2.startAnimation( animHide );
                    Stab2.setVisibility(View.GONE);
                    //Stab3.startAnimation( animHide );
                    Stab3.setVisibility(View.GONE);
                    hsStab.setImageDrawable(getResources().getDrawable(R.drawable.down));
                }else{
                    Stab1.setVisibility(View.VISIBLE);
                    //Stab1.startAnimation( animShow );
                    Stab2.setVisibility(View.VISIBLE);
                    //Stab2.startAnimation( animShow );
                    Stab3.setVisibility(View.VISIBLE);
                    //Stab3.startAnimation( animShow );
                    hsStab.setImageDrawable(getResources().getDrawable(R.drawable.up));
                }
            }
        });


        visualizzaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto", urlFoto);

                DialogVisualizzaFotoRapporto newFragment = new DialogVisualizzaFotoRapporto();
                newFragment.setArguments(bundle);
                newFragment.show(getActivity().getFragmentManager(), "DialogVisualizzaFotoRichiesta");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        Query intervento;
        intervento = FirebaseDB.getInterventi().orderByKey().equalTo(idIntervento);

        intervento.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot child : dataSnapshot.getChildren()){
                    ticketInterventoMap.put(child.getKey(),child.getValue());
                    }

                recuperaDettagliTicket(ticketInterventoMap);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }




    public void recuperaDettagliTicket(final Map<String, Object> ticketInterventoMap) {

        final Map<String, Object> ticketInterventoMap2 = new HashMap<String, Object>();
        final Map<String, Object> ticketInterventoMap3 = new HashMap<String, Object>();

        Query query2;
        query2 = FirebaseDB.getStabili().orderByKey().equalTo( ticketInterventoMap.get("stabile").toString());

        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //HashMap temporaneo per immagazzinare i dati dello stabile per ognuno dei figli presenti nello snapshot,
                // ovvero per tutti i figli di un singolo nodo Stabile recuperiamo i dati per inserirli nel MAP
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ticketInterventoMap2.put(child.getKey(), child.getValue());
                    }


                Query query3;
                query3 = FirebaseDB.getFornitori().orderByKey().equalTo( ticketInterventoMap.get("fornitore").toString() );
                query3.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            ticketInterventoMap3.put(child.getKey(), child.getValue());
                        }

                        // Avvaloro tutti i dati della card che mi interessano inserendone i relativi dati
                        // anche quelli provenienti dallo stabile sovrascrivendo i codici passati in ticketIntervento
                        // Avvaloriamo una variabile TicketIntervento appositamente creata in modo da inserire poi questo
                        // oggetto all'interno di un Array di interventi che utilizzeremo per popolare la lista Recycle
                        TicketIntervento ticketIntervento = new TicketIntervento(
                                ticketInterventoMap.get("idIntervento").toString(),
                                ticketInterventoMap.get("amministratore").toString(),
                                ticketInterventoMap.get("data_ticket").toString(),
                                ticketInterventoMap.get("data_ultimo_aggiornamento").toString(),
                                ticketInterventoMap.get("fornitore").toString(),
                                ticketInterventoMap.get("aggiornamento_condomini").toString(),
                                ticketInterventoMap.get("descrizione_condomini").toString(),
                                ticketInterventoMap.get("oggetto").toString(),
                                ticketInterventoMap.get("richiesta").toString(),
                                ticketInterventoMap.get("stabile").toString(),
                                ticketInterventoMap.get("stato").toString(),
                                ticketInterventoMap.get("priorità").toString(),
                                ticketInterventoMap.get("foto").toString(),
                                ticketInterventoMap3.get("categoria").toString(),   // FORNITORE
                                ticketInterventoMap3.get("nome").toString(),        // FORNITORE
                                ticketInterventoMap3.get("nome_azienda").toString(),// FORNITORE
                                ticketInterventoMap2.get("nome").toString(),        // STABILE
                                ticketInterventoMap2.get("indirizzo").toString()    // STABILE
                        );

                        try
                        {
                            Tstato.setText(ticketIntervento.getStato().toString());
                            TdataTicket.setText(ticketIntervento.getDataTicket().toString());
                            Toggetto.setText(ticketIntervento.getOggetto().toString());
                            Tazienda.setText( ticketIntervento.getNomeAziendaFornitore().toString());
                            Tfornitore.setText(ticketIntervento.getNomeFornitore().toString());
                            Tcategoria.setText(ticketIntervento.getCategoria().toString());
                            Trichiesta.setText(ticketIntervento.getRichiesta().toString());
                            Tstabile.setText(ticketIntervento.getNomeStabile().toString());
                            Tindirizzo.setText(ticketIntervento.getIndirizzoStabile().toString());
                            Tdescrizione.setText(ticketIntervento.getDescrizioneCondomini().toString());
                            Taggiornamento.setText(ticketIntervento.getAggiornamentoCondomini().toString());
                            TidTicketIntervento.setText(ticketIntervento.getIdTicketIntervento().toString());

                            //Setta bundle per cambio fornitore
                            bundle.putString("idInterventoRifiutato", ticketIntervento.getIdTicketIntervento());
                            bundle.putString("idStabile",ticketIntervento.getStabile());
                            bundle.putString("foto", ticketIntervento.getFoto());

                            if ( ticketIntervento.getFoto().equals( "-") )
                            {   visualizzaFoto.setVisibility(View.GONE);
                            }else{
                                urlFoto = ticketIntervento.getFoto();
                            }


                            if(ticketIntervento.getStato().equals("rifiutato")){
                                cambiaFornitore.setVisibility(View.VISIBLE);
                            }

//                            if ( ! "-".equals( ticketIntervento.getFoto() ) )
//                            {
//                                Picasso.with(getActivity().getApplicationContext()).load(ticketIntervento.getFoto()).fit().centerCrop().into(Tfoto);
//                            }
//                            else
//                            {
//                                Tfoto.setVisibility(View.GONE);
//                            }


                            String stato = ticketIntervento.getStato();

                            switch(stato) {
                                // intervento richiesto o rifiutato (al condomino interressa solo che sia stato processato
                                // dall'amministratore, se un fornitore lo rifiuterà, lui lo vedrà ancora in attesa
                                // di essere preso in carico
                                case "in attesa" :
                                {
                                    mLogoStato.setImageResource(R.drawable.in_attesa3);
                                    mStato.setText("Intervento richiesto");
                                    break;
                                }

                                case "in corso": // intervento in corso
                                {
                                    mLogoStato.setImageResource(R.drawable.inwork2);
                                    mStato.setText("Intervento in corso");
                                    break;
                                }
                                case "completato":   // intervento concluso
                                {
                                    mLogoStato.setImageResource(R.drawable.interv_complet);
                                    mStato.setText("Intervento completato");
                                    break;
                                }
                                case "rifiutato" :
                                {
                                    mLogoStato.setImageResource(R.drawable.rifiutato);
                                    mStato.setText("Intervento rifiutato");
                                    break;
                                }
                                default:
                            }


                        }
                        catch (NullPointerException e) {}

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }

}