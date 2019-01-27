package com.access.espol.marco77713.espolaccess.views.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static com.access.espol.marco77713.espolaccess.R.layout.explanation;
/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment{

    TextView txtQuestion;
    Button btn1, btn2;
    ImageView img, img_info_ques;
    Button btnNext;

    CarouselView carouselView;

    public Pregunta pregunta;
    public int numero_pregunta;
    Map<Integer, int[]> imageExplanations = new HashMap<>();
    private Dialog myDialog;

    public QuestionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        imageExplanations.put(1, new int[]{R.drawable.parqueadero1, R.drawable.parqueadero_2});
        imageExplanations.put(2, new int[]{R.drawable.rampa_1, R.drawable.rampa_2});
        imageExplanations.put(3, new int[]{R.drawable.bano_1, R.drawable.bano_2});
        imageExplanations.put(4, new int[]{R.drawable.ascensor_1, R.drawable.ascensor_2});
        imageExplanations.put(5, new int[]{R.drawable.mesa_1, R.drawable.mesa_2});

        txtQuestion = (TextView) view.findViewById(R.id.question);
        txtQuestion.setText(pregunta.getPregunta());

        myDialog = new Dialog(this.getContext());
        myDialog.setContentView(explanation);



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

                img_info_ques.setBackground(getResources().getDrawable(R.drawable.info_button_pushed));

                carouselView = (CarouselView) myDialog.findViewById(R.id.explanation_carousel);
                carouselView.setPageCount(imageExplanations.get(numero_pregunta).length);
                carouselView.setImageListener(new ImageListener() {
                    @Override
                    public void setImageForPosition(int position, ImageView imageView) {
                        imageView.setImageResource(imageExplanations.get(numero_pregunta)[position]);
                    }
                });

                Button button = (Button) myDialog.findViewById(R.id.btn_closeExplanation);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img_info_ques.setBackground(getResources().getDrawable(R.drawable.info_button));
                        myDialog.dismiss();
                    }
                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                myDialog.show();

                
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
