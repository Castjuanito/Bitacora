package com.example.bitacora.activities.Registro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitacora.R;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.activities.Registro.adapters.RegistroAdapter;
import com.example.bitacora.models.Registro;
import com.example.bitacora.util.SQLRegistroToRegistroParser;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivy extends AppCompatActivity {

    SQLRegistro mSQLRegistro = new SQLRegistro(this);
    List<Registro> rowItems;
    ListView mylistview;
    int idViaje;
    int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Intent intent = getIntent();
        idViaje = intent.getIntExtra("idViaje",-1);
        tipo = intent.getIntExtra("tipo", -1);

        rowItems = SQLRegistroToRegistroParser.toRegistros(idViaje,tipo,this);
        mylistview = findViewById(R.id.listViewRegistros);
        RegistroAdapter adapter = new RegistroAdapter(this, rowItems);
        mylistview.setAdapter(adapter);


    }

}
