package com.access.espol.marco77713.espolaccess.views.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.adapter.PositionAdapter;
import com.access.espol.marco77713.espolaccess.adapter.SearcherAdapter;
import com.access.espol.marco77713.espolaccess.model.BuildRow;
import com.access.espol.marco77713.espolaccess.model.Edificio;
import com.access.espol.marco77713.espolaccess.model.Posicion;
import com.access.espol.marco77713.espolaccess.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {


    private List<Posicion> posicionsList = new ArrayList<>();
    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PositionAdapter pAdapter;

    RelativeLayout relativeLayout;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");


    public ChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_challenge, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.positions);

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    posicionsList.clear();
                    usersList.clear();
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        User user = snap.getValue(User.class);
                        usersList.add(user);
                        System.out.println("Value is: " + user);
                    }
                    Collections.sort(usersList);

                    prepareUsersData();

                    pAdapter = new PositionAdapter(posicionsList, getContext(), usersList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(pAdapter);

                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println("Failed to read value." + error.toException());
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "No tienes acceso a internet, lo necesitas para hacer uso de la app", Toast.LENGTH_LONG).show();
        }

        return view;
    }


    private void prepareUsersData() {
        int i = 1;
        for(User user:usersList){
            posicionsList.add(new Posicion(i, user.getEmail(), user.getPuntos()));
            i++;
        }
    }

}
