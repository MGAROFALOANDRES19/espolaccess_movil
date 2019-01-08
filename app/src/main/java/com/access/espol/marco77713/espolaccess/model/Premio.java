package com.access.espol.marco77713.espolaccess.model;

class Premio {
    private  String descripcion;
    private  String nombre;
    private int puntos;


    public Premio(String descripcion, String nombre, int puntos) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
