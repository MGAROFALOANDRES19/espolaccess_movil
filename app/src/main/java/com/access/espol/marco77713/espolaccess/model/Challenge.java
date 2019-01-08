package com.access.espol.marco77713.espolaccess.model;

public class Challenge {
    private boolean disponible;
    private Premio premio;

    public Challenge(boolean disponible, Premio premio) {
        this.disponible = disponible;
        this.premio = premio;
    }

    public Challenge(){

    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Premio getPremio() {
        return premio;
    }

    public void setPremio(Premio premio) {
        this.premio = premio;
    }
}
