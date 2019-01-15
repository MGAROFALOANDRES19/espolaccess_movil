package com.access.espol.marco77713.espolaccess.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Pregunta;
import com.access.espol.marco77713.espolaccess.views.fragments.MapsActivity;
import com.access.espol.marco77713.espolaccess.views.fragments.QuestionFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.StratFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.WinShareFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EvaluationActivity extends AppCompatActivity {

    Button btnPrev, btnNext;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    DatabaseReference myRef2 = database.getReference("validaciones");

    List<QuestionFragment> questionFragments = new ArrayList<>();
    QuestionFragment questionFragment;

    int contPreguntas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        StratFragment stratFragment = new StratFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, stratFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

        myRef = database.getReference("preguntas/edificio");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Pregunta pregunta = snap.getValue(Pregunta.class);
                    questionFragment = new QuestionFragment();
                    questionFragment.pregunta = pregunta;

                    questionFragments.add(questionFragment);


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });

    }

    public void prevAction(View view) {
        btnPrev = (Button) findViewById(R.id.prev);
        if(btnPrev.getTag().equals("Cancelar")){
            System.out.println("PILAS");
            this.finish();

            //startActivity(new Intent(EvaluationActivity.this, MapsActivity.class));
        }

    }

    public void nextAction(View view) {
        btnNext = (Button) findViewById(R.id.next);
        if(btnNext.getTag().equals("Iniciar")){


            btnNext.setTag("Next");
            System.out.println(questionFragments.size());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragments.get(contPreguntas))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
            contPreguntas++;
        }

        else if(btnNext.getTag().equals("Next")){

            getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragments.get(contPreguntas))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();

            if (contPreguntas < questionFragments.size()){
                btnNext.setTag("Acabar");
            }
                contPreguntas++;
        }

        else if (btnNext.getTag().equals("Acabar")){

            for (QuestionFragment q: questionFragments){
                System.out.println("RESPUESTA" + q.pregunta.getRespuesta());
            }

            WinShareFragment winShareFragment = new WinShareFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, winShareFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();

        }
    }
}
