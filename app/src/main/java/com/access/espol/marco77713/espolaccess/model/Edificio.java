package com.access.espol.marco77713.espolaccess.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Edificio {
    private String nombre;
    private float longitud;
    private float latitud;
    private int cantidad_evaluaciones;
    private int resultado_accesibilidad; //0 -> no accesible, 1 -> medianamente accesible, 2->totalmente accesible
    private boolean ascensor;
    private boolean rampas;
    private boolean parqueaderos;
    private boolean banos_discapacidad;



    public  Edificio(){

    }

    public Edificio(String nombre, float longitud, float latitud, int cantidad_evaluaciones, int resultado_accesibilidad, boolean ascensor, boolean rampas, boolean parqueaderos, boolean banos_discapacidad) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.cantidad_evaluaciones = cantidad_evaluaciones;
        this.resultado_accesibilidad = resultado_accesibilidad;
        this.ascensor = ascensor;
        this.rampas = rampas;
        this.parqueaderos = parqueaderos;
        this.banos_discapacidad = banos_discapacidad;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre", nombre);
        result.put("longitud", longitud);
        result.put("latitud", latitud);
        result.put("cantidad_evaluaciones", cantidad_evaluaciones);
        result.put("resultado_accesibilidad", resultado_accesibilidad);
        result.put("ascensor", ascensor);
        result.put("rampas", rampas);
        result.put("parqueaderos", parqueaderos);
        result.put("banos_discapacidad", banos_discapacidad);

        return result;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public int getCantidad_evaluaciones() {
        return cantidad_evaluaciones;
    }

    public void setCantidad_evaluaciones(int cantidad_evaluaciones) {
        this.cantidad_evaluaciones = cantidad_evaluaciones;
    }

    public int getResultado_accesibilidad() {
        return resultado_accesibilidad;
    }

    public void setResultado_accesibilidad(int resultado_accesibilidad) {
        this.resultado_accesibilidad = resultado_accesibilidad;
    }

    public boolean isAscensor() {
        return ascensor;
    }

    public void setAscensor(boolean ascensor) {
        this.ascensor = ascensor;
    }

    public boolean isRampas() {
        return rampas;
    }

    public void setRampas(boolean rampas) {
        this.rampas = rampas;
    }

    public boolean isParqueaderos() {
        return parqueaderos;
    }

    public void setParqueaderos(boolean parqueaderos) {
        this.parqueaderos = parqueaderos;
    }

    public boolean isBanos_discapacidad() {
        return banos_discapacidad;
    }

    public void setBanos_discapacidad(boolean banos_discapacidad) {
        this.banos_discapacidad = banos_discapacidad;
        }

    @Override
    public String toString() {
        return "Edificio{" +
                "nombre='" + nombre + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                ", cantidad_evaluaciones=" + cantidad_evaluaciones +
                ", resultado_accesibilidad=" + resultado_accesibilidad +
                ", ascensor=" + ascensor +
                ", rampas=" + rampas +
                ", parqueaderos=" + parqueaderos +
                ", banos_discapacidad=" + banos_discapacidad +
                '}';
    }
}