package com.example.bitacora.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.List;

public class Muestra extends Registro {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Muestra(int id, String titulo, List<String> fotos, String descripcion, List<String> audios) {
        super(id, titulo, fotos, descripcion, audios);
    }
}
