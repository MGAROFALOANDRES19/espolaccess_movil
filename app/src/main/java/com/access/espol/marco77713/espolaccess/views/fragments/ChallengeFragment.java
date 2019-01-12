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
import com.access.espol.marco77713.espolaccess.model.Edificio;
import com.access.espol.marco77713.espolaccess.model.Posicion;
import com.access.espol.marco77713.espolaccess.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {


    private List<Posicion> posicionsList = new ArrayList<>();
    private List<User> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PositionAdapter pAdapter;

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


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    usersList.add(user);
                    System.out.println("Value is: " + user);
                }

                prepareUsersData();

                pAdapter = new PositionAdapter(posicionsList);
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

        return view;
    }


    private void prepareUsersData() {
        for(User user:usersList){
            posicionsList.add(new Posicion(user.getLugar(), user.getEmail(), user.getPuntos()));
        }
    }

}
