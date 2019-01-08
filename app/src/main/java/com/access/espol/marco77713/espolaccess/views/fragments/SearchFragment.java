package com.access.espol.marco77713.espolaccess.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class SearchFragment extends Fragment  implements SearchView.OnQueryTextListener{


    private List<BuildRow> buildRowsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearcherAdapter sAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.builds);

        sAdapter = new SearcherAdapter(buildRowsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);

        prepareBuilsData();

        return view;
    }

    private void prepareBuilsData() {
        BuildRow buildRow = new BuildRow("EDCOM", "Espol - Prosperina");
        buildRowsList.add(buildRow);

        buildRow = new BuildRow("Biblioteca", "Espol - Prosperina");
        buildRowsList.add(buildRow);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                Toast.makeText(getActivity(), "Action View Expanded...", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getActivity(), "Collapse View Expanded...", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        menuItem.setOnActionExpandListener(onActionExpandListener);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        String userInput = newText.toLowerCase();
        List<BuildRow> newList = new ArrayList<>();

        for (BuildRow builRow : buildRowsList)
        {
            if(builRow.getNombreEdificio().toLowerCase().contains(userInput))
            {
                newList.add(builRow);
            }
        }

        System.out.println(buildRowsList);
        sAdapter.updateList(newList);

        return true;
    }

}
