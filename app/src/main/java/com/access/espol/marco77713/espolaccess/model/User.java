package com.access.espol.marco77713.espolaccess.model;

import java.util.ArrayList;

public class User {
    private int puntos;
    private int lugar;
    private ArrayList<String> edificios_evaluados;
    private ArrayList<Pregunta> respuestas;
    private String email;
    private int personalizacion; //0,1,2

    public User(String email, int personalizacion) {
        this.puntos = 5;
        this.lugar = 1;
        this.edificios_evaluados = new ArrayList<>();
        this.respuestas = null;
        this.email = email;
        this.personalizacion = personalizacion;
    }

    public User(){

    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public ArrayList<String> getEdificios_evaluados() {
        return edificios_evaluados;
    }

    public void setEdificios_evaluados(ArrayList<String> edificios_evaluados) {
        this.edificios_evaluados = edificios_evaluados;
    }

    public ArrayList<Pregunta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Pregunta> respuestas) {
        this.respuestas = respuestas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPersonalizacion() {
        return personalizacion;
    }

    public void setPersonalizacion(int personalizacion) {
        this.personalizacion = personalizacion;
    }
}
