/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.taxiruedas;

/**
 *
 * @author Marcos
 */
public class Taxista extends UserTaxi{
    
    /**
     * Nombre del usuario
     */
    private String nombre;
    
    /**
     * Apellido del usuario
     */
    private String apellidos;
    
    
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
        return "Apodo: " + this.apodo + "\t|Nombre: " + this.nombre + "\t|Apellidos: " + this.apellidos;
    }
}
