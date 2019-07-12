package com.example.bitacora;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViajeAdapter extends BaseAdapter {


    Context context;
    List<Viaje> rowItems;

    ViajeAdapter(Context context, List<Viaje> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.item_viajes,viewGroup,false);
        }

        TextView fechaTxt= (TextView) view.findViewById(R.id.fecha);
        TextView ubicacionTxt= (TextView) view.findViewById(R.id.trayecto);


        final Viaje s= (Viaje) this.getItem(position);

        fechaTxt.setText(s.getFechaSalida());
        ubicacionTxt.setText(s.getUbicacion());

        return view;
    }
}
