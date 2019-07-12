package com.example.bitacora;

import java.time.LocalDate;

public class Recuerdo extends Registro {
    public Recuerdo(int id, String titulo, String foto, String descripcion, String audio, LocalDate fecha, String ubicacion, double latitud, double longitud) {
        super(id, titulo, foto, descripcion, audio, fecha, ubicacion, latitud, longitud);
    }
}
