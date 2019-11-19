package com.example.bitacora.activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitacora.R;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.SQLViaje;
import com.example.bitacora.activities.Viaje.CrearViajeActivity;
import com.example.bitacora.activities.Viaje.MisViajesActivity;

public class MainActivity extends AppCompatActivity {

    SQLViaje nSQLViaje;
    SQLRegistro nSQLRegistro;
    private Button misViajes, crearViaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        misViajes = findViewById(R.id.buttonMisViajes);
        crearViaje = findViewById(R.id.buttonIniciarViaje);

        misViajes.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MisViajesActivity.class);
            startActivity(intent);
        });

        crearViaje.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CrearViajeActivity.class);
            startActivity(intent);
        });
    }


}
