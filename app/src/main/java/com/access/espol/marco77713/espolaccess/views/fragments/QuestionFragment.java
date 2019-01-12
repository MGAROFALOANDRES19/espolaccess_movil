package com.access.espol.marco77713.espolaccess.views.fragments;


import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Pregunta;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment{

    TextView txtQuestion;
    Button btn1, btn2, btn3;

    public Pregunta pregunta;

    public QuestionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        txtQuestion = (TextView) view.findViewById(R.id.question);
        txtQuestion.setText(pregunta.getPregunta());

        btn1 = (Button) view.findViewById(R.id.ask1);
        btn1.setText(pregunta.getOpcion1());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pregunta.setRespuesta(0);
                System.out.println("PRUEBA" + pregunta.getRespuesta());
            }
        });

        btn2 = (Button) view.findViewById(R.id.ask2);
        btn2.setText(pregunta.getOpcion2());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pregunta.setRespuesta(1);
                System.out.println("PRUEBA" + pregunta.getRespuesta());
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
