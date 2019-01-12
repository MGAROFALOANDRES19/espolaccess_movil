package com.access.espol.marco77713.espolaccess.views.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.views.BuildingActivity;
import com.access.espol.marco77713.espolaccess.views.EvaluationActivity;

import java.util.ArrayList;

import static com.access.espol.marco77713.espolaccess.R.layout.see_or_evaluate;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements  View.OnClickListener{

    Dialog myDialog;
    public ArrayList<String> edificios_evaluados = new ArrayList<>();
    public String edificio = "EDCOM";
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myDialog = new Dialog(this.getContext());
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn1 = (Button) getActivity().findViewById(R.id.map);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button btn1, btn2;

        if (v.getId() == R.id.map){
            myDialog.setContentView(see_or_evaluate);
            btn1 = (Button) myDialog.findViewById(R.id.see);
            btn2 = (Button) myDialog.findViewById(R.id.evaluate);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(myDialog.getContext(), BuildingActivity.class);
                    intent.putExtra("edificio", edificio);
                    startActivity(intent);

                    myDialog.dismiss();
                }
            });

            if (this.edificios_evaluados.contains(edificio)){
                btn2.setEnabled(false);
            }

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(myDialog.getContext(), EvaluationActivity.class);
                    startActivity(intent);

                    myDialog.dismiss();
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            myDialog.show();

        }
    }
}
