package com.example.bitacora;

import java.util.List;

public class Departamento {
    private String departamentoName;
    private List<String> municipios;

    public Departamento(String departamentoName, List<String> municipios) {
        this.departamentoName = departamentoName;
        this.municipios = municipios;
    }

    public String getDepartamentoName() {
        return departamentoName;
    }

    public List<String> getMunicipios() {
        return municipios;
    }
}
