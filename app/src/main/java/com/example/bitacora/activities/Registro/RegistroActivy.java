package com.example.bitacora.activities.Registro;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitacora.R;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.activities.Registro.adapters.RegistroAdapter;
import com.example.bitacora.models.Registro;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivy extends AppCompatActivity {

    SQLRegistro mSQLRegistro = new SQLRegistro(this);
    List<Registro> rowItems;
    ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mSQLRegistro = new SQLRegistro(this);
        rowItems = new ArrayList<Registro>();

        rowItems = cargarRegistros();
        mylistview = findViewById(R.id.listViewRegistros);
        RegistroAdapter adapter = new RegistroAdapter(this, rowItems);
        mylistview.setAdapter(adapter);


    }

    private List<Registro> cargarRegistros() {
        Registro mRegistro = new Registro(1);
        rowItems.add(mRegistro);
        return rowItems;
    }
}
