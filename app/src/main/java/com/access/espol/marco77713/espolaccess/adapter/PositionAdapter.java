package com.access.espol.marco77713.espolaccess.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Posicion;
import com.access.espol.marco77713.espolaccess.model.User;
import com.access.espol.marco77713.espolaccess.views.EstadisticActivity;

import java.util.List;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.MyViewHolder> {

    private List<Posicion> positionsList;
    public Context mContext;
    public  List<User> userList;
    int position;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lugar, nombre, puntos;

        public MyViewHolder(View view) {
            super(view);
            lugar = (TextView) view.findViewById(R.id.lugar);
            nombre = (TextView) view.findViewById(R.id.nombre);
            puntos = (TextView) view.findViewById(R.id.puntos);
        }
    }


    public PositionAdapter(List<Posicion> positionsList, Context context, List<User> usersList) {
        this.positionsList = positionsList;
        this.mContext = context;
        this.userList = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.position_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        this.position = position;
        final Posicion posicion = positionsList.get(this.position);
        holder.lugar.setText("" + posicion.getLugar());
        holder.nombre.setText(posicion.getNombre());
        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EstadisticActivity.class);
                intent.putExtra("edificios_evaluados", userList.get(position).getEdificios_evaluados().size());
                intent.putExtra("nombre", positionsList.get(position).getNombre());
                mContext.startActivity(intent);

            }
        });
        holder.puntos.setText("" + posicion.getPuntos());
    }

    @Override
    public int getItemCount() {
        return positionsList.size();
    }
}