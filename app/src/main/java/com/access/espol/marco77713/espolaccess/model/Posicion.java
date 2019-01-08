package com.access.espol.marco77713.espolaccess.model;

public class Posicion {

    private int lugar;
    private String nombre;
    private int puntos;

    public Posicion(int lugar, String nombre, int puntos) {
        this.lugar = lugar;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
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
