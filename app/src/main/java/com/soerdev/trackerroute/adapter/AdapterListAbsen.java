package com.soerdev.trackerroute.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soerdev.trackerroute.R;
import com.soerdev.trackerroute.model.ModelListAbsen;

import java.util.ArrayList;
import java.util.List;

public class AdapterListAbsen extends RecyclerView.Adapter<AdapterListAbsen.ViewHolder>{

    private List<ModelListAbsen> modelListAbsens;
    private Context context;

    public AdapterListAbsen(Context context, List<ModelListAbsen> modelListAbsenArrayList){
        this.modelListAbsens = modelListAbsenArrayList;
        this.context = context;
    }

    @Override
    public AdapterListAbsen.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_absen, null);

        ViewHolder viewHolder = new ViewHolder(mView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListAbsen.ViewHolder holder, int position) {
        ModelListAbsen MLA = modelListAbsens.get(position);

        holder.username.setText(String.valueOf(MLA.getUsername()));
        holder.tanggalAbsen.setText(String.valueOf(MLA.getDate()));
        holder.kordinatAwal.setText(String.valueOf(MLA.getAwal()));
        holder.kordinatAkhir.setText(String.valueOf(MLA.getAkhir()));
    }

    @Override
    public int getItemCount() {
        return modelListAbsens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, tanggalAbsen, kordinatAwal, kordinatAkhir;

        public ViewHolder(View itemView){
            super(itemView);

            username = (TextView)itemView.findViewById(R.id.userName);
            tanggalAbsen = (TextView)itemView.findViewById(R.id.tanggalAbsen);
            kordinatAwal = (TextView)itemView.findViewById(R.id.awalKoordinat);
            kordinatAkhir = (TextView)itemView.findViewById(R.id.akhirKoordinat);
        }
    }
}
