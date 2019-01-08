package com.access.espol.marco77713.espolaccess.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Posicion;

import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.MyViewHolder> {

    private List<Posicion> positionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lugar, nombre, puntos;

        public MyViewHolder(View view) {
            super(view);
            lugar = (TextView) view.findViewById(R.id.lugar);
            nombre = (TextView) view.findViewById(R.id.nombre);
            puntos = (TextView) view.findViewById(R.id.puntos);
        }
    }


    public PositionAdapter(List<Posicion> positionsList) {
        this.positionsList = positionsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.position_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Posicion posicion = positionsList.get(position);
        holder.lugar.setText("" + posicion.getLugar());
        holder.nombre.setText(posicion.getNombre());
        holder.puntos.setText("" + posicion.getPuntos());
    }

    @Override
    public int getItemCount() {
        return positionsList.size();
    }
}