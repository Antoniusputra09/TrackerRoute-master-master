package com.soerdev.trackerroute.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soerdev.trackerroute.R;
import com.soerdev.trackerroute.model.ModelListAbsen;

import java.util.List;

public class AdapterAbsenList extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<ModelListAbsen> data;

    public AdapterAbsenList(Activity activity, List<ModelListAbsen> data){
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int location) {
        return data.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(layoutInflater == null){
            layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = layoutInflater.inflate(R.layout.list_absen, null);
        }

        TextView username = (TextView)view.findViewById(R.id.userName);
        TextView tanggalAbsen = (TextView)view.findViewById(R.id.tanggalAbsen);
        TextView kordinatAwal = (TextView)view.findViewById(R.id.awalKoordinat);
        TextView kordinatAkhir = (TextView)view.findViewById(R.id.akhirKoordinat);

        ModelListAbsen modelListAbsen = data.get(position);

        username.setText(modelListAbsen.getUsername());
        tanggalAbsen.setText(modelListAbsen.getDate());
        kordinatAkhir.setText(modelListAbsen.getAkhir());
        kordinatAwal.setText(modelListAbsen.getAwal());

        return view;
    }
}
