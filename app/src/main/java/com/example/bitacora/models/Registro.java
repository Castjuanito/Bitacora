package com.example.bitacora.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.List;

public class Registro {
    private int id;
    private String titulo;
    private List<String> fotos;
    private String descripcion;
    private List<String> audios;
    private LocalDate fecha;
    private String ubicacion;
    private double latitud;
    private double longitud;

    public Registro(int id) {
        this.id = id;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Registro(int id, String titulo, List<String> fotos, String descripcion, List<String> audios) {
        this.id = id;
        this.titulo = titulo;
        this.fotos = fotos;
        this.descripcion = descripcion;
        this.audios = audios;
        this.fecha = LocalDate.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getAudios() {
        return audios;
    }

    public void setAudio(List<String> audios) {
        this.audios = audios;
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
