package com.access.espol.marco77713.espolaccess.views;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.User;
import com.access.espol.marco77713.espolaccess.views.fragments.ChallengeFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.MapFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.MapsActivity;
import com.access.espol.marco77713.espolaccess.views.fragments.ProfileFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import static com.access.espol.marco77713.espolaccess.R.layout.info_popup;

public class ContainerActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    BottomBar bottomBar;
    Dialog myDialog;

    private String userID;
    User user;

    //PARA EL POPUP IMAGENES
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.vector_86, R.drawable.vector_87, R.drawable.vector_88, R.drawable.vector_89};

    ChallengeFragment challengeFragment = new ChallengeFragment();
    MapFragment mapFragment = new MapFragment();
    ProfileFragment profileFragment = new ProfileFragment(mAuth);
    SearchFragment searchFragment = new SearchFragment();
    MapsActivity mapsActivity = new MapsActivity();

    @Override
    protected void onStart() {
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
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);


        this.getDatabasesInstances();
        this.setViews();


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.challenge:
                        //changeFragment(challengeFragment);

                        break;
                    case R.id.maps:
                        //getPuntos();
                        //MapsActivity mapsActivity = new MapsActivity();
                        //changeFragment(mapsActivity);

                        break;
                    case R.id.search:
                        changeFragment(searchFragment);
                        break;
                    case R.id.profile:
                        changeFragment(profileFragment);
                        break;
                }
            }

        });


        }



    private void changeFragment(Fragment challengeFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, challengeFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

    }

    private void getPuntos() {

       myRef = mFirebaseDatabase.getReference("users/" + String.valueOf(mAuth.getCurrentUser().getUid()));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                System.out.println("QUE PASO " + dataSnapshot);
                user = dataSnapshot.getValue(User.class);
                System.out.println(""+user.getEdificios_evaluados());
                mapFragment.edificios_evaluados = user.getEdificios_evaluados();
                changeFragment(mapFragment);
                switch (user.getPuntos()){
                    case 1:
                        showToolbar("", true, R.drawable.btn_rounded, R.drawable.btn_rounded);
                        break;
                    case 2:
                        showToolbar("", true, R.drawable.btn_rounded, R.drawable.btn_rounded);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }


        });


    }

    private void setViews() {
        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.maps);
        myDialog = new Dialog(this);
    }

    private void getDatabasesInstances() {
        //BASE DE DATOS
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(ContainerActivity.this, MainActivity.class));
                }
            }
        };
    }

    public void showPopUp() {

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };

        TextView txtClose;
        myDialog.setContentView(info_popup);
        txtClose = (TextView) myDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        myDialog.show();

        carouselView = (CarouselView) myDialog.findViewById(R.id.carouselviewPopUp);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
    }

    public void showToolbar(String title, boolean upButton, int drawable, int backDrawable) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, backDrawable));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        this.getSupportActionBar().setHomeAsUpIndicator(drawable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }

}