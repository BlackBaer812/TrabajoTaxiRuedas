/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.taxiruedas;

import java.time.LocalDate;

/**
 * Clase para guardar los viaje realizados
 * @author Marcos Ruiz Clemente
 */
public class Viaje {
    /**
     * Id del viaje
     */
    private int id;
    /**
     * Apodo de usuario
     */
    private String apodo_usu;
    /**
     * Fecha de la reserva
     */
    private LocalDate fecha;
    /**
     * Zona de inicio del viaje
     */
    private Zonas zInicio;
    /**
     * Zona final del viaje
     */
    private Zonas zFinal;
    /**
     * Apodo del taxista que ha aceptado el viaje
     */
    private String apodo_taxista;
    /**
     * Matricula del taxi que ha aceptado el taxi
     */
    private String matTaxi;
    /**
     * Si el viaje ha sido finalizado
     */
    private int fin;
    
    /**
     * Constructor de taxi con la información para el usuario
     * @param ID Clave primaria del viaje
     * @param aUsu Apodo del usuario que realizo la reserva
     * @param f fecha que se realizo la reserva
     * @param zI zona de inicio del viaje
     * @param zF zona final de viaje
     * @param aTaxista apodo del taxista
     * @param mTaxi matricula del taxi con el que se realizara el viaje
     */
    public Viaje(int ID, String aUsu, LocalDate f, String zI, String zF, String aTaxista, String mTaxi){
        this.id = ID;
        this.apodo_usu =  aUsu;
        this.fecha = f;
        switch(zI){
            case "zona1"->{
                this.zInicio = Zonas.zona1;
            }
            case "1"->{
                this.zInicio = Zonas.zona1;
            }
            case "zona2"->{
                this.zInicio = Zonas.zona2;
            }
            case "2"->{
                this.zInicio = Zonas.zona2;
            }
            case "zona3"->{
                this.zInicio = Zonas.zona3;
            }
            case "3"->{
                this.zInicio = Zonas.zona3;
            }
            case "zona4"->{
                this.zInicio = Zonas.zona4;
            }
            case "4"->{
                this.zInicio = Zonas.zona4;
            }
            case "zona5"->{
                this.zInicio = Zonas.zona5;
            }
            case "5"->{
                this.zInicio = Zonas.zona5;
            }
        }
        switch(zF){
            case "zona1"->{
                this.zFinal = Zonas.zona1;
            }
            case "1"->{
                this.zFinal = Zonas.zona1;
            }
            case "zona2"->{
                this.zFinal = Zonas.zona2;
            }
            case "2"->{
                this.zFinal = Zonas.zona2;
            }
            case "zona3"->{
                this.zFinal = Zonas.zona3;
            }
            case "3"->{
                this.zFinal = Zonas.zona3;
            }
            case "zona4"->{
                this.zFinal = Zonas.zona4;
            }
            case "4"->{
                this.zFinal = Zonas.zona4;
            }
            case "zona5"->{
                this.zFinal = Zonas.zona5;
            }
            case "5"->{
                this.zFinal = Zonas.zona5;
            }
        }
        this.apodo_taxista = aTaxista;
        this.matTaxi = mTaxi;
    }

    
    @Override
    public String toString() {
        return "ID: " + id + "\t|Usuario: " + apodo_usu + "\t|Fecha: " + fecha + 
                "\t|Inicio: " + zInicio + "\t|Final: " + zFinal +
                "\t|Taxista: " + apodo_taxista + "\t|Matrícula: " + matTaxi;
    }
    
    
}
