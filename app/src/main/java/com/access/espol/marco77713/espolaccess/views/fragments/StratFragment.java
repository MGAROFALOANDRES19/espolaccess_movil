package com.access.espol.marco77713.espolaccess.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.access.espol.marco77713.espolaccess.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StratFragment extends Fragment {

    Button btnIniciar;

    public StratFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_strat, container, false);
    }

}
