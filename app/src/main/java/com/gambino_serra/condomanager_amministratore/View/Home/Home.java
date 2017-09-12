package com.gambino_serra.condomanager_amministratore.View.Home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gambino_serra.condomanager_amministratore.View.Home.BachecaNotifiche.BachecaInterventiCompletati.BachecaInterventiCompletati;
import com.gambino_serra.condomanager_amministratore.View.Home.InterventiInCorso.BachecaInterventiInCorso.BachecaInterventiInCorso;
import com.gambino_serra.condomanager_amministratore.View.Home.BachecaStabili.BachecaStabili;
import com.gambino_serra.condomanager_amministratore.tesi.R;

public class Home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private BottomNavigationView bottomNavigationView;

    private OnFragmentInteractionListener mListener;

    public Home() { }

    /**
     * Use this factory method to create a new instance of this Menu using the provided parameters.
     */
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_navigationbar, container, false);
        }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = BachecaStabili.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                break;
                            case R.id.action_item2:
                                selectedFragment = BachecaInterventiInCorso.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                break;
                            case R.id.action_item3:
                                selectedFragment = BachecaInterventiCompletati.newInstance();
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                break;
                            }
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BachecaInterventiInCorso.newInstance());
        transaction.commit();

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            }
        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        }

    /**
     * This interface must be implemented by activities that contain this Menu to allow an interaction in this Menu
     * to be communicated to the activity and potentially other fragments contained in that activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
