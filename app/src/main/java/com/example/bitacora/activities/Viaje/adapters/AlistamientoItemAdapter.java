package com.example.bitacora.activities.Viaje.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.bitacora.R;
import com.example.bitacora.models.AlistamientoItem;

import java.util.List;

public class AlistamientoItemAdapter extends BaseAdapter {

    Context context;
    List<AlistamientoItem> rowItems;

    public AlistamientoItemAdapter(Context context, List<AlistamientoItem> rowItems) {
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_check, viewGroup, false);


        CheckBox estado = view.findViewById(R.id.checkBox);


        final AlistamientoItem s = (AlistamientoItem) this.getItem(i);

        estado.setId(i);

        estado.setText(s.getContenido());
        estado.setChecked(s.getEstado());


        return view;
    }
}
