package com.access.espol.marco77713.espolaccess.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.views.fragments.QuestionFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.StratFragment;
import com.access.espol.marco77713.espolaccess.views.fragments.WinShareFragment;

public class EvaluationActivity extends AppCompatActivity {

    Button btnPrev, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        StratFragment stratFragment = new StratFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, stratFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

    }

    public void prevAction(View view) {
        btnPrev = (Button) findViewById(R.id.prev);
        if(btnPrev.getText() == "Cancelar"){
            startActivity(new Intent(EvaluationActivity.this, ContainerActivity.class));
        }

    }

    public void nextAction(View view) {
        btnNext = (Button) findViewById(R.id.next);
        if(btnNext.getText().equals("Iniciar")){

            QuestionFragment questionFragment = new QuestionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, questionFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        btnNext.setText("Acabar");

        }

        else if (btnNext.getText().equals("Acabar")){
            WinShareFragment winShareFragment = new WinShareFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, winShareFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();

        }
    }
}
