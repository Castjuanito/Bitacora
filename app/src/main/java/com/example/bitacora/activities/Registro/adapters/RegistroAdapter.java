package com.example.bitacora.activities.Registro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bitacora.R;
import com.example.bitacora.models.Registro;
import com.example.bitacora.models.Viaje;

import java.util.List;

public class RegistroAdapter extends BaseAdapter {


    Context context;
    List<Registro> rowItems;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.item_viajes,viewGroup,false);
        }

        TextView fechaTxt= (TextView) view.findViewById(R.id.fecha);
        TextView ubicacionTxt= (TextView) view.findViewById(R.id.trayecto);


        return view;
    }
}
