/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.taxiruedas;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Esta clase recoge la reserva realizada por un usuario y no aceptada por un taxista
 * @author Marcos
 */
public class Reserva implements Serializable{
    /**
     * Id de la reserva
     */
    private Integer id;
    
    /**
     * Apodo del usuario que realiza la reserva
     */
    private String apU;
    
    /**
     * Fecha y hora en la que se realiza la reserva
     */
    private LocalDate h;
    
    /**
     * Zona de inicio
     */
    private Zonas zonaIni;
    
    /**
     * Zona final de llegada
     */
    private Zonas zonaFin;
    
    /**
     * Si ha sido aceptado
     */
    private boolean aceptado;
    
    /**
     * Tipo de taxi que queremos
     */
    private TipoTaxi tTaxi;
    
    /**
     * Crea la reserva en el programa
     * @param id El numero de la reserva
     * @param apU Se refiere al apodo del usuario que ha realizado la reserva
     * @param zI Zona desde donde se inicia el viaje
     * @param zF Zona donde terminara el viaje
     * @param acept Si la reserva ha sido aceptada o no
     * @param tTax Tipo de taxi pedido
     */
    public Reserva(Integer id, String apU,String zI, String zF, int acept, TipoTaxi tTax){
        this.id=id;
        this.apU=apU;
        this.h = LocalDate.now();
        //esto hay que pasarlo a la parte de creación de reserva y cambiar 
        //el parametro de creacion de reserva al tipo enumerado
        switch(zI){
            case "zona1"->{
                this.zonaIni = Zonas.zona1;
            }
            case "1"->{
                this.zonaIni = Zonas.zona1;
            }
            case "zona2"->{
                this.zonaIni = Zonas.zona2;
            }
            case "2"->{
                this.zonaIni = Zonas.zona2;
            }
            case "zona3"->{
                this.zonaIni = Zonas.zona3;
            }
            case "3"->{
                this.zonaIni = Zonas.zona3;
            }
            case "zona4"->{
                this.zonaIni = Zonas.zona4;
            }
            case "4"->{
                this.zonaIni = Zonas.zona4;
            }
            case "zona5"->{
                this.zonaIni = Zonas.zona5;
            }
            case "5"->{
                this.zonaIni = Zonas.zona5;
            }
        }
        //esto hay que pasarlo a la parte de creación de reserva y cambiar 
        //el parametro de creacion de reserva al tipo enumerado
        switch(zF){
            case "zona1"->{
                this.zonaFin = Zonas.zona1;
            }
            case "1"->{
                this.zonaFin = Zonas.zona1;
            }
            case "zona2"->{
                this.zonaFin = Zonas.zona2;
            }
            case "2"->{
                this.zonaFin = Zonas.zona2;
            }
            case "zona3"->{
                this.zonaFin = Zonas.zona3;
            }
            case "3"->{
                this.zonaFin = Zonas.zona3;
            }
            case "zona4"->{
                this.zonaFin = Zonas.zona4;
            }
            case "4"->{
                this.zonaFin = Zonas.zona4;
            }
            case "zona5"->{
                this.zonaFin = Zonas.zona5;
            }
            case "5"->{
                this.zonaFin = Zonas.zona5;
            }
        }
        switch (acept){
            case 0->{
                this.aceptado = false;
            }
            case 1->{
                this.aceptado = true;
            }
        }
        this.tTaxi = tTax;
    }

    @Override
    public String toString() {
        return "Apodo usuario: " + this.apU + "\t|Fecha de reserva: " + this.h.toString() + "\r" + "\t|Tipo de taxi: " + this.tTaxi.toString();
    }
    
    
}
