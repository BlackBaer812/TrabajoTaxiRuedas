/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.taxiruedas;

import java.util.ArrayList;
import java.util.List;

/**
 * Recoge los datos de un comentario
 * @author Marcos
 */
public class Comentario {
    /**
     * Id del comentario
     */
    private int id;
    /**
     * Puntuación que se le da al viaje
     */
    private int puntuacion;
    /**
     * Comentario escrito
     */
    private String comentario;
    /**
     * Id del viaje sobre el que se realiza el comentario
     */
    private int id_viaje;
    
    /**
     * Constructor completo
     * @param idC id del comentario
     * @param punt puntuación del viaje
     * @param coment comentario del viaje
     * @param idV id del viaje comentado
     */
    public Comentario(int idC, int punt, String coment,int idV){
        this.id=idC;
        this.puntuacion=punt;
        this.comentario=coment;
        this.id_viaje=idV;
    }
    
    /**
     * Constructor para solo 3 elementos para consultas que no requieran todos los campos de viaje
     * @param idV ID del viaje comentado
     * @param punt puntuación que tiene ese viaje
     * @param coment Comentario del viaje
     */
    public Comentario(int idV, int punt, String coment){
        this.id_viaje = idV;
        this.puntuacion = punt;
        this.comentario = coment;
    }

    /**
     * Getter de la puntuación que tiene un comentario
     * @return Devulve la puntuación del viaje
     */
    public int getPuntuacion() {
        return puntuacion;
    }
    
    /**
     * Getter del comentario que tiene ese id (opinion de hasta 240 caracteres)
     * Cada 24 caracteres metera un retorno de carro
     * @return Devuelve el comentario formateado para que no salga todo en una sola linea
     */
    public String getComentario() {
        String sal = "";
        
        List<Character> lC =new ArrayList<>();
        
        int letras=0;
        
        for(int i = 0; i<this.comentario.length(); i++){
            lC.add(this.comentario.charAt(i));
        }
        
        for(char c:lC){
            sal += c;
            letras++;
            if(letras == 24){
                sal += "\r";
            }
        }
        
        return sal;
        
    }
    
    
    
    @Override
    public String toString(){
        String sal ="Id del viaje: " + this.id_viaje + "\t|Puntuación del viaje" + this.puntuacion;
        if(!"".equals(this.comentario)){
            sal += "\t|Comentario del viaje " + this.comentario;
        }
        return sal;
    }
}
