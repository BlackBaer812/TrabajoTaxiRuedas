/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.taxiruedas;

import java.io.Serializable;

/**
 *
 * @author Marcos
 */
public class Taxista extends UserTaxi implements Serializable{
    
    /**
     * Nombre del usuario
     */
    private String nombre;
    
    /**
     * Apellido del usuario
     */
    private String apellidos;
    
    /**
     * Disponibilidad del taxista
     */
    private boolean disp;
    
    /**
     * Zona en la que esta el taxista disponible
     */
    private Zonas lugar;
    
    /**
     * Matricula del taxi que lleva en ese momento
     */
    private String matTaxi;
    
    /**
     * Construtor de la clase Usuario, al extender de UserTaxi usa su constructor
     * más las variables de nombre y ape (apellidos)
     * @param apodo Nombre del taxista en la aplicación
     * @param clave contraseña que tiene en la aplicación
     * @param nombre Nombre del taxista
     * @param ape Apellidos del taxista
     */
    public Taxista(String apodo, String clave, String nombre, String ape) {
        super(apodo, clave);
        this.nombre = nombre;
        this.apellidos = ape;
    }
    
    /**
     * Constructor para inicio de sesion de taxista
     * @param apodo apodo del taxista que inicia sesion
     * @param nombre nombre del taxista al iniciar sesion
     * @param ape apellidos del taxista al iniciar sesion
     * @param z zona en la que se encuentra
     * @param mTaxi matricula taxi
     */
    public Taxista(String apodo, String nombre, String ape, String z, String mTaxi) {
        super(apodo);
        this.nombre = nombre;
        this.apellidos = ape;
        switch(z){
            case "zona1"->{
                this.lugar = Zonas.zona1;
            }
            case "1"->{
                this.lugar = Zonas.zona1;
            }
            case "zona2"->{
                this.lugar = Zonas.zona2;
            }
            case "2"->{
                this.lugar = Zonas.zona2;
            }
            case "zona3"->{
                this.lugar = Zonas.zona3;
            }
            case "3"->{
                this.lugar = Zonas.zona3;
            }
            case "zona4"->{
                this.lugar = Zonas.zona4;
            }
            case "4"->{
                this.lugar = Zonas.zona4;
            }
            case "zona5"->{
                this.lugar = Zonas.zona5;
            }
            case "5"->{
                this.lugar = Zonas.zona5;
            }
        }
        this.matTaxi = mTaxi;
    }
    
    /**
     * Constructor para tener toda la información de los taxistas
     * @param apodo apodo en la aplicacion
     * @param clave clave usada en la aplicacion
     * @param nombre nombre real
     * @param ape apellidos real
     * @param disp disponibilidad
     * @param z zona en la que se encuenta
     */
    public Taxista(String apodo, String clave, String nombre, String ape, boolean disp, String z) {
        super(apodo, clave);
        this.nombre = nombre;
        this.apellidos = ape;
        this.disp = disp;
        switch(z){
            case "zona1"->{
                this.lugar = Zonas.zona1;
            }
            case "1"->{
                this.lugar = Zonas.zona1;
            }
            case "zona2"->{
                this.lugar = Zonas.zona2;
            }
            case "2"->{
                this.lugar = Zonas.zona2;
            }
            case "zona3"->{
                this.lugar = Zonas.zona3;
            }
            case "3"->{
                this.lugar = Zonas.zona3;
            }
            case "zona4"->{
                this.lugar = Zonas.zona4;
            }
            case "4"->{
                this.lugar = Zonas.zona4;
            }
            case "zona5"->{
                this.lugar = Zonas.zona5;
            }
            case "5"->{
                this.lugar = Zonas.zona5;
            }
        }
        
    }
    
    /**
     * Clase heredada de UserTaxi, nos dice que tipo es (Usuario o Taxista)
     * @return string "taxista"
     */
    @Override
    public String tipo() {
        return "taxista";
    }
    
    /**
     * Funcion sobre escrita de toString
     * @return "Apodo: " + this.apodo + "\t|Nombre: " + this.nombre + "\t|Apellidos: " + this.apellidos;
     */
    @Override
    public String toString(){
        
        String sal = "Apodo: " + this.apodo + "\t|Nombre: " + this.nombre + "\t|Apellidos: " + this.apellidos;
        
        if(this.lugar!= null){
            sal += "\t|Zona: " + this.lugar.toString();
        }
        
        if(this.matTaxi!=null){
            sal += "\t|Matricula: " + this.matTaxi;
        }
        return sal;
    }
}
