package com.access.espol.marco77713.espolaccess.model;

public class Pregunta {

    private String pregunta;
    private String opcion1;
    private String opcion2;
    private int respuesta;

    public Pregunta(String pregunta, String opcion1, String opcion2, int respuesta) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.respuesta = respuesta;
    }

    public Pregunta(){

    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }
}
