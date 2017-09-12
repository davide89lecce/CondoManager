package com.gambino_serra.condomanager_amministratore.View.DrawerMenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gambino_serra.condomanager_amministratore.Model.FirebaseDB.FirebaseDB;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.GestioneCondomini.GestioneCondomini;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.InformazioniPersonali.InformazioniPersonali;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.StoricoInterventi.BachecaInterventiArchiviati;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.GestioneCondomini.GestioneCondomini;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.GestioneFornitori.GestioneFornitori;
import com.gambino_serra.condomanager_amministratore.View.DrawerMenu.Menu.GestioneStabili.GestioneStabili;
import com.gambino_serra.condomanager_amministratore.View.Home.Home;
import com.gambino_serra.condomanager_amministratore.tesi.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainDrawer extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FirebaseAuth firebaseAuth;
    private Firebase firebase;
    private String nome;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_INFO = "info_personali";
    private static final String TAG_STORICO_INTERVENTI = "storico_interventi";
    private static final String TAG_GESTIONE_CONDOMINI = "gestione_condomini";
    private static final String TAG_GESTIONE_FORNITORI = "gestione_fornitori";
    private static final String TAG_GESTIONE_STABILI = "gestione_stabili";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home Menu when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        String UID  = firebaseAuth.getCurrentUser().getUid();

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        FirebaseDB.getAmministratori().child(UID).child("nome").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nome = dataSnapshot.getValue(String.class);
                }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
            }
    }

    /***
     * Returns respected Menu that user selected from navigation menu
     */
    private void loadHomeFragment() {

        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
            }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the activity_main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                }
            };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
            }

        //Closing drawer on item click
        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                Home homeFragment = new Home();
                return homeFragment;
            case 1:
                // info personali
                InformazioniPersonali infoFragment = new InformazioniPersonali();
                return infoFragment;
            case 2:
                // storico inteventi
                BachecaInterventiArchiviati interventiFragment = new BachecaInterventiArchiviati();
                return interventiFragment;
            case 3:
                // gestione condomini
                GestioneCondomini GestioneCondominiFragment = new GestioneCondomini();
                return GestioneCondominiFragment;
            case 4:
                // gestione fornitori
                GestioneFornitori GestioneFornitoriFragment = new GestioneFornitori();
                return GestioneFornitoriFragment;
            case 5:
                // gestione stabili
                GestioneStabili GestioneStabiliFragment = new GestioneStabili();
                return GestioneStabiliFragment;
            default:
                return new Home();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the activity_main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        navigationView.getMenu().getItem(0).setChecked(true);
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_info_personali:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_INFO;
                        break;
                    case R.id.nav_storico_interventi:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_STORICO_INTERVENTI;
                        break;
                    case R.id.nav_gestione_stabili:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_GESTIONE_STABILI;
                        break;
                    case R.id.nav_gestione_condomini:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_GESTIONE_CONDOMINI;
                        break;
                    case R.id.nav_gestione_fornitori:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_GESTIONE_FORNITORI;
                        break;
                    case R.id.nav_logout:
                        drawer.closeDrawers();
                        firebaseAuth.signOut();
                        startActivity(new Intent(MainDrawer.this, com.gambino_serra.condomanager_amministratore.View.Login.LoginActivity.class));
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked())
                    { menuItem.setChecked(false); }
                else
                    { menuItem.setChecked(true); }

                menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
            }

        // This code loads home Menu when back key is pressed when user is in other Menu than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
                }
        }
        super.onBackPressed();
    }
}
