package com.access.espol.marco77713.espolaccess.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Accesibility;

import org.w3c.dom.Text;

import java.util.List;

public class AccesibilityAdapter extends RecyclerView.Adapter<AccesibilityAdapter.MyViewHolder> {

    private List<Accesibility> accesibilitiesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;

        public MyViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.icon_accesibility);
            text = (TextView) view.findViewById(R.id.info_accesibility);
        }
    }


    public AccesibilityAdapter(List<Accesibility> accesibilitiesList) {
        this.accesibilitiesList = accesibilitiesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accesibility_information, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        System.out.println(accesibilitiesList.get(position));
        Accesibility accesibility = accesibilitiesList.get(position);
        holder.text.setText(""+accesibility.getInfo());
        holder.icon.setBackground(accesibility.getIcon());

    }

    @Override
    public int getItemCount() {
        return accesibilitiesList.size();
    }
}