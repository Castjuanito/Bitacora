package com.example.bitacora.models;

import java.time.LocalDate;

public class Infraestructura extends Registro {
    public Infraestructura(int id, String titulo, String foto, String descripcion, String audio, LocalDate fecha, String ubicacion, double latitud, double longitud) {
        super(id, titulo, foto, descripcion, audio, fecha, ubicacion, latitud, longitud);
    }
}
