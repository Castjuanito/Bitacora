package com.example.bitacora.activities.Viaje.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bitacora.R;
import com.example.bitacora.models.Departamento;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoAdapter extends ArrayAdapter<Departamento> {
    private List<Departamento> departamentoList = new ArrayList<>();

    public DepartamentoAdapter(@NonNull Context context, int resource, int spinnerText, @NonNull List<Departamento> stateList) {
        super(context, resource, spinnerText, stateList);
        this.departamentoList = stateList;
    }

    @Override
    public Departamento getItem(int position) {
        return departamentoList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    /**
     * Gets the state object by calling getItem and
     * Sets the state name to the drop-down TextView.
     *
     * @param position the position of the item selected
     * @return returns the updated View
     */
    private View initView(int position) {
        Departamento state = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.departamento_list, null);
        TextView textView =  v.findViewById(R.id.spinnerTextDep);
        textView.setText(state.getDepartamentoName());
        return v;

    }
}