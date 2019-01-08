package com.access.espol.marco77713.espolaccess.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.views.ContainerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WinShareFragment extends Fragment {


    public WinShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_win_share,
                container, false);
        TextView txtClose = (TextView) view.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), ContainerActivity.class));
            }
        });
        return view;
    }

}
