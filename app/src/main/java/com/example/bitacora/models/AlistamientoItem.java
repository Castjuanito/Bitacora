package com.example.bitacora.models;

public class AlistamientoItem {
    private  int id;
    private String contenido;
    private Boolean estado;


    public AlistamientoItem(int id, String contenido, Boolean estado) {
        this.id = id;
        this.contenido = contenido;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void changeEstado() {
        if (this.estado==false)
            this.estado=true;
        if (this.estado==true)
            this.estado=false;
    }
}
