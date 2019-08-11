package com.example.bitacora.activities.Viaje;

import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitacora.R;
import com.example.bitacora.SQLViaje;
import com.example.bitacora.activities.Alistamiento.AlistamientoActivity;

public class DetalleViajeActivity extends AppCompatActivity {

    private int selectedID;
    SQLViaje mSQLViaje;

    TextView titulo;
    TextView profesor;
    TextView materia;
    Button buttonAlistamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_viaje);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        mSQLViaje = new SQLViaje(this);
        buttonAlistamiento = (Button) findViewById(R.id.buttonAlistamiento);

        titulo = (TextView) findViewById(R.id.textTitulo);
        profesor = (TextView) findViewById(R.id.textViewProfesor);
        materia = (TextView) findViewById(R.id.textViewMateria);

        String fecha;
        String mat;
        String prof;
        String ubicacion;
        Cursor viaje = mSQLViaje.getItemByID(selectedID);

        while(viaje.moveToNext()) {
             fecha = viaje.getString(1);
             mat = viaje.getString(2);
             prof = viaje.getString(3);
             ubicacion = viaje.getString(4);
            titulo.setText(fecha+ ' ' +ubicacion);
            profesor.setText("Profesor: "+prof);
            materia.setText("Materia: "+mat);
        }

        buttonAlistamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, AlistamientoActivity.class);
                editScreenIntent.putExtra("id",selectedID);
                startActivity(editScreenIntent);
            }
        });

        //Toast.makeText(getApplicationContext(), "Selected ID: " + selectedID, Toast.LENGTH_SHORT).show();


    }
}
