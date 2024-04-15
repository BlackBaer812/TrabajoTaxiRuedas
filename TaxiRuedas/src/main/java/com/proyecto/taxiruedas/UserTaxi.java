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
     * nombre de usuario
     */
    protected String nombre;
    /**
     * clave del usuario (Se deberá de implementar una en criptación)
     */
    protected String key;
    
    /**
     * Constructor del usuario
     * @param nombre nombre del usuario
     * @param clave contraseña del usuario
     */
    public UserTaxi(String nombre, String clave){
        this.nombre = nombre;
        this.key = clave;
    }
    
    /**
     * Devuelve si un usuario es igual a otro ya guardado
     * @param o se le introduce el objeto a comparar
     * @return true si coinciden usuario y clave, false si no coinciden usuario y clave
     */
    @Override
    public boolean equals(Object o){
        return this.nombre.equals(((UserTaxi) o).getNombre()) && this.key.equals(((UserTaxi) o).getKey());
    }

    /**
     * Nos devuelve el nombre del usuario
     * @return String nombre de usuario
     */
    public String getNombre() {
        return nombre;
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
     * @param nombre String nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return this.nombre.compareTo(((UserTaxi) o).getNombre());
    }
    
    
}
