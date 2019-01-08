package com.access.espol.marco77713.espolaccess.model;

public class BuildRow {
    private String nombreEdificio;
    private String ubicacionEdificio;

    public BuildRow(String nombreEdificio, String ubicacionEdificio) {
        this.nombreEdificio = nombreEdificio;
        this.ubicacionEdificio = ubicacionEdificio;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio;
    }

    public String getUbicacionEdificio() {
        return ubicacionEdificio;
    }

    public void setUbicacionEdificio(String ubicacionEdificio) {
        this.ubicacionEdificio = ubicacionEdificio;
    }
}
