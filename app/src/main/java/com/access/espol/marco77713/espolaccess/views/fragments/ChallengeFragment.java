package com.access.espol.marco77713.espolaccess.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.adapter.PositionAdapter;
import com.access.espol.marco77713.espolaccess.adapter.SearcherAdapter;
import com.access.espol.marco77713.espolaccess.model.BuildRow;
import com.access.espol.marco77713.espolaccess.model.Posicion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {


    private List<Posicion> posicionsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PositionAdapter pAdapter;


    public ChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_challenge, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.positions);

        pAdapter = new PositionAdapter(posicionsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        preparePositionsData();

        return view;
    }

    private void preparePositionsData() {

        Posicion posicion = new Posicion(1, "Mercedes Baque", 45);
        posicionsList.add(posicion);

        posicion = new Posicion(1, "Marco Garofalo", 95);
        posicionsList.add(posicion);


    }

}
