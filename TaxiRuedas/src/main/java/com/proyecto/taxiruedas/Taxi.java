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
public class Taxi implements Serializable{
    /**
     * Matricula del taxi
     */
    private String matricula;
    
    /**
     * Tipo de vehiculo de la case enumerado TipoTaxi
     */
    private TipoTaxi tipo;
    
    /**
     * Apodo del taxista
     */
    private String apodoT;
    
    /**
     * constructor completo de taxi
     * @param mat matricula del taxi
     * @param t tipo de taxi
     * @param apoT Taxista que lo conduce
     */
    public Taxi(String mat,TipoTaxi t, String apoT){
        this.matricula = mat;
        this.apodoT = apoT;
        this.tipo = t;
    }
    
    /**
     * Constructor para el listado
     * @param mat matricula del taxi
     * @param t tipo de taxi
     */
    public Taxi(String mat, String t){
        this.matricula = mat;
        switch(t){
            case "estandar"->{
                this.tipo = TipoTaxi.estandar;
            }
            case "lujo" ->{
                this.tipo = TipoTaxi.lujo;
            }
            case "entrega" ->{
                this.tipo = TipoTaxi.entrega;
            }
        }
    }
    
    @Override
    public String toString(){
        String sal = "\t|Matricula: " + this.matricula + "\t|Tipo: " + this.tipo.toString();
        if(this.apodoT != null){
            sal += "\t|Taxista: " + this.apodoT;
        }
        return sal;
    }
}
