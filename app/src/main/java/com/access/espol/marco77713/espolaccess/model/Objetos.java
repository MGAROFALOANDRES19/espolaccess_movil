package com.access.espol.marco77713.espolaccess.model;

public class Objetos {

    public String nombre;
    public String icono;
    public double latitud;
    public double longitud;
    public boolean estado;


    public Objetos(){
        nombre="";
        latitud=0.0;
        longitud=0.0;
        estado=false;
        icono="";
    }
    public Objetos(String nombre,double latitud, double longitud, boolean estado, String icono){
        this.nombre=nombre;
        this.latitud=latitud;
        this.longitud=longitud;
        this.estado=estado;
        this.icono=icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}