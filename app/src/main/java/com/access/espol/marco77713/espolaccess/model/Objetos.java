package com.access.espol.marco77713.espolaccess.model;

public class Objetos {

    public String nombre;
    public String icono;
    public double latitud;
    public double longitud;
    public boolean estado;
    public int resultado_accesbilidad;


    public Objetos(String nombre, float latitud, float longitud, boolean estado){
        this.nombre ="";
        this.latitud =0.0;
        this.longitud =0.0;
        this.estado =false;
        icono="";

    }
    public Objetos(String nombre,double latitud, double longitud, boolean estado, int resultado_accesibilidad){
        this.nombre=nombre;
        this.latitud=latitud;
        this.longitud=longitud;
        this.estado=estado;
        this.icono=icono;
        this.resultado_accesbilidad = resultado_accesibilidad;
    }

    @Override
    public String toString() {
        return "Objetos{" +
                "nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", estado=" + estado +
                ", resultado_accesbilidad=" + resultado_accesbilidad +
                '}';
    }

    public Objetos() {

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