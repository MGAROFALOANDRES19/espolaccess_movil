package com.access.espol.marco77713.espolaccess.views.fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;

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
        ImageView imageView = (ImageView) view.findViewById(R.id.txtClose);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getActivity().finish();
            }
        });
        return view;
    }

}
