package com.access.espol.marco77713.espolaccess.model;

import android.graphics.drawable.Drawable;

public class Accesibility {
    private Drawable icon;
    private String info;

    public Accesibility(Drawable icon, String info) {
        this.icon = icon;
        this.info = info;
    }

    public Accesibility(){

    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Accesibility{" +
                "icon=" + icon +
                ", info='" + info + '\'' +
                '}';
    }
}
