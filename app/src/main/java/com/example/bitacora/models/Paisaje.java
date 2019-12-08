package com.example.bitacora.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Paisaje extends Registro {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Paisaje(int id, String titulo, List<String> fotos, String descripcion, List<String> audios) {
        super(id, titulo, fotos, descripcion, audios);
    }
}
