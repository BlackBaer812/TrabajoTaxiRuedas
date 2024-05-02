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
     * Crea la reserva en el programa
     * @param id El numero de la reserva
     * @param apU Se refiere al apodo del usuario que ha realizado la reserva
     */
    public Reserva(Integer id, String apU){
        this.id=id;
        this.apU=apU;
        this.h = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Apodo usuario: " + this.apU + "\t|Fecha de reserva: " + this.h.toString() + "\r";
    }
    
    
}
