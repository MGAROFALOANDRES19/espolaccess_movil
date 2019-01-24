package com.access.espol.marco77713.espolaccess.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.access.espol.marco77713.espolaccess.R;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private boolean mesa;
    private String nombre_completo;

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public Context context;

    public  Edificio(){

    }

    public Edificio(String nombre, float longitud, float latitud, int cantidad_evaluaciones, int resultado_accesibilidad, boolean ascensor, boolean rampas, boolean parqueaderos, boolean banos_discapacidad, boolean mesa, String nombre_completo) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.cantidad_evaluaciones = cantidad_evaluaciones;
        this.resultado_accesibilidad = resultado_accesibilidad;
        this.ascensor = ascensor;
        this.rampas = rampas;
        this.parqueaderos = parqueaderos;
        this.banos_discapacidad = banos_discapacidad;
        this.mesa = mesa;
        this.nombre_completo = nombre_completo;
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
        result.put("mesa", mesa);
        result.put("nombre_completo", nombre_completo);

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

    public boolean isMesa() {
        return mesa;
    }

    public void setMesa(boolean mesa) {
        this.mesa = mesa;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public List<Accesibility> setResultViewAccesibility() {

        List<Drawable> drawablesSinEvaluar = Arrays.asList(
                        context.getResources().getDrawable(R.drawable.icon_sin_evaluar_parqueadero),
                        context.getResources().getDrawable(R.drawable.icon_sin_evaluar_rampa),
                        context.getResources().getDrawable(R.drawable.icon_sin_evaluar_ascensor),
                        context.getResources().getDrawable(R.drawable.icon_sin_evaluar_bano),
                        context.getResources().getDrawable(R.drawable.icon_sin_evaluar_mesa)

        );
        List<Drawable> drawablesAccesibles = Arrays.asList(
                context.getResources().getDrawable(R.drawable.icon_accesible_parqueadero),
                context.getResources().getDrawable(R.drawable.icon_accesible_rampa),
                context.getResources().getDrawable(R.drawable.icon_accesible_ascensor),
                context.getResources().getDrawable(R.drawable.icon_accesible_bano),
                context.getResources().getDrawable(R.drawable.icon_accesible_mesa)
        );
        List<Drawable> drawablesMedianamenteAccesibles = Arrays.asList(
                context.getResources().getDrawable(R.drawable.icon_accesible_medianamente_accesible_parqueadero),
                context.getResources().getDrawable(R.drawable.icon_accesible_medianamente_accesible_rampas),
                context.getResources().getDrawable(R.drawable.icon_accesible_medianamente_accesible_ascensores),
                context.getResources().getDrawable(R.drawable.icon_accesible_medianamente_accesible_banos),
                context.getResources().getDrawable(R.drawable.icon_accesible_medianamente_accesible_mesa)
        );
        List<Drawable> drawablesNoAccesibles = Arrays.asList(
                context.getResources().getDrawable(R.drawable.icon_no_accesible_parqueadero),
                context.getResources().getDrawable(R.drawable.icon_no_accesible_rampa),
                context.getResources().getDrawable(R.drawable.icon_no_accesible_ascensor),
                context.getResources().getDrawable(R.drawable.icon_no_accesible_bano),
                context.getResources().getDrawable(R.drawable.icon_no_accesible_mesa)
        );

        int i = 0;

        List<Accesibility> accesibilities = new ArrayList<Accesibility>();

        for (Drawable drawable : drawablesSinEvaluar){
            accesibilities.add(new Accesibility(drawable, "Sin evaluar"));
        }

        if (this.getResultado_accesibilidad() ==  1){
            for (Accesibility accesibility : accesibilities){
                accesibility.setIcon(drawablesNoAccesibles.get(i));
                i++;
            }
            i=0;
        }

        if (this.getResultado_accesibilidad() ==  2){
            for (Accesibility accesibility : accesibilities){
                accesibility.setIcon(drawablesMedianamenteAccesibles.get(i));
                i++;
            }
            i=0;
        }
        if (this.getResultado_accesibilidad() ==  3){
            for (Accesibility accesibility : accesibilities){
                accesibility.setIcon(drawablesAccesibles.get(i));
                i++;
            }
            i=0;
        }

        if(this.isParqueaderos()){
            accesibilities.get(0).setInfo("Existe plaza de aparcamiento reservada");
        }
        else {
            accesibilities.get(0).setInfo("No existe plaza de aparcamiento reservada");
        }
        if(this.isRampas()){
            accesibilities.get(1).setInfo("Acceso de rampas menor de 12%");
        }
        else {
            accesibilities.get(1).setInfo("No cuentan con acceso de rampas menor de 12%");
        }
        if(this.isAscensor()){
            accesibilities.get(2).setInfo("Hay ascensores que permiten movilizarse");
        }
        else {
            accesibilities.get(2).setInfo("No hay ascensores que permiten movilizarse");
        }
        if(this.isBanos_discapacidad()){
            accesibilities.get(3).setInfo("Existen baños debidamente accesibles");
        }
        else {
            accesibilities.get(3).setInfo("No existen baños debidamente accesibles");
        }
        if(this.isMesa()){
            accesibilities.get(4).setInfo("Existe mesa con altura adecuada para silla de ruedas");
        }
        else {
            accesibilities.get(4).setInfo("No existe mesa con altura adecuada para silla de ruedas");
        }

        return accesibilities;
    }
}