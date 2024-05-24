/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.taxiruedas;

import java.io.Serializable;

/**
 * Clase padre para los usuarios de la aplicacion, tanto los clientes como los taxistas
 * @author Marcos
 * @version 1.0
 */
public abstract class UserTaxi implements Serializable, Comparable{
    /**
     * apodo de usuario
     */
    protected String apodo;
    /**
     * clave del usuario (Se deberá de implementar una en criptación)
     */
    protected String key;
    
    /**
     * Constructor del usuario
     * @param nombre apodo del usuario
     * @param clave contraseña del usuario
     */
    public UserTaxi(String nombre, String clave){
        this.apodo = nombre;
        this.key = clave;
    }
    
    /**
     * Constructor para usuario al iniciar la sesión
     * @param nombre apodo del usuario
     */
    public UserTaxi(String nombre){
        this.apodo = nombre;
    }
    
    /**
     * Devuelve si un usuario es igual a otro ya guardado
     * @param o se le introduce el objeto a comparar
     * @return true si coinciden usuario y clave, false si no coinciden usuario y clave
     */
    @Override
    public boolean equals(Object o){
        return this.apodo.equals(((UserTaxi) o).getApodo()) && this.key.equals(((UserTaxi) o).getKey());
    }

    /**
     * Nos devuelve el apodo del usuario
     * @return String apodo de usuario
     */
    public String getApodo() {
        return apodo;
    }

    /**
     * Nos devulve la clave del usuario
     * @return String contraseña del usuario
     */
    public String getKey() {
        return key;
    }

    /**
     * Nombre nuevo del usuario
     * @param nombre String nuevo apodo
     */
    public void setNombre(String nombre) {
        this.apodo = nombre;
    }

    /**
     * Contraseña nueva
     * @param key String nueva contraseña
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Compara si un usuario esta antes que otro
     * @param o Se le introduce el objeto con el queremos comparar
     * @return -1 si el usuario base va antes, 0 si son iguales y 1 si va primero el usuario con el comparamos
     */
    @Override
    public int compareTo(Object o) {
        return this.apodo.compareTo(((UserTaxi) o).getApodo());
    }
    
    /**
     * Metodo abstrabto para implementar en las clases hijas
     * @return Devuelve que tipo de usuario es (taxista,usuario)
     */
    public abstract String tipo();
}
