package com.example.bitacora.activities.Viaje;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitacora.R;
import com.example.bitacora.SQLViaje;
import com.example.bitacora.activities.Alistamiento.AlistamientoActivity;
import com.example.bitacora.activities.Registro.CrearRegistroActivity;
import com.example.bitacora.activities.Registro.RegistroActivy;

public class DetalleViajeActivity extends AppCompatActivity {

    SQLViaje mSQLViaje;
    TextView titulo;
    TextView profesor;
    TextView materia;
    Button buttonAlistamiento;
    ImageButton buttonAgregarRecuerdos;
    ImageButton buttonAgregarPaisaje;
    ImageButton buttonAgregarMuestra;
    ImageButton buttonAgregarInfraestructura;
    Button buttonRecuerdos;
    Button buttonPaisajes;
    Button buttonMuestras;
    Button buttonInfraestructuras;


    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_viaje);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        mSQLViaje = new SQLViaje(this);
        buttonAlistamiento = findViewById(R.id.buttonAlistamiento);
        buttonAgregarRecuerdos = findViewById(R.id.buttonAgregarRecuerdos);
        buttonAgregarPaisaje = findViewById(R.id.buttonAgregarPaisaje);
        buttonAgregarMuestra = findViewById(R.id.buttonAgregarMuestra);
        buttonAgregarInfraestructura = findViewById(R.id.buttonAgregarInfraestructura);

        buttonRecuerdos = findViewById(R.id.buttonRecuerdos);
        buttonPaisajes = findViewById(R.id.buttonPaisajes);
        buttonMuestras = findViewById(R.id.buttonMuestras);
        buttonInfraestructuras = findViewById(R.id.buttonInfraestructuras);


        titulo = findViewById(R.id.textTitulo);
        profesor = findViewById(R.id.textViewProfesor);
        materia = findViewById(R.id.textViewMateria);

        String fecha;
        String mat;
        String prof;
        String ubicacion;
        Cursor viaje = mSQLViaje.getItemByID(selectedID);

        while (viaje.moveToNext()) {
            fecha = viaje.getString(1);
            mat = viaje.getString(2);
            prof = viaje.getString(3);
            ubicacion = viaje.getString(4);
            titulo.setText(fecha + ' ' + ubicacion);
            profesor.setText("Profesor: " + prof);
            materia.setText("Materia: " + mat);
        }

        buttonAlistamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, AlistamientoActivity.class);
                editScreenIntent.putExtra("id", selectedID);
                startActivity(editScreenIntent);
            }
        });

        buttonAgregarRecuerdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, CrearRegistroActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 1);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        buttonAgregarPaisaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, CrearRegistroActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 2);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        buttonAgregarMuestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, CrearRegistroActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 3);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        buttonAgregarInfraestructura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, CrearRegistroActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 4);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });


        buttonRecuerdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, RegistroActivy.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 1);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        buttonPaisajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, RegistroActivy.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 2);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        buttonMuestras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, RegistroActivy.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 3);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        buttonInfraestructuras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreenIntent = new Intent(DetalleViajeActivity.this, RegistroActivy.class);
                Bundle extras = new Bundle();
                extras.putInt("viajeId", selectedID);
                extras.putInt("tipo", 4);
                editScreenIntent.putExtras(extras);
                startActivity(editScreenIntent);
            }
        });

        //Toast.makeText(getApplicationContext(), "Selected ID: " + selectedID, Toast.LENGTH_SHORT).show();


    }
}
