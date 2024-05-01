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
public class Usuario extends UserTaxi implements Serializable{
    
    /**
     * Nombre del usuario
     */
    private String nombre;
    
    /**
     * Apellido del usuario
     */
    private String apellidos;
    
    /**
     * Lugar en el que se encuentra el cliente
     */
    private Zonas lugar;
    
    /**
     * Construtor de la clase Usuario, al extender de UserTaxi usa su constructor
     * más las variables de nombre y ape (apellidos)
     * @param apodo Nombre de usuario en la aplicación
     * @param clave contraseña que tiene en la aplicación
     * @param nombre Nombre del usuario
     * @param ape Apellidos del usuario
     */
    public Usuario(String apodo, String clave, String nombre, String ape) {
        super(apodo, clave);
        this.nombre = nombre;
        this.apellidos = ape;
    }

    /**
     * Construtor de la clase Usuario, al extender de UserTaxi usa su constructor
     * más las variables de nombre y ape (apellidos)
     * @param apodo Nombre de usuario en la aplicación
     * @param clave contraseña que tiene en la aplicación
     * @param nombre Nombre del usuario
     * @param ape Apellidos del usuario
     * @param z Zona en la que se encuentra el usuario
     */
    public Usuario(String apodo, String clave, String nombre, String ape, String z) {
        super(apodo, clave);
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
    }
    
    /**
     * Clase heredada de UserTaxi, nos dice que tipo es (Usuario o Taxista)
     * @return string "usuario"
     */
    @Override
    public String tipo() {
        return "usuario";
    }
    
    /**
     * Funcion sobre escrita de toString
     * @return "Apodo: " + this.apodo + "\t|Nombre: " + this.nombre + "\t|Apellidos: " + this.apellidos;
     */
    @Override
    public String toString(){
        return "Apodo: " + this.apodo + "\t|Nombre: " + this.nombre + "\t|Apellidos: " + this.apellidos + "\t|Zona: " + this.lugar.toString();
    }
}
