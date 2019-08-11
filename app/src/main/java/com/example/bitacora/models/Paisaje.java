package com.example.bitacora.models;

import java.time.LocalDate;
import java.util.List;

public class Paisaje extends Registro {
    public Paisaje(int id, String titulo, List<String> fotos, String descripcion, List<String> audios, LocalDate fecha, String ubicacion, double latitud, double longitud) {
        super(id, titulo, fotos, descripcion, audios, fecha, ubicacion, latitud, longitud);
    }
}
