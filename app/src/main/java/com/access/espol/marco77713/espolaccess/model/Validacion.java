package com.access.espol.marco77713.espolaccess.model;

import java.util.ArrayList;

public class Validacion {
    private String user;
    private String edificio;
    private ArrayList<Integer> respuestas;

    public Validacion(String user, String edificio, ArrayList<Integer> respuestas) {
        this.user = user;
        this.edificio = edificio;
        this.respuestas = respuestas;
    }

    public Validacion(){

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public ArrayList<Integer> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Integer> respuestas) {
        this.respuestas = respuestas;
    }
}
