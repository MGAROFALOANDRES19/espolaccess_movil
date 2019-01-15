package com.access.espol.marco77713.espolaccess.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.BuildRow;
import com.access.espol.marco77713.espolaccess.model.Posicion;
import com.access.espol.marco77713.espolaccess.views.BuildingActivity;
import com.access.espol.marco77713.espolaccess.views.fragments.MapsActivity;

import java.util.ArrayList;
import java.util.List;

public class SearcherAdapter extends RecyclerView.Adapter<SearcherAdapter.MyViewHolder> {

    private List<BuildRow> buildRowsList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreEdificio, ubicacionEdificio;

        public MyViewHolder(View view) {
            super(view);
            nombreEdificio = (TextView) view.findViewById(R.id.nombreEdificio);
            ubicacionEdificio = (TextView) view.findViewById(R.id.ubicacionEdificio);
        }
    }


    public SearcherAdapter(List<BuildRow> buildRowsList, Context context) {
        this.buildRowsList = buildRowsList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int build) {
        final BuildRow buildRow = buildRowsList.get(build);
        holder.nombreEdificio.setText(buildRow.getNombreEdificio());
        holder.nombreEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BuildingActivity.class);
                intent.putExtra("edificio",buildRow.getNombreEdificio());
                mContext.startActivity(intent);

            }
        });
        holder.ubicacionEdificio.setText(buildRow.getUbicacionEdificio());
    }

    @Override
    public int getItemCount() {
        return buildRowsList.size();
    }

    public void updateList(List<BuildRow> buildRows){
        buildRowsList = new ArrayList<>();
        buildRowsList.addAll(buildRows);
        notifyDataSetChanged();
    }
}