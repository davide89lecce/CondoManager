package com.gambino_serra.condomanager_amministratore.View.Home.BachecaStabili;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.gambino_serra.condomanager_amministratore.Model.Entity.MarkerStabile;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.MainDrawer;
import com.gambino_serra.condomanager_amministratore.View.SezioneStabile.SezioneStabile;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * La classe gestisce l'invio della richiesta, della relativa posizione e la scelta dell'Helper al quale inviare la richiesta di coda,
 * mediante l'uso delle mappe e l'utilizzo delle GoogleApiClient.
 */
@RuntimePermissions
public class MappaStabili extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, OnMapReadyCallback, GoogleMap.OnMapClickListener{

    static final int TIME_DIALOG_ID1 = 1;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    final private static String MY_PREFERENCES = "kiuPreferences";
    final private static String EMAIL = "email";
    final Bundle bundle = new Bundle();
    ImageView closeButton;
    View view;
    StringBuilder ora_richiesta;
    LatLng ltln;
    String address;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */
    private int ora;
    private int minuti;

    private FirebaseAuth firebaseAuth;
    private String uidAmministratore;
    Map<String, Object> stabileMap;
    ArrayList<MarkerStabile> stabili;
    private ArrayList<MarkerStabile> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mappa_stabili);

        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<MarkerStabile>();
        stabileMap = new HashMap<String,Object>();
        stabili = new ArrayList<MarkerStabile>();

        closeButton = (ImageView) findViewById(R.id.closeButton2);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.MappaInterventi);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Errore caricamento mappe", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Il metodo permette di caricare le mappe di Google.
     */
    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            MappaStabiliPermissionsDispatcher.getMyLocationWithCheck(this);
            map.setOnMapClickListener(this);
            }
        else
            {
            Toast.makeText(this, "Errore caricamento mappe", Toast.LENGTH_SHORT).show();
            }
    }

    @SuppressWarnings("all")
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        if (map != null) {
            //Adesso che la mappa e' caricata puo' ricevere la psosizione
            map.setMyLocationEnabled(true);
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            connectClient();
        }
    }

    /**
     * Il metodo permette la connessione al Client dei servizi di Google delle mappe.
     */
    protected void connectClient() {
        // Connette il Client
        if (isGooglePlayServicesAvailable() && mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Il metodo e' chiamato quando l'Activity torna visibile (foreground).
     */
    @Override
    protected void onStart() {
        super.onStart();
        connectClient();
    }

    /*
     * Il metodo e' chiamato quando l'Activity perde la visibilita'.
     */
    @Override
    protected void onStop() {
        // Disconnette il Client
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    /**
     * Il metodo gestisce i risultati ritornati dal FragmentActivity dei Google Play services.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Decide cosa fare in base al codice di richiesta originale.
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
                //Se il risultato del codice e' Activity.RESULT_OK, prova a riconnettersi.
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mGoogleApiClient.connect();
                        break;
                }
        }
    }

    /**
     * Il metodo verifica che i servizi di Google siano disponibili, in caso contrario una NuovoAvviso viene visualizzata al'utente.
     */
    private boolean isGooglePlayServicesAvailable() {

        // Verifica disponibilita' dei servizi di Google
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // Se Google Play services sono disponibili
        if (ConnectionResult.SUCCESS == resultCode) {
            Log.d("Location Updates", "Google Play services is available.");
            return true;
            }
        else {
            // Ricevo la Error NuovoAvviso dai servizi Google Play.
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // Se Google Play services puo' fornire una Error NuovoAvviso
            if (errorDialog != null) {
                // Creazione di un DialogFragment
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getFragmentManager(), "Location Updates");
                }
            return false;
        }
    }

    /**
     * Il metodo viene invocato dal Location Services quando la richiesta di connessione al client
     * e' avvenuta con successo. In questo momento si puo' richiedere la posizione corrente.
     */
    @Override
    public void onConnected(Bundle dataBundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
            }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null)
            {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
            }
        else
            {
            Toast.makeText(this, "Abilita GPS", Toast.LENGTH_SHORT).show();
            }
        startLocationUpdates();
    }

    /**
     * Il metodo permette di aggiornare la posizione.
     */
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
            }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public void onLocationChanged(Location location) { }

    /**
     * Il metodo e' invocato dal Location Services se la connessione con il client si interrrompe a causa di un errore.
     */
    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconesso", Toast.LENGTH_SHORT).show();
            }
        else if (i == CAUSE_NETWORK_LOST)
            {
            Toast.makeText(this, "Rete dati assente", Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * Il metodo viene invocato dal Location Services se lo stesso servizio fallisce
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Se il problema di connessione e' risolvibile (da Google) allora viene
        // inviato un Intent all'Activity predisposta a risolvere il problema.
        if (connectionResult.hasResolution()) {
            try
                {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                }
            catch (IntentSender.SendIntentException e)//L'eccezione e' sollevata nel caso in cui l'Intent viene eliminato.
                {
                e.printStackTrace();  // Log the error
                }
        }
        else
            {
            Toast.makeText(getApplicationContext(), "Servizio GPS non disponibile", Toast.LENGTH_LONG).show();
            }
    }

    /**
     * Il metodo permette di caricare e visualizzare la mappa nella UI dell'applicazione e le sue componenti invocando il metodo loadMap(map).
     */
    @Override
    public void onMapReady(GoogleMap map) {

        loadMap(map);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent setting = new Intent(MappaStabili.this, MainDrawer.class);
                startActivity(setting);
                }
        });

        map.clear();

        //Caricamento interventi su mappa

        uidAmministratore = firebaseAuth.getCurrentUser().getUid().toString();
        Query query;
        query = FirebaseDB.getStabili().orderByChild("amministratore").equalTo(uidAmministratore);

        // la query seleziona solo gli interventi con un determinato fornitore
        //il listener lavora sui figli della query, ovvero su titti gli interventi recuperati
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //HashMap temporaneo per immagazzinare i dati di un ticket
                stabileMap = new HashMap<String, Object>();
                stabileMap.put("id", dataSnapshot.getKey()); //primo campo del MAP

                // per ognuno dei figli presenti nello snapshot, ovvero per tutti i figli di un singolo nodo Interv
                // recuperiamo i dati per inserirli nel MAP
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    stabileMap.put(child.getKey(), child.getValue());
                }

                visualizzaMarkers(stabileMap);
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

        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Seleziona il marker sulla mappa", Snackbar.LENGTH_LONG);
        View view1 = snack.getView();
        TextView tv = (TextView) view1.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();
    }

    /**
     * Il metodo permete di posizionare il marker (non utilizzato)
     */
    @Override
    public void onMapClick(LatLng latLng) {}


    public void visualizzaMarkers(final Map<String, Object> stabileMap){

                // Avvaloriamo una variabile TicketIntervento appositamente creata in modo da inserire poi questo
                // oggetto all'interno di un Array di interventi che utilizzeremo per popolare la lista Recycle
                try {

                    MarkerStabile markerStabile = new MarkerStabile(
                            stabileMap.get("id").toString(),
                            stabileMap.get("nome").toString(),
                            stabileMap.get("indirizzo").toString(),
                            stabileMap.get("latitudine").toString(),
                            stabileMap.get("longitudine").toString()
                    );

                        // inserisce l'oggetto stabile nell'array stabili
                        stabili.add(markerStabile);

                        //codice latitudine longitudine
                        LatLng ltlnHelpers;
                        ltlnHelpers = new LatLng(Double.parseDouble(markerStabile.getLatitudine()), Double.parseDouble(markerStabile.getLongitudine()));

                        //Aggiunge il marker con il colore in base alla priorità

                            map.addMarker(new MarkerOptions()
                                    .position(ltlnHelpers)
                                    .title(markerStabile.getNomeStabile())
                                    .snippet(markerStabile.getIndirizzo())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        //Setta l'onclick sul marker e intent a InterventoInCorso
                        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker arg0) {
                                for (final MarkerStabile stabile : stabili) {
                                    if ((stabile.getNomeStabile()).equals(arg0.getTitle())) {
                                        Intent intent = new Intent(getApplicationContext(), SezioneStabile.class);
                                        bundle.putString("idStabile", stabile.getIdStabile());
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(intent);
                                    }
                                }
                            }
                        });


                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Non riesco ad aprire l'oggetto " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }

    /**
     * Il metodo gestisce la comunicazione, tramite NuovoAvviso, degli errori che possono verificarsi.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        private Dialog mDialog;

        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

}