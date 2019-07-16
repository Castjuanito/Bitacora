package com.example.bitacora.models;

import java.time.LocalDate;

public class Registro {
    private int id;
    private String titulo;
    private String foto;
    private String descripcion;
    private String audio;
    private LocalDate fecha;
    private String ubicacion;
    private double latitud;
    private double longitud;


    public Registro(int id, String titulo, String foto, String descripcion, String audio, LocalDate fecha, String ubicacion, double latitud, double longitud) {
        this.id = id;
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
        this.audio = audio;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
