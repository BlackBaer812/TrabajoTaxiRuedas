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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class TaxiRuedas {

    public static void main(String[] args) {
        Connection conexion = conectarBD();
        login(conexion);
        
    }
    
    
    /*************************************************/
    /*************************************************/
    /********************* login *********************/
    /*************************************************/
    /*************************************************/
    
    /**
     * login para iniciar sesión, crear cuenta o recuperar cuenta (no programado aún)
     * @param conexion conexión a la base de datos
     */
    public static void login(Connection conexion){
        int op;
        
        int fallos = 0;
        
        do{
            System.out.println("________________________");
            System.out.println("Menu de selección:");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registarse");
            System.out.println("3. Recuperar contraseña");
            System.out.println("4. Salir");
            System.out.print("Introduce una opción: ");
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1->{//Iniciar sesion
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
                }
                case 2->{//Crear usuario
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
                    
                }
                case 3 ->{ //recuperar cuenta
                    
                }
                case 4 ->{ //Salir del programa
                    System.out.println("Saliendo del programa.");
                    cerrarConexion(conexion);
                }
                default ->{
                    System.out.println("Esta opción " + op + " no esta disponible.");
                }
            }
            fallos = 0;
        }while(op!=4);
        cerrarConexion(conexion);
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
            System.out.println("6. Cerrar Sesion");
            System.out.print("Introduce una opción: ");
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1->{//cambia de zona
                    cambioZona(conexion, u1);
                }
                case 2 ->{//realizar reserva
                    crearReserva(conexion,u1);
                }
                case 3 ->{//Listado de taxistas
                    List<Taxista> lTaxista = listaTaxistas(conexion);
                    Arrays.deepToString(lTaxista.toArray());
                }
                case 4 ->{//Historial de viajes (no reservas)
                    
                }
                case 5 ->{//Realizar comentarios
                    
                }
                case 6 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                }
                default->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op != 6);
    }
    
    /**
     * Menu taxista para adminsitrar sus acciones
     * @param conexion conexión a la base de datos
     * @param apo apodo del taxista
     */
    public static void menuTaxista(Connection conexion,String apo){
        Taxista u1 = selectTaxista(conexion, apo);
        int op;
        
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
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1 ->{//cambiar de zona
                    cambioZona(conexion, u1);
                }
                case 2 ->{ //menu taxi
                    System.out.println("Acceciendo a menu taxi.");
                    menuTaxi(conexion,u1);
                }
                
                case 3 ->{//Lista de reservas
                    List <Reserva> lResev = listaReservas(conexion,"reserva");
                    for(Reserva reserv:lResev){
                        System.out.println(reserv);
                    }
                }
                case 4 ->{//Aceptar viaje
                    System.out.println("¿Qué viaje quiere aceptar? (ID del viaje)");
                    int idR = new Scanner(System.in).useLocale(Locale.US).nextInt();
                    System.out.println("¿Qué vehiculo va a usar (Matrícula)?");
                    String mat = new Scanner(System.in).useLocale(Locale.US).nextLine();
                    aceptReserva(conexion,u1,mat,idR);
                    noDisponible(conexion,u1.getApodo());
                }
                case 5 ->{//Lista de viajes
                    List <Viaje> lViajes = listaViajesNoFinT(conexion,u1);
                    for(Viaje viaj:lViajes){
                        System.out.println(viaj);
                    }
                }
                case 6 ->{//Finalizar viaje aceptado
                    System.out.println("¿Qué viaje desea marcar como finalizado?");
                    int vF = new Scanner(System.in).useLocale(Locale.US).nextInt();
                    finViaje(conexion,u1,vF);
                    disponible(conexion,u1.getApodo());
                }
                case 7 ->{//Lista de comentarios
                    
                }
                case 8 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                    noDisponible(conexion,apo);
                    cerrarConexion(conexion);
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
                    for(Taxi tax:lTaxis){
                        System.out.println(tax);
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
                if(tipo != null){
                    Reserva e = new Reserva (id,apodo,fecha,zI,zF,tipo);
                    sal.add(e);
                }
                
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        return sal;
    }
    
    /**
     * Función para recuperar la lista de viajes
     * @param conexion conexion a la BBDD
     * @param u1 Taxista que consulta la lista
     * @return Lista de los Viajes no finalizados
     */
    public static List<Viaje> listaViajesNoFinT(Connection conexion,Taxista u1){
        List<Viaje> sal = new ArrayList<>();
        String sql = String.format("SELECT * FROM viaje WHERE finalizado = 0 and apodo_taxista = ?");

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
                Viaje e = new Viaje (id,aUsu,fecha,zI,zF,aTaxista,mTaxi);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    }
    
    /**
     * 
     * @param conexion conexion a la BBDD
     * @param u1 Taxista que consulta la lista
     * @return 
     */
    public static List<Viaje> listaViajesComentT(Connection conexion,Taxista u1){
        List<Viaje> sal = new ArrayList<>();
        String sql = String.format("SELECT * FROM viaje WHERE finalizado = 0 and apodo_taxista = ?");

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
                Viaje e = new Viaje (id,aUsu,fecha,zI,zF,aTaxista,mTaxi);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        
        return sal;
    }
    
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
        
        System.out.print("¿A que zona quiere ir?");
        String zFinal = new Scanner(System.in).useLocale(Locale.US).nextLine();
        String zF = compruebaZona(zFinal);
        
        System.out.print("¿Que tipo de taxi quiere?");
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
        
        String sql="INSERT INTO reserva(apodo, fecha, ZonaInicio, ZonaFinal, aceptado,tipotaxi) "
                + "VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement pstmt = conex.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            pstmt.setObject(2, ahora);
            pstmt.setString(3, u1.getLugarS());
            pstmt.setString(4, zF);
            pstmt.setInt(5, 0);
            pstmt.setString(6, tipo);
            
            pstmt.executeUpdate();
            sal = true;
        }catch(SQLException e){
            e.printStackTrace();
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
            case "usario"->{
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
        
        String sql=String.format("UPDATE %s SET zona = ? WHERE apodo = ?",tabla);
        try{
            
            PreparedStatement pstmt = conexion.prepareStatement(sql);

            pstmt.setString(1, zona);
            pstmt.setString(2, u1.getNombre());
            
            pstmt.executeUpdate();
            hecho = true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return hecho;
    }
    
    /*************************************************/
    /*************************************************/
    /************* Funciones basicas *****************/
    /*************************************************/
    /*************************************************/
    
    /**
     * Comprueba si la zona introducida es una zona aceptada
     * @param entrada 
     * @return 
     */
    public static String compruebaZona(String entrada){
        String zona="";
        
        switch(entrada){
            case "zona1"->{
                zona = Zonas.zona1.toString();
            }
            case "1"->{
                zona = Zonas.zona1.toString();
            }
            case "zona2"->{
                zona = Zonas.zona2.toString();
            }
            case "2"->{
                zona = Zonas.zona2.toString();
            }
            case "zona3"->{
                zona = Zonas.zona3.toString();
            }
            case "3"->{
                zona = Zonas.zona3.toString();
            }
            case "zona4"->{
                zona = Zonas.zona4.toString();
            }
            case "4"->{
                zona = Zonas.zona4.toString();
            }
            case "zona5"->{
                zona = Zonas.zona5.toString();
            }
            case "5"->{
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
