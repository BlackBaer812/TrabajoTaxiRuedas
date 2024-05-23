/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto.taxiruedas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class TaxiRuedas {

    public static void main(String[] args) {
        login();
        
    }
    
    
    /*************************************************/
    /*************************************************/
    /********************* login *********************/
    /*************************************************/
    /*************************************************/
    
    /**
     * login para iniciar sesión, crear cuenta o recuperar cuenta (no programado aún)
     */
    public static void login(){
        int op;
        
        int fallos = 0;
        
        do{
            System.out.println("________________________");
            System.out.println("Menu de selección:");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registarse");
            System.out.println("3. Salir");
            System.out.print("Introduce una opción: ");
            try{
                op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            }
            catch(InputMismatchException e){
                op = 0;
            }
            switch(op){
                case 1->{//Iniciar sesion
                    Connection conexion = conectarBD();
                    while(fallos<3){
                        ArrayList <String> sal = iSesion(conexion);
                        switch(sal.get(1)){
                            case "ND" ->{
                                System.out.println("No existe el usuario");
                                
                                fallos=3;
                            }
                            case "usuario" ->{
                                //Menu de usuario
                                System.out.println("Inicio de sesion existoso como usuario");
                                //Aqui va el menu del usuario
                                menuUsu(conexion,sal.get(0));
                                fallos=3;
                            }
                            case "taxista" ->{
                                //Menu de taxista
                                System.out.println("Inicio de sesion existoso como taxista");
                                disponible(conexion,sal.get(0));
                                menuTaxista(conexion,sal.get(0));
                                fallos=3;
                            }
                        }
                    }
                    cerrarConexion(conexion);
                }
                case 2->{//Crear usuario
                    Connection conexion = conectarBD();
                    System.out.println("Crear Cuenta:");
                    System.out.println("1. Usuario");
                    System.out.println("2. Taxista");
                    System.out.print("Introduce una opción: ");
                    int op1 = new Scanner(System.in).useLocale(Locale.US).nextInt();
                    switch(op1){
                        case 1->{//Crear cuenta usuario
                            System.out.print("Usuario:");
                            String usu = new Scanner(System.in).useLocale(Locale.US).nextLine();
                            if(!existeReg(conexion, usu, "usuario","apodo")){
                                if(crearUsu(conexion,usu)){
                                    System.out.println("Usuario creado");
                                }
                            }
                        }
                        case 2->{//Crear cuenta taxista
                            System.out.print("Usuario:");
                            String usu = new Scanner(System.in).useLocale(Locale.US).nextLine();
                            if(!existeReg(conexion, usu, "taxista","apodo")){
                                if(crearTaxista(conexion,usu)){
                                    System.out.println("Usuario creado");
                                }
                            }
                        }
                    }
                    cerrarConexion(conexion);
                }
                case 3 ->{ //Salir del programa
                    System.out.println("Saliendo del programa.");
                }
                default ->{
                    System.out.println("Esta opción no esta disponible.");
                }
            }
            fallos = 0;
        }while(op!=4);
    }
    
    
    /**
     * Menu del usuario cuando se conecta
     * @param conexion conexcion usada para acceder a la base de datos
     * @param apo apodo del usuario 
     */
    public static void menuUsu(Connection conexion,String apo){
        Usuario u1 = selectUsu(conexion,apo);
        int op;
        do{
            System.out.println("Menu Usuario");
            System.out.println("1. Cambiar de zona.");
            System.out.println("2. Realizar reserva.");
            System.out.println("3. Taxistas disponibles.");
            System.out.println("4. Historial de viajes.");
            System.out.println("5. Realizar comentarios.");
            System.out.println("6. Historial de comentarios.");
            System.out.println("7. Cerrar Sesion");
            System.out.print("Introduce una opción: ");
            try{
                op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            }
            catch(InputMismatchException e){
                op = 0;
            }
            switch(op){
                case 1->{//cambia de zona
                    cambioZona(conexion, u1);
                }
                case 2 ->{//realizar reserva
                    crearReserva(conexion,u1);
                }
                case 3 ->{//Listado de taxistas
                    List<Taxista> lTaxista = listaTaxistas(conexion);
                    if(!lTaxista.isEmpty()){
                        for(Taxista t:lTaxista){
                            System.out.println(t);
                        }  
                    }
                    else{
                        System.out.println("Actualmente no hay ningún taxista disponible."
                                + "\rDisculpe las molestias.");
                    }
                }
                case 4 ->{//Historial de viajes (no reservas)
                    List<Viaje> lViajes= listaViajesU(conexion,u1);
                    if(!lViajes.isEmpty()){
                        for(Viaje v:lViajes){
                            System.out.println(v);
                        }
                    }
                    else{
                        System.out.println("Aún no ha realizado ningun viaje con nostros.");
                    }
                    
                }
                case 5 ->{//Realizar comentarios
                    crearComentario(conexion,u1);
                }
                case 6 ->{//Lista comentarios
                    List<String> lComents= listaComentRealU(conexion,u1);
                    if(!lComents.isEmpty()){
                        for(String v:lComents){
                            System.out.println(v);
                        }
                    }
                    else{
                        System.out.println("Aún no ha realizado ningun comentario.");
                    }
                }
                case 7 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                }
                default->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op != 7);
    }
    
    /**
     * Menu taxista para adminsitrar sus acciones
     * @param conexion conexión a la base de datos
     * @param apo apodo del taxista
     */
    public static void menuTaxista(Connection conexion,String apo){
        Taxista u1 = selectTaxista(conexion, apo);
        int op;
        
        Viaje v1 = null;
        
        do{
            System.out.println("Menu Usuario");
            System.out.println("1. Cambiar de zona.");
            System.out.println("2. Menu taxi.");
            System.out.println("3. Lista de reservas.");
            System.out.println("4. Aceptar reserva.");
            System.out.println("5. Historial de viajes.");
            System.out.println("6. Finalizar viaje.");
            System.out.println("7. Lista de comentarios.");
            System.out.println("8. Cerrar Sesion.");
            System.out.print("Introduce una opción: ");
            try{
                op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            }
            catch(InputMismatchException e){
                op = 0;
            }
            switch(op){
                case 1 ->{//cambiar de zona
                    cambioZona(conexion, u1);
                }
                case 2 ->{ //menu taxi
                    System.out.println("Acceciendo a menu taxi.");
                    menuTaxi(conexion,u1);
                }
                
                case 3 ->{//Lista de reservas
                    if(compruebaReservas(conexion)>0){
                        List <Reserva> lResev = listaReservas(conexion,"reserva");
                        for(Reserva reserv:lResev){
                            System.out.println(reserv);
                        }
                    }
                    else{
                        System.out.println("No hay ninguna reserva a la espera de ser aceptada");
                    }
                }
                case 4 ->{//Aceptar viaje
                    if(compruebaReservas(conexion)>0){
                        if(viajesActivos(conexion,u1)>0){
                            System.out.println("Antes de poder aceptar otro viaje debe de completar los anteriores");
                        }
                        else{
                            List <Reserva> lResev = listaReservas(conexion,"reserva");
                            for(Reserva reserv:lResev){
                                System.out.println(reserv);
                            }
                            System.out.print("¿Qué viaje quiere aceptar? (ID del viaje)");
                            int idR = new Scanner(System.in).useLocale(Locale.US).nextInt();
                            List <Taxi> lTaxis = listaTaxis(conexion,u1);
                            if(!lTaxis.isEmpty()){
                                for(Taxi tax:lTaxis){
                                    System.out.println(tax);
                                }
                            }
                            System.out.print("¿Qué vehiculo va a usar (Matrícula)?");
                            String mat = new Scanner(System.in).useLocale(Locale.US).nextLine();
                            aceptReserva(conexion,u1,mat,idR);
                            noDisponible(conexion,u1.getApodo());
                            v1 = viajeActivo(conexion,u1);
                        }
                    }
                    else{
                        System.out.println("No hay reserva que pueda ser aceptada.");
                    }
                }
                case 5 ->{//Lista de viajes
                    List <Viaje> lViajes = listaViajesT(conexion,u1);
                    if(!lViajes.isEmpty()){
                        for(Viaje viaj:lViajes){
                            System.out.println(viaj);
                        }
                    }
                    else{
                        System.out.println("Aún no has terminado ningún viaje.");
                    }
                }
                case 6 ->{//Finalizar viaje aceptado
                    if(v1!=null){
                        finViaje(conexion,u1,v1.getId());
                        disponible(conexion,u1.getApodo());
                    }
                    else{
                        System.out.println("No tienes ningún viaje por finalizar.");
                    }
                }
                case 7 ->{//Lista de comentarios
                    List<String> lComents= listaComentRealT(conexion,u1);
                    if(!lComents.isEmpty()){
                        for(String v:lComents){
                            System.out.println(v);
                        }
                    }
                }
                case 8 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                    if(viajesActivos(conexion,u1)>0){
                        if(v1 == null){
                            v1 = viajeActivo(conexion,u1);
                        }
                        finViaje(conexion,u1,v1.getId());
                    }
                    noDisponible(conexion,apo);
                }
                default->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op != 8);
    }
    
    /**
     * Menu del taxista para administrar sus taxis
     * @param conexion Conexión a la base de datos
     * @param u1 Usuario que se a conectado al menu
     */
    public static void menuTaxi(Connection conexion, Taxista u1){
        
        int op;
        do{
            System.out.println("Menu Taxi:");
            System.out.println("1. Añadir taxi.");
            System.out.println("2. Lista de taxi.");
            System.out.println("3. Modificar taxi.");
            System.out.println("4. Eliminar taxi.");
            System.out.println("5. Cerrar Sesion");
            System.out.print("Introduce una opción: ");
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1->{ //añadir taxi
                    crearTaxi(conexion,u1);
                    System.out.println("Taxi creado.");
                }
                case 2->{ //Lista de taxis
                    List <Taxi> lTaxis = listaTaxis(conexion,u1);
                    if(!lTaxis.isEmpty()){
                        for(Taxi tax:lTaxis){
                            System.out.println(tax);
                        }
                    }
                }
                case 3->{ //Modificar taxi
                    modTaxi(conexion);
                }
                case 4->{ //Eliminar taxi
                    eliminarReg(conexion,"taxi","matricula");
                }
                case 5->{
                    System.out.println("Volviendo al menu de usuario.");
                }
                default ->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op!=5);
    }
    
    /**
     * En inicio de sesion preguntaremos que tipo de usurio es el que inicia sesión
     * @param conexion Es la conexión a la base de datos.
     * @return En el estring de salida en el campo 0 tenemos el usuario con el 
     * que iniciamos la sesion y en el 1 si es usuario, taxista o ND.
     */
    public static ArrayList<String> iSesion(Connection conexion){
        ArrayList <String> sal = new ArrayList<>();
        System.out.print("Usuario: ");
        String usu = new Scanner(System.in).useLocale(Locale.US).nextLine();
        usu = usu.toLowerCase();
        sal.add(usu);
        if(existeReg(conexion,usu,"usuario","apodo")){
            System.out.print("Contraseña: ");
            String clave = new Scanner(System.in).useLocale(Locale.US).nextLine();
            clave = clave.toLowerCase();
            if(existeReg(conexion, clave,"usuario","clave")){
                sal.add("usuario");
            }
        }
        if(existeReg(conexion,usu,"taxista","apodo")){
            System.out.print("Contraseña: ");
            String clave = new Scanner(System.in).useLocale(Locale.US).nextLine();
            clave = clave.toLowerCase();
            if(existeReg(conexion, clave,"taxista","clave")){
                sal.add("taxista");
            }
        }
        if(sal.size()==1){
            sal.add("ND");
        }
        return sal;
    }
    
    /**
     * Crea el objeto de usuario que se va a usar
     * @param conexion conexión a la base de datos
     * @param usu apodo del usuario que ha iniciado sesión
     * @return usuario para poder acceder a su zona
     */
    public static Usuario selectUsu(Connection conexion, String usu){
        Usuario e = null;
        String sql = String.format("SELECT * FROM usuario WHERE apodo = ?");
        
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, usu);
            ResultSet res = pstmt.executeQuery();
                if(res.next()){
                    String apo = res.getString("apodo");
                    String nombre = res.getString("nombre");
                    String apellidos = res.getString("apellidos");
                    String zona = res.getString("zona");
                    e = new Usuario (apo,nombre,apellidos,zona);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        return e;
    }
    
    /**
     * Crea el objeto Taxista que ha iniciado sesión
     * @param conexion conexión con base de datos
     * @param usu apodo del taxista que ha iniciado sesión
     * @return taxista que ha iniciado sesión para saber quien es y poder acceder a su zona
     */
    public static Taxista selectTaxista(Connection conexion, String usu){
        Taxista e = null;
        String sql = String.format("SELECT * FROM taxista WHERE apodo = ?");
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, usu);
            ResultSet res = pstmt.executeQuery();
                if(res.next()){
                    String apo = res.getString("apodo");
                    String nombre = res.getString("nombre");
                    String apellidos = res.getString("apellidos");
                    String zona = res.getString("zona");
                    e = new Taxista (apo,nombre,apellidos,zona);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        return e;
    }
    
    /**
     * Función para aceptar una reserva
     * @param conexion Conexión a la base de datos
     * @param u1 taxista que acepta los viajes
     * @param mat amtricula del vehiculo que realizara el viaje
     * @param idReserva id de la reserva
     */
    public static void aceptReserva(Connection conexion, Taxista u1, String mat, int idReserva){
        String sql = "CALL aceptar_reserva (?,?,?)";
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            pstmt.setString(2,mat);
            pstmt.setInt(3, idReserva);
            
            pstmt.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * función para recuperar que viaje tiene activo en este momento
     * @param conexion conexión a la base de datos
     * @param u1 taxista que esta conectado ahora mismo
     * @return objeto de la clase Viaje
     */
    public static Viaje viajeActivo(Connection conexion, Taxista u1){
        Viaje e = null;
        String sql = String.format("SELECT * FROM viaje WHERE apodo_taxista = ? and finalizado = 0");
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            ResultSet res = pstmt.executeQuery();
                if(res.next()){
                    int id = res.getInt("ID");
                    String fecha = res.getString("fecha");
                    DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate f = LocalDate.parse(fecha, us);
                    String aUsu = res.getString("apodo_usu");
                    String zI = res.getString("ZonaInicio");
                    String zF = res.getString("ZonaFinal");
                    String aTaxi = res.getString("apodo_taxista");
                    String mTaxi = res.getString("mat_taxi");
                    int p = res.getInt("precio");
                    e = new Viaje (id,aUsu,f,zI,zF,aTaxi,mTaxi,p);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        return e;
    }
    
    public static int viajesActivos(Connection conexion, Taxista u1){
        int sal = 0;
        String sql = String.format("SELECT count(*) FROM viaje WHERE apodo_taxista = ? and finalizado = 0");
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            ResultSet res = pstmt.executeQuery();
                if(res.next()){
                    sal = res.getInt(1);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        return sal;
        
    } 
    
    /**
     * Pone disponible al taxista
     * @param conexion conexion a la base de datos
     * @param apo apodo del taxista que se conecta
     */
    public static void disponible(Connection conexion, String apo){
       
        String sql=String.format("UPDATE taxista SET disponible='1' WHERE apodo = ?");
        try{
            
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, apo);
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    
    /**
     * Pone como no disponible al taxista
     * @param conexion conexion a la base de datos
     * @param apo apodo del taxista que se desconecta 
     */
    public static void noDisponible(Connection conexion, String apo){
        String sql=String.format("UPDATE taxista SET disponible='0' WHERE apodo = ?");
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, apo);
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        } 
    }
    
    /**
     * Función para crear la lista de reservas actuales
     * @param conexion conexión a la base de datos
     * @param tabla tabla donde miramos
     * @return Lista de las reservas
     */
    public static List<Reserva> listaReservas(Connection conexion, String tabla){
        List<Reserva> sal = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s WHERE Aceptado = 0" , tabla);

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int id = res.getInt("ID");
                String apodo = res.getString("apodo");
                DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(res.getString("fecha"),us);
                String zI = res.getString("ZonaInicio");
                String zF = res.getString("ZonaFinal");
                String tipo = res.getString("tipotaxi");
                int p = res.getInt("precio");
                if(tipo != null){
                    Reserva e = new Reserva (id,apodo,fecha,zI,zF,tipo,p);
                    sal.add(e);
                }
                
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        return sal;
    }
    
    /**
     * Función para recuperar la lista de viajes finalizados
     * @param conexion conexion a la BBDD
     * @param u1 Taxista que consulta la lista
     * @return Lista de los Viajes no finalizados
     */
    public static List<Viaje> listaViajesT(Connection conexion,Taxista u1){
        List<Viaje> sal = new ArrayList<>();
        String sql = String.format("SELECT * FROM viaje WHERE finalizado = 1 and apodo_taxista = ?");

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            
            pstmt.setString(1, u1.getApodo());
            
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int id = res.getInt("ID");
                String aUsu = res.getString("apodo_usu");
                DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(res.getString("fecha"),us);
                String zI = res.getString("ZonaInicio");
                String zF = res.getString("ZonaFinal");
                String aTaxista = res.getString("apodo_taxista");
                String mTaxi = res.getString("mat_taxi");
                int p = res.getInt("precio");
                Viaje e = new Viaje (id,aUsu,fecha,zI,zF,aTaxista,mTaxi,p);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    }
    
    /**
     * Lista de viajes que ha realizado un usuario
     * @param conexion conexion a la BBDD
     * @param u1 Taxista que consulta la lista
     * @return ArrayList de viajes con los datos solicitados
     */
    public static List<Viaje> listaViajesComentU(Connection conexion,Usuario u1){
        List<Viaje> sal = new ArrayList<>();
        String sql = String.format("SELECT * FROM viaje WHERE finalizado = 1 and id NOT IN (Select id_viaje from comentario) and apodo_usu = ?");

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            
            pstmt.setString(1, u1.getApodo());
            
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int id = res.getInt("ID");
                String aUsu = res.getString("apodo_usu");
                DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(res.getString("fecha"),us);
                String zI = res.getString("ZonaInicio");
                String zF = res.getString("ZonaFinal");
                String aTaxista = res.getString("apodo_taxista");
                String mTaxi = res.getString("mat_taxi");
                int p = res.getInt("precio");
                Viaje e = new Viaje (id,aUsu,fecha,zI,zF,aTaxista,mTaxi,p);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    }
    
    /**
     * Función para obtener los viajes realizados por el usuario
     * @param conexion conexión a la BBDD
     * @param u1 usuario que se ha conectado
     * @return Lista con los viajes realizados por el usuario
     */
    public static List<Viaje> listaViajesU(Connection conexion,Usuario u1){
        List<Viaje> sal = new ArrayList<>();
        String sql = String.format("SELECT * FROM viaje WHERE finalizado = 1 and apodo_usu = ?");

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            
            pstmt.setString(1, u1.getApodo());
            
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int id = res.getInt("ID");
                String aUsu = res.getString("apodo_usu");
                DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(res.getString("fecha"),us);
                String zI = res.getString("ZonaInicio");
                String zF = res.getString("ZonaFinal");
                String aTaxista = res.getString("apodo_taxista");
                String mTaxi = res.getString("mat_taxi");
                int p = res.getInt("precio");
                Viaje e = new Viaje (id,aUsu,fecha,zI,zF,aTaxista,mTaxi,p);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    }
    
    /**
     * Función que lista el viaje y sus comentarios para el usuario
     * @param conexion Conexión a la BBDD
     * @param u1 Usaurio que realiza la petición a la base de datos
     * @return List con los string que contienen la información del viaje y su comentario
     */
    public static List<String> listaComentRealU(Connection conexion, Usuario u1){
        List<String> sal = new ArrayList<>();
        
        String sql = String.format("SELECT id_viaje,fecha,ZonaInicio,ZonaFinal,apodo_taxista,mat_taxi,puntuacion,comentario FROM viaje INNER JOIN comentario on viaje.ID=comentario.id_viaje where apodo_usu = ?");

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            
            pstmt.setString(1, u1.getApodo());
            
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int idV = res.getInt("id_viaje");
                DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(res.getString("fecha"),us);
                String zI = res.getString("ZonaInicio");
                String zF = res.getString("ZonaFinal");
                String aTaxista = res.getString("apodo_taxista");
                String mTaxi = res.getString("mat_taxi");
                int punt = res.getInt("puntuacion");
                String coment = res.getString("comentario");
                Comentario com = new Comentario(idV,punt,coment);
                String e = "Fecha del viaje: " + fecha + "\t|Zona de inicio: " + zI
                        + "\t|Zona final: " + zF + "\t|Apodo del taxista: " + aTaxista
                        + "\t|Matricula del taxi: " + mTaxi + "\t|Puntuación: " + punt
                        + "\t|Comentario: \r" + com.getComentario() + "\r";
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    }
    
    /**
     * Función que lista el viaje y sus comentarios para el taxista
     * @param conexion Conexión a la BBDD
     * @param u1 Taxista que realiza la petición a la base de datos
     * @return List con los string que contienen la información del viaje y su comentario
     */
    public static List <String> listaComentRealT(Connection conexion, Taxista u1){
        List<String> sal = new ArrayList<>();
        
        String sql = String.format("SELECT id_viaje,fecha,ZonaInicio,ZonaFinal,apodo_taxista,mat_taxi,puntuacion,comentario FROM viaje INNER JOIN comentario on viaje.ID=comentario.id_viaje where apodo_taxista = ?");

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            
            pstmt.setString(1, u1.getApodo());
            
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int idV = res.getInt("id_viaje");
                DateTimeFormatter us = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fecha = LocalDate.parse(res.getString("fecha"),us);
                String zI = res.getString("ZonaInicio");
                String zF = res.getString("ZonaFinal");
                String mTaxi = res.getString("mat_taxi");
                int punt = res.getInt("puntuacion");
                String coment = res.getString("comentario");
                Comentario com = new Comentario(idV,punt,coment);
                String e = "Fecha del viaje: " + fecha + "\t|Zona de inicio: " + zI
                        + "\t|Zona final: " + zF + "\t|Matricula del taxi: " + mTaxi 
                        + "\t|Puntuación: " + punt
                        + "\t|Comentario: \r" + com.getComentario() + "\r";
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    } 
    
    /**
     * Función para crear comentarios en la base de datos
     * @param conexion conexión a la BBDD
     * @param u1 Usuario que crea el comentario
     */
    public static void crearComentario(Connection conexion, Usuario u1){
        
        /*
        Viajes realizados y que puede comentar
        */
        List<Viaje> lViajes= listaViajesComentU(conexion,u1);
        for(Viaje v:lViajes){
            System.out.println(v);
        }
        if (!lViajes.isEmpty()) {
            //Datos introducidos
            System.out.print("Id del viaje que quiere comentar: ");
            int idV = new Scanner(System.in).useLocale(Locale.US).nextInt();

            System.out.print("Puntuación del 1 al 5 (en número): ");
            int punt = new Scanner(System.in).useLocale(Locale.US).nextInt();

            if (punt < 1) {
                punt = 1;
            }

            if (punt > 5) {
                punt = 5;
            }

            System.out.println("Comentario(240 caracteres como maximo): ");
            String coment = new Scanner(System.in).useLocale(Locale.US).nextLine();
            if (coment.isEmpty()) {
                coment = "No comment.";
            }
            if (coment.length() > 240) {
                coment = cuentaLetras(coment);
            }

            //Query para la BBDD
            String sql = "INSERT INTO comentario(puntuacion, comentario, id_viaje) "
                    + "VALUES (?,?,?)";

            try {
                PreparedStatement pstmt = conexion.prepareCall(sql);

                pstmt.setInt(1, punt);
                pstmt.setString(2, coment);
                pstmt.setInt(3, idV);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("No tiene ningún viaje finalizado nuevo para comentar.");
        }
    }
    
    /**
     * Finaliza el viaje activo
     * @param conexion conexión a la BBDD
     * @param u1 taxista que ha iniciado la sesión
     * @param idV Id del viaje
     */
    public static void finViaje(Connection conexion, Taxista u1, int idV){
        String sql=String.format("UPDATE viaje SET finalizado='1' WHERE apodo_taxista = ? and ID = ?");
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            pstmt.setInt(2, idV);
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Funcion para crear una lista de taxis de un mismo taxista
     * @param conexion conexión a la base de datos
     * @param u1 usuario por el que buscaremos
     * @return Lista de taxis pertenecientes a un taxista
     */
    public static List<Taxi> listaTaxis(Connection conexion, Taxista u1){
        List<Taxi> sal = new ArrayList<>();
        String sql = String.format("SELECT matricula,tipo FROM taxi WHERE apodotaxi = \'%s\'" , u1.getApodo());

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String matricula = res.getString("matricula");
                String tipo = res.getString("tipo");
                Taxi e = new Taxi (matricula,tipo);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        return sal;
    }
    
    /**
     * Función para crear una lista de taxistas
     * @param conexion conexión a la base de datos

     * @return devuelve una lista de taxistas disponibles
     */
    public static List<Taxista> listaTaxistas(Connection conexion){
        List<Taxista> sal = new ArrayList<>();
        String sql = String.format("SELECT apodo,nombre,apellidos,zona FROM taxista where disponible = 1");

        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String apodo = res.getString("apodo");
                String nombre = res.getString("nombre");
                String ape = res.getString("apellidos");
                String z = res.getString("zona");
                Taxista e = new Taxista (apodo,nombre,ape,z);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        return sal;
    }
    
    /**
     * Funcion para crear un taxi relacionado con un taxista
     * @param conex conexión a la base de datos
     * @param u1 Taxista que crea el taxi
     */
    public static void crearTaxi(Connection conex, Taxista u1){
        System.out.print("Matricula: ");
        String mat = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.println("Tipo de taxi: ");
        System.out.println("1. Estandar");
        System.out.println("2. Lujo");
        System.out.println("3. Entrega");
        System.out.print("Introduce una opción:");
        int op = new Scanner(System.in).useLocale(Locale.US).nextInt();
        String tipo = "";
        switch(op){
            case 1->{
                tipo = "estandar";
            }
            case 2->{
                tipo = "lujo";
            }
            case 3->{
                tipo = "entrega";
            }
            default ->{
                System.out.println("Opcion no aceptada");
            }
        }
        
        String sql=String.format("INSERT INTO taxi(matricula, tipo,apodotaxi) "
                + "VALUES (?,?,?)");
        try{
            PreparedStatement pstmt = conex.prepareStatement(sql);
            pstmt.setString(1, mat);
            pstmt.setObject(2, tipo);
            pstmt.setString(3, u1.getApodo());
            
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Funcion para modificar el tipo de taxi que es
     * @param conexion conexion a la base de datos
     */
    public static void modTaxi(Connection conexion){
        System.out.println("¿Que taxi quiere modificar? (Introduzca la matricula)");
        String mat = new Scanner(System.in).useLocale(Locale.US).nextLine();
        if(existeReg(conexion,mat,"taxi","matricula")){
            System.out.println("¿Qué tipo de vehículo es?");
            System.out.println("1. Estandar");
            System.out.println("2. Lujo");
            System.out.println("3. Entrega");
            System.out.print("Introduce una opción:");
            int op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            String tipo = "";
            switch(op){
                case 1->{
                    tipo = "estandar";
                }
                case 2->{
                    tipo = "lujo";
                }
                case 3->{
                    tipo = "entrega";
                }
                default ->{
                    System.out.println("Opcion no aceptada");
                }
            }
            
            if(!"".equals(tipo)){
                String sql=String.format("UPDATE taxi SET tipo=? WHERE matricula=?");
                try{
                    PreparedStatement pstmt = conexion.prepareStatement(sql);
                    pstmt.setString(1, tipo);
                    pstmt.setString(2, mat);

                    pstmt.executeUpdate();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                System.out.println("Modificación realizada con exito."); 
            }
            else{
                System.out.println("No se ha podido realizar la modificación porque el tipo especificado no existe.");
            }
        }
        else{
            System.out.println("Ese taxi no existe");
        }
    }
    
    /**
     * Elimina un registro de una tabla
     * @param conexion conexion a la base de datos
     * @param tabla Tabla de donde borramos
     * @param columna Columna de PK
     */
    public static void eliminarReg(Connection conexion,String tabla, String columna){
        System.out.println("¿Que registro quiere eleminar?");
        String mat = new Scanner(System.in).useLocale(Locale.US).nextLine();
        if(existeReg(conexion,mat,"taxi","matricula")){
            String sql=String.format("DELETE FROM %s WHERE %s = ?",tabla,columna);
            
            try{
                PreparedStatement pstmt = conexion.prepareCall(sql);
                pstmt.setString(1, mat);
                System.out.println(pstmt);
                pstmt.executeUpdate();
                System.out.println("Registro borrado correctamente de " + tabla);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("El registro no existe.");
        }
    }
    
    /**
     * Función para crear una reserva en la BBDD
     * @param conex conexión a la BBDD
     * @param u1 usuario que crea la reserva siendo este objeto de tipo Usuario
     * @return true si lo crea, false si no
     */
    public static boolean crearReserva(Connection conex, Usuario u1){
        boolean sal = false;
        
        System.out.print("¿A que zona quiere ir? ");
        String zFinal = new Scanner(System.in).useLocale(Locale.US).nextLine();
        String zF = compruebaZona(zFinal);
        
        System.out.println("¿Que tipo de taxi quiere?");
        System.out.println("1. Estandar");
        System.out.println("2. Lujo");
        System.out.println("3. Entrega");
        System.out.print("Introduce una opción:");
        int op = new Scanner(System.in).useLocale(Locale.US).nextInt();
        String tipo = "";
        switch(op){
            case 1->{
                tipo = "estandar";
            }
            case 2->{
                tipo = "lujo";
            }
            case 3->{
                tipo = "entrega";
            }
            default ->{
                System.out.println("Opcion no aceptada");
            }
        }
        
        LocalDate ahora = LocalDate.now();
        int precio = calculoTarifa(Integer.parseInt(String.valueOf(u1.getLugarS().charAt(4))),Integer.parseInt(String.valueOf(zF.charAt(4))));
        
        System.out.print("El precio de su reserva sera de: " + precio 
                + " ¿Acepta este precio? (Si/No). ");
        String acept = new Scanner(System.in).useLocale(Locale.US).next();
        acept = acept.toLowerCase();
        if(acept.charAt(0)=='s'){
            String sql="INSERT INTO reserva(apodo, fecha, ZonaInicio, ZonaFinal, aceptado,tipotaxi,precio) "
                + "VALUES (?,?,?,?,?,?,?)";
            try{
                PreparedStatement pstmt = conex.prepareStatement(sql);
                pstmt.setString(1, u1.getApodo());
                pstmt.setObject(2, ahora);
                pstmt.setString(3, u1.getLugarS());
                pstmt.setString(4, zF);
                pstmt.setInt(5, 0);
                pstmt.setString(6, tipo);
                pstmt.setInt(7, precio);

                pstmt.executeUpdate();
                sal = true;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("La reserva no se ha realizado");
        }
        return sal;
    }
    
    /**
     * Función para saber si hay reservas en el sistema
     * @param conexion conexion a la base de datos
     * @return cantidad de reservas sin aceptar en el sistema
     */
    public static int compruebaReservas(Connection conexion){
        int sal = 0;
        String sql = String.format("SELECT count(*) FROM reserva WHERE aceptado = 0");
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
                if(res.next()){
                    sal = res.getInt(1);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        return sal;
    }
    
    /**
     * Función para crear un usuario en la base de datos
     * @param conex conexión a la base de datos
     * @param apo apodo que va a usar el usuario
     * @return true
     */
    public static boolean crearUsu(Connection conex, String apo){
        boolean sal = false;
        System.out.print("Clave: ");
        String cla = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("Nombre: ");
        String nom = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("Apellidos: ");
        String ape = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("Email: ");
        String email = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("zona: ");
        String z = new Scanner(System.in).useLocale(Locale.US).nextLine();
        String zona = compruebaZona(z);
        apo = apo.toLowerCase();
        String sql="INSERT INTO usuario(apodo, clave, nombre, apellidos, email, zona) "
                + "VALUES (?,?,?,?,?,?)";
        
        //Esta opción es otra para hacer la consulta de insert en prueba
        //String sql = "INSERT INTO Prueba (id,nombre,direccion,fecha) VALUES(?,?,?,?)";
        try{
            PreparedStatement pstmt = conex.prepareCall(sql);
            
            pstmt.setString(1, apo);
            pstmt.setString(2, cla);
            pstmt.setString(3, nom);
            pstmt.setString(4, ape);
            pstmt.setString(5, email);
            pstmt.setString(6, zona);
            
            pstmt.executeUpdate();
            sal = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sal;
    }
    
    /**
     * Función para crear taxista en la base de datos
     * @param conex conexión a la base de datos
     * @param apo el apodo que va usar
     * @return Al crearse devuelve true
     */
    public static boolean crearTaxista(Connection conex, String apo){
        boolean sal = false;
        System.out.print("Clave: ");
        String cla = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("Nombre: ");
        String nom = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("Apellidos: ");
        String ape = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("Email: ");
        String email = new Scanner(System.in).useLocale(Locale.US).nextLine();
        System.out.print("zona: ");
        String z = new Scanner(System.in).useLocale(Locale.US).nextLine();
        String zona = compruebaZona(z);
        
        apo = apo.toLowerCase();
        String sql="INSERT INTO taxista(apodo, clave, nombre, apellidos, email, disponible, zona) "
                + "VALUES (?,?,?,?,?,?,?)";
        
        //Esta opción es otra para hacer la consulta de insert en prueba
        //String sql = "INSERT INTO Prueba (id,nombre,direccion,fecha) VALUES(?,?,?,?)";
        try{
            PreparedStatement pstmt = conex.prepareCall(sql);
            
            pstmt.setString(1, apo);
            pstmt.setString(2, cla);
            pstmt.setString(3, nom);
            pstmt.setString(4, ape);
            pstmt.setString(5, email);
            pstmt.setInt(6, 0);
            pstmt.setString(7, zona);
            
            pstmt.executeUpdate();
            sal = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sal;
    }
    
    /**
     * Funcion para saber si existe un registro
     * @param conex conexión a la base de datos
     * @param apo variable a consultar en tipo string
     * @param tabla tabla donde se consulta la variable
     * @param nomCol nombre de la columna donde esta la variable
     * @return true si esta, false si no esta
     */
    public static boolean existeReg(Connection conex, String apo, String tabla, String nomCol){
        String sql=String.format("SELECT COUNT(*) FROM %s WHERE %s = ?",tabla,nomCol);
        try{
            PreparedStatement pstmt = conex.prepareStatement(sql);
            pstmt.setString(1, apo);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int contado = res.getInt(1); //Coge la primera columna
                return contado > 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    
    /**
     * Función para cambiar la zona en la que se encuentra actualmente
     * @param conexion Conexión a la BBDD
     * @param u1 Usuario que esta realizando la acción
     * @return false si no se realiza el update, true si se realiza correctamente
     */
    public static boolean cambioZona(Connection conexion, UserTaxi u1){
        boolean hecho = false;
        
        String tabla = "";
        
        switch(u1.tipo()){
            case "usuario"->{
                u1 = (Usuario) u1;
                tabla = "usuario";
            }
            case "taxista"->{
                u1 = (Taxista) u1;
                tabla = "taxista";
            }
        }
        
        System.out.println("¿En que zona se encuentra ahora?");
        String zona = compruebaZona(new Scanner(System.in).useLocale(Locale.US).nextLine());
        
        String usu = u1.getApodo();
        
        String sql = "";
        
        if(tabla.equals("taxista")){
            sql="UPDATE taxista SET zona = ? WHERE apodo = ?";
        }
        if("usuario".equals(tabla)){
            sql="UPDATE usuario SET zona = ? WHERE apodo = ?";
        }
        
        if(!sql.isEmpty()){
            try{
            
                PreparedStatement pstmt = conexion.prepareStatement(sql);

                pstmt.setString(1, zona);
                pstmt.setString(2, usu);

                pstmt.executeUpdate();
                hecho = true;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        
        return hecho;
    }
    
    /*************************************************/
    /*************************************************/
    /************* Funciones basicas *****************/
    /*************************************************/
    /*************************************************/
    
    /**
     * Función para calcular el precio que se cobrara por el viaje
     * @param fila Zona desde la que se inicia el viaje
     * @param columna Zona desde la que finaliza el viaje
     * @return Precio del viaje en concreto
     */
    public static int calculoTarifa(int fila, int columna){
        int precio = 0;
        
        fila -=1;
        columna -= 1;
        
        ArrayList<ArrayList<Integer>> tablaPrecios = new ArrayList<ArrayList<Integer>>();
        
        ArrayList <Integer> fila1 = new ArrayList<>();
        fila1.add(0);
        fila1.add(3);
        fila1.add(4);
        fila1.add(7);
        fila1.add(13);
        
        ArrayList <Integer> fila2 = new ArrayList<>();
        fila2.add(3);
        fila2.add(0);
        fila2.add(1);
        fila2.add(4);
        fila2.add(10);
        
        ArrayList <Integer> fila3 = new ArrayList<>();
        fila3.add(4);
        fila3.add(1);
        fila3.add(0);
        fila3.add(3);
        fila3.add(9);
        
        ArrayList <Integer> fila4 = new ArrayList<>();
        fila4.add(7);
        fila4.add(4);
        fila4.add(3);
        fila4.add(0);
        fila4.add(4);
        
        ArrayList <Integer> fila5 = new ArrayList<>();
        fila5.add(13);
        fila5.add(10);
        fila5.add(9);
        fila5.add(4);
        fila5.add(0);
        
        tablaPrecios.add(fila1);
        tablaPrecios.add(fila2);
        tablaPrecios.add(fila3);
        tablaPrecios.add(fila4);
        tablaPrecios.add(fila5);
        
        int km = tablaPrecios.get(fila).get(columna);
        
        precio = 3 + km*3;
        
        return precio;
    }
    
    /**
     * Función para limitar la cantidad de letras
     * @param lCar El string con el comentario completo
     * @return El string con el máximo de caracteres aceptados
     */
    public static String cuentaLetras(String lCar){
        String sal = "";
        
        List <Character> lC =new ArrayList<>();
        
        int letras=0;
        
        for(int i = 0; i<lCar.length() ; i++){
            lC.add(lCar.charAt(i));
        }
        
        char c = ' ';
        for(int j = 0; j < 240; j++){
            c = lC.get(j);
            sal += c;
            letras++;
        }
        
        return sal;
    }
    
    /**
     * Comprueba si la zona introducida es una zona aceptada
     * @param entrada String con la zona que se ha introducido
     * @return devuelve el string de la zona igual que el enumerado de las zona aceptadas
     */
    public static String compruebaZona(String entrada){
        String zona="";
        
        switch(entrada){
            case "zona1","1"->{
                zona = Zonas.zona1.toString();
            }
            case "zona2","2"->{
                zona = Zonas.zona2.toString();
            }
            case "zona3","3"->{
                zona = Zonas.zona3.toString();
            }
            case "zona4","4"->{
                zona = Zonas.zona4.toString();
            }
            case "zona5","5"->{
                zona = Zonas.zona5.toString();
            }
            default ->{
                System.out.println("Zona no aceptada. '\r'Se pone zona 1 por defecto.");
                zona = Zonas.zona1.toString();
            }
        }
        
        return zona;
    }
    
    /**
     * Conecta a una base de datos que le indiques
     * @return Devuelve la conexión que usaremos con la base de datos
     */
    public static Connection conectarBD(){
        
        
        
        try{
            //cargar el controlador de jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Establecer conexion
            conexion = DriverManager.getConnection(url, usuario,password);
            System.out.println("Conexcion establecida");
        }catch (ClassNotFoundException e){
            System.out.println("Error no se puede cargar el controlador de JDBC");
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
        return conexion;
    }
    
    /**
     * Cierra la conexión con la base de datos
     * @param conexion Como parametro de entrada se le da la conexión que estamos utilizando
     */
    public static void cerrarConexion(Connection conexion){
        if(conexion != null){
            try{
                conexion.close();
            }catch(SQLException e){
                System.out.println("Error al cerrar la BBDD" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}