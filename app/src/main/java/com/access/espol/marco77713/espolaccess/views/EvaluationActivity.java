package com.access.espol.marco77713.espolaccess.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Pregunta;
import com.access.espol.marco77713.espolaccess.model.Validacion;
import com.access.espol.marco77713.espolaccess.views.fragments.MapsActivity;
import com.access.espol.marco77713.espolaccess.views.fragments.QuestionFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.StratFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.WinShareFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationActivity extends AppCompatActivity {

    Button btnPrev, btnNext;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef, myRefValidations;

    DatabaseReference myRef2 = database.getReference();

    List<QuestionFragment> questionFragments = new ArrayList<>();
    QuestionFragment questionFragment;

    int contPreguntas = 0;

    String currentUser;
    String edificio;
    int user_puntos;

    FrameLayout frameLayout;
    Drawable drawable;

    int i;

    Map<String, Drawable> imageBuildings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        btnPrev = (Button) findViewById(R.id.prev);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("user");
        edificio = intent.getStringExtra("edificio");
        i = intent.getIntExtra("n_edificios_evaluados", 0);
        System.out.println("Esto es lo que llega " + i);
        user_puntos = intent.getIntExtra("user_puntos", 0);
        frameLayout = (FrameLayout) findViewById(R.id.container);

        imageBuildings.put("EDCOM", getResources().getDrawable(R.drawable.edcom2_start_evaluation));
        imageBuildings.put("FIEC", getResources().getDrawable(R.drawable.fiec_start_evaluation));
        imageBuildings.put("EDCOM_background", getResources().getDrawable(R.drawable.edcom_background));
        imageBuildings.put("FIEC_background", getResources().getDrawable(R.drawable.fiec_background));

        frameLayout.setBackground(imageBuildings.get(edificio));

        StratFragment stratFragment = new StratFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, stratFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

        myRef = database.getReference("preguntas/edificio");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int j = 1;


                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Pregunta pregunta = snap.getValue(Pregunta.class);

                    questionFragment = new QuestionFragment();

                    questionFragment.pregunta = pregunta;
                    questionFragment.numero_pregunta = j;

                    questionFragments.add(questionFragment);
                    j++;

                }
            frameLayout.setBackground(imageBuildings.get(edificio+"_background"));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });

    }

    public void prevAction(View view) {

        if(btnPrev.getTag().equals("Cancelar")){
            System.out.println("PILAS");
            this.finish();
            btnPrev.setBackground(getResources().getDrawable(R.drawable.prev_arrow_button_pushed));
            //startActivity(new Intent(EvaluationActivity.this, MapsActivity.class));
        }

    }

    public void nextAction(View view) {

        btnNext = (Button) findViewById(R.id.next);
        if(btnNext.getTag().equals("Iniciar")){
            ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if(isConnected) {
                contPreguntas = 0;
                btnNext.setBackground(getResources().getDrawable(R.drawable.next_arrow_button_pushed));
                btnNext.setTag("Next");
                btnNext.setEnabled(false);
                btnPrev.setVisibility(View.INVISIBLE);
                System.out.println("Numero de preguntas: " + questionFragments.size());
                getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragments.get(contPreguntas))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                //btnNext.setBackground(getResources().getDrawable(R.drawable.next_arrow_button));
                contPreguntas++;
            }
            else{
                Toast.makeText(getBaseContext(), "No tienes acceso a internet, lo necesitas para hacer uso de la app", Toast.LENGTH_LONG).show();
            }
        }

        else if(btnNext.getTag().equals("Next")){

            btnNext.setBackground(getResources().getDrawable(R.drawable.next_arrow_button_pushed));
            btnNext.setEnabled(false);
            System.out.println("Contador de preguntas: " + contPreguntas);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragments.get(contPreguntas))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();

            if (contPreguntas == questionFragments.size()-1){
                btnNext.setTag("Acabar");
            }
                contPreguntas++;
        }

        else if (btnNext.getTag().equals("Acabar")){
            ArrayList<Integer> respuestas = new ArrayList<Integer>();
            for (QuestionFragment q: questionFragments){
                respuestas.add(q.pregunta.getRespuesta());
            }

            final List<Integer> list_1 = Arrays.asList();
            List<Integer> list_2 = Arrays.asList();
            List<Integer> list_3 = Arrays.asList();
            List<Integer> list_4 = Arrays.asList();
            List<Integer> list_5 = Arrays.asList();

            Validacion validacion = new Validacion(currentUser, edificio, respuestas);
            myRef2.child("validaciones").push().setValue(validacion);


            /*Query query = myRef2.child("validaciones").orderByChild("edificio").equalTo(edificio);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        System.out.println(snapshot.getValue());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
            System.out.println("CUal es tu problema " + i);
            System.out.println("EDIFICIOS EVALUADOS " + i);
            myRef2.child("users").child(currentUser).child("edificios_evaluados").child(""+i).setValue(edificio);
            myRef2.child("users").child(currentUser).child("puntos").setValue(user_puntos+25);

            WinShareFragment winShareFragment = new WinShareFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, winShareFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();

            btnNext.setBackground(getResources().getDrawable(R.drawable.social_button));
            btnNext.setTag("share");
        }

        else if (btnNext.getTag().equals("share")){
            Toast toast=Toast.makeText(this, "SE COMPARTIRÁ EN UNA RED SOCIAL #SoyIncluyente",Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
