package com.example.bitacora;

import java.time.LocalDate;

public class Viaje {
    private int id;
    String fechaSalida;
    String materia;
    String profesor;
    private String ubicacion;
    private double latitud;
    private double longitud;


    public Viaje(int id, String fechaSalida, String ubicacion) {
        this.id = id;
        this.fechaSalida = fechaSalida;
        this.ubicacion = ubicacion;
    }

    public Viaje(int id, String fechaSalida, String materia, String profesor, String ubicacion, double latitud, double longitud) {
        this.id = id;
        this.fechaSalida = fechaSalida;
        this.materia = materia;
        this.profesor = profesor;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
