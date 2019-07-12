package com.example.bitacora;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    SQLViaje nSQLViaje;
    SQLRegistro nSQLRegistro;
    private Button misViajes, crearViaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        misViajes = (Button) findViewById(R.id.buttonMisViajes);
        crearViaje = (Button) findViewById(R.id.buttonIniciarViaje);

        misViajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MisViajesActivity.class);
                startActivity(intent);
            }
        });

        crearViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrearViajeActivity.class);
                startActivity(intent);
            }
        });
    }


}
