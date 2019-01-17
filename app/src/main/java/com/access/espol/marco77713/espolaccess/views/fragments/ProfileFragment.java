package com.access.espol.marco77713.espolaccess.views.fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    private TextView email;
    private RadioGroup personalizacion;
    public  User userClass;

    RadioButton r1, r2, r3;

    public ProfileFragment(FirebaseAuth mAuth) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        email = (TextView) view.findViewById(R.id.email);
        personalizacion = (RadioGroup) view.findViewById(R.id.personalizacion);
        final Button button = (Button) view.findViewById(R.id.signOut);
        r1 = (RadioButton) view.findViewById(R.id.r1);
        r2 = (RadioButton) view.findViewById(R.id.r2);
        r3 = (RadioButton) view.findViewById(R.id.r3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setEnabled(false);
                button.setTextColor(Color.parseColor("#7f7b6d"));
                button.setBackground(getResources().getDrawable(R.drawable.btn_rounded_pushed));

                mAuth.signOut();
            }
        });

        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    System.out.println("onAuthStateChanged:signed_in:" + user.getUid());
                    System.out.println("Successfully signed in with: " + user.getEmail());
                    email.setText(user.getEmail());
                    switch (userClass.getPersonalizacion()){
                        case (0):
                            r1.setChecked(true);
                            break;

                        case (1):
                            r2.setChecked(true);
                            break;

                        case (2):
                            r3.setChecked(true);
                            break;

                    }

                } else {
                    // User is signed out
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                System.out.println("onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });

        final Button btnSave = (Button) view.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSave.setEnabled(false);
                btnSave.setTextColor(Color.parseColor("#7f7b6d"));
                btnSave.setBackground(getResources().getDrawable(R.drawable.btn_rounded_pushed));
                System.out.println("onClick: Submit pressed.");
                String mail = email.getText().toString();
                int personalization = 2;

                if(r1.isChecked()){
                    personalization = 0;
                }if(r2.isChecked()){
                    personalization = 1;
                }if(r3.isChecked()){
                    personalization = 2;
                }

                System.out.println("onClick: Attempting to submit to database: \n" +
                        "name: " + personalization + "\n" +
                        "email: " + email + "\n"
                );

                //handle the exception if the EditText fields are null
                if(!mail.equals("") && !personalizacion.equals("")){
                    userClass.setEmail(mail);
                    userClass.setPersonalizacion(personalization);
                    System.out.println(userClass);
                    myRef.child("users").child(userID).setValue(userClass);
                    toastMessage("New Information has been saved.");
                    email.setText("");

                }else{
                    toastMessage("Fill out all the fields");
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
