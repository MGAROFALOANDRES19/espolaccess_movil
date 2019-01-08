package com.access.espol.marco77713.espolaccess.views;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.views.fragments.ChallengeFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.MapFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.ProfileFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import static com.access.espol.marco77713.espolaccess.R.layout.info_popup;

public class ContainerActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    Dialog myDialog;
    private String userID;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.vector_86, R.drawable.vector_87, R.drawable.vector_88, R.drawable.vector_89};


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

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.maps);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.challenge:
                        ChallengeFragment challengeFragment = new ChallengeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, challengeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.maps:
                        MapFragment mapFragment = new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        showToolbar("", true, R.drawable.vector_66, R.drawable.btn_rounded);
                        break;
                    case R.id.search:
                        SearchFragment searchFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment(mAuth);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                }
            }
        });


        /*    <include layout="@layout/bottom_bar"/>*/

        myDialog = new Dialog(this);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void showPopUp() {
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
        this.getSupportActionBar().setHomeAsUpIndicator(drawable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        /*quizas hacer toda la imagen toolbar*/


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_favorite:
                showPopUp();
                break;
            // action with ID action_settings was selected
        }
    return  true;
    }*/

}