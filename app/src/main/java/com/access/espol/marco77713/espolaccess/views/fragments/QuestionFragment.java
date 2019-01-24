package com.access.espol.marco77713.espolaccess.views.fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Pregunta;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment{

    TextView txtQuestion;
    Button btn1, btn2;
    ImageView img, img_info_ques;
    Button btnNext;

    public Pregunta pregunta;
    public int numero_pregunta;

    public QuestionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        txtQuestion = (TextView) view.findViewById(R.id.question);
        txtQuestion.setText(pregunta.getPregunta());

        img = (ImageView) view.findViewById(R.id.percentage);

        switch (numero_pregunta){
            case 1:
                img.setBackground(getResources().getDrawable(R.drawable.progress_questions_1));
                break;
            case 2:
                img.setBackground(getResources().getDrawable(R.drawable.progress_questions_2));
                break;
            case 3:
                img.setBackground(getResources().getDrawable(R.drawable.progress_questions_3));
                break;
            case 4:
                img.setBackground(getResources().getDrawable(R.drawable.progress_questions_4));
                break;
            case 5:
                img.setBackground(getResources().getDrawable(R.drawable.progress_questions_5));
                break;
        }

        btn1 = (Button) view.findViewById(R.id.ask1);
        btn2 = (Button) view.findViewById(R.id.ask2);
        img_info_ques = (ImageView) view.findViewById(R.id.info_question);

        img_info_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNext = getActivity().findViewById(R.id.next);
        btn1.setText(pregunta.getOpcion1());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pregunta.setRespuesta(0);

                answerPushed(btn1, btn2);

                System.out.println("PRUEBA" + pregunta.getRespuesta());
            }


        });


        btn2.setText(pregunta.getOpcion2());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pregunta.setRespuesta(1);

                answerPushed(btn2, btn1);

                System.out.println("PRUEBA" + pregunta.getRespuesta());
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    private void answerPushed(Button btnTmp1, Button btnTmp2) {
        btnTmp1.setEnabled(false);
        btnTmp1.setTextColor(Color.parseColor("#7f7b6d"));
        btnTmp1.setBackground(getResources().getDrawable(R.drawable.btn_rounded_pushed));

        btnTmp2.setEnabled(true);
        btnTmp2.setTextColor(Color.parseColor("#fff8db"));
        btnTmp2.setBackground(getResources().getDrawable(R.drawable.btn_rounded));

        btnNext.setEnabled(true );
        btnNext.setBackground(getResources().getDrawable(R.drawable.btn_rounded));

    }

}
