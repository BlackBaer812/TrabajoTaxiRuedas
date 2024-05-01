/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto.taxiruedas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class TaxiRuedas {

    public static void main(String[] args) {
        Map <String, Object> userTaxi = new HashMap<>();
        
        //anadir(userTaxi);
        
        userTaxi.put("Ma", new Usuario("Ma","m","Marc","RC"));
        userTaxi.put("An", new Taxista("An","a","Andrea","MM",true,"zona1"));
        
        System.out.println(userTaxi.get("Ma"));
        
        System.out.println(entrada(userTaxi));
        
    }
    
    /**
     * Función para añadir un usuario a Map de datos
     * @param uT Es el Map en el cual queremos guardar los datos
     */
    public static void anadir(Map<String,Object> uT){
        System.out.print("Nombre de usuario: ");
        String apodo = new Scanner (System.in).useLocale(Locale.US).next();
        System.out.print("Clave del usuario: ");
        String key = new Scanner(System.in).useLocale(Locale.US).next();
        System.out.print("Nombre: ");
        String nombre = new Scanner (System.in).useLocale(Locale.US).next();
        System.out.print("Apellidos: ");
        String ape = new Scanner (System.in).useLocale(Locale.US).next();
        System.out.println("Usuario/taxista");
        String tipo = new Scanner (System.in).useLocale(Locale.US).next();
        tipo = tipo.toLowerCase();
        switch(tipo.charAt(0)){
            case 'u' ->{
                Usuario user = new Usuario(apodo,key,nombre,ape);
                uT.put(user.getNombre(), user);
            }
            case 't' ->{
                Taxista taxi = new Taxista(apodo,key,nombre,ape);
                uT.put(taxi.getNombre(), taxi);
            }
        }
    }
    
    /**
     * Función para comprobar si la contraseña introducida es correcta
     * @param uT Map que se refiere donde están guardados todos los datos
     * @return Boolean si la contraseña introducida concuerda True, si no False
     */
    public static boolean entrada(Map<String,Object> uT){
        boolean sal = false;
        System.out.print("Usuario: ");
        String apodo = new Scanner (System.in).useLocale(Locale.US).next();
        String conGuard = ((UserTaxi) uT.get(apodo)).getKey();
        System.out.print("Contraseña: ");
        String conEnt = new Scanner (System.in).useLocale(Locale.US).next();
        if(conGuard.equals(conEnt)){
            sal = true;
        }
        return sal;
    }
    
    /**
     * Función para cargar los datos desde un archivo externo
     * @return devulve los datos guardaos para insertarlos en nuestra estructura de datos
     */
    public static HashMap<String,Object> cargaDatos(){
        HashMap<String,Object> carga = null;
        
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("club.data"))){
            /*Scanner s = new Scanner(inputStream);
            while(s.hasNext()){
                carga.add(s.next());
            }
            s.close();*/
            carga =  (HashMap<String,Object>) inputStream.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        
        return carga;
    }
    
    /**
     * Guarda los datos introducidos en la sesión en el archivo regTemp.data
     * @param lista Map (String, Object) con los datos que vamos a guardar
     */
    public static void guardarDatos(Map<String,Object> lista){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("club.data"))){
            /*for(Object reg:lista){
                outStream.writeObject(reg);
                
            }
            outStream.flush();
            outStream.close();*/
            
            out.writeObject(lista);
            out.flush();
            out.close();
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
