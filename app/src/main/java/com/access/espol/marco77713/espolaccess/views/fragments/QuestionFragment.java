package com.access.espol.marco77713.espolaccess.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    TextView txtQuestion;
    Button btn1, btn2, btn3;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);



        // Inflate the layout for this fragment
        return view;
    }

}
