package com.example.bitacora.models;

import java.time.LocalDate;
import java.util.List;

public class Infraestructura extends Registro {
    public Infraestructura(int id, String titulo, List<String> fotos, String descripcion, List<String> audios) {
        super(id, titulo, fotos, descripcion, audios);
    }
}
