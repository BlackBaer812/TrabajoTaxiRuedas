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
     * 
     * @param conexion 
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
        Usuario u1 = selectUsu(conexion, "usuario",apo);
        int op;
        do{
            System.out.println("Menu Usuario");
            System.out.println("1. Realizar reserva.");
            System.out.println("2. Taxistas disponibles.");
            System.out.println("3. Historial de viajes.");
            System.out.println("4. Realizar comentarios.");
            System.out.println("5. Cerrar Sesion");
            System.out.print("Introduce una opción: ");
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1 ->{//realizar reserva
                    crearReserva(conexion,u1);
                }
                case 2 ->{//Listado de taxistas
                    List<Taxista> lTaxista = listaTaxistas(conexion,"Taxista");
                    Arrays.deepToString(lTaxista.toArray());
                }
                case 3 ->{//Historial de viajes (no reservas)
                    
                }
                case 4 ->{//Realizar comentarios
                    
                }
                case 5 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                }
                default->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op != 5);
    }
    
    public static void menuTaxista(Connection conexion,String apo){
        Taxista u1 = selectTaxista(conexion, "taxista",apo);
        int op;
        do{
            System.out.println("Menu Usuario");
            System.out.println("1. Menu taxi");
            System.out.println("2. Lista de reservas");
            System.out.println("3. Historial de viajes.");
            System.out.println("4. Lista de comentarios.");
            System.out.println("5. Cerrar Sesion");
            System.out.print("Introduce una opción: ");
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1 ->{ //menu taxi
                    System.out.println("Acceciendo a menu taxi.");
                    menuTaxi(conexion,u1);
                }
                
                case 2 ->{//Lista de reservas
                    List <Reserva> lResev = listaReservas(conexion,"reserva");
                    for(Reserva reserv:lResev){
                        System.out.println(reserv);
                    }
                }
                case 3 ->{//Lista de viajes
                    
                }
                case 4 ->{//Lista de comentarios
                    
                }
                case 5 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                    noDisponible(conexion,apo);
                    cerrarConexion(conexion);
                }
                default->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op != 5);
    }
    
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
                    List <Taxi> lTaxis = listaTaxis(conexion,"taxi",u1);
                    System.out.println(lTaxis);
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
     * 
     * @param conexion
     * @param tabla
     * @param usu
     * @return usuario para poder acceder a su zona
     */
    public static Usuario selectUsu(Connection conexion, String tabla, String usu){
        Usuario e = null;
        String sql = String.format("SELECT * FROM %s WHERE apodo = \"%s\"", tabla,usu);
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
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
     * 
     * @param conexion
     * @param tabla
     * @param usu
     * @return taxista creado para saber quien es y poder acceder a su zona
     */
    public static Taxista selectTaxista(Connection conexion, String tabla, String usu){
        Taxista e = null;
        String sql = String.format("SELECT * FROM %s WHERE apodo = \"%s\"", tabla,usu);
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
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
     * Pone disponible al taxista
     * @param conexion conexion a la base de datos
     * @param apo apodo del taxista que se conecta
     */
    public static void disponible(Connection conexion, String apo){
       
        String sql=String.format("UPDATE taxista SET disponible='1' WHERE apodo = ?");
        try{
            PreparedStatement pstmt = conexion.prepareCall(sql);
            
            pstmt = conexion.prepareStatement(sql);
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
            PreparedStatement pstmt = conexion.prepareCall(sql);
            
            pstmt = conexion.prepareStatement(sql);
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
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
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
    
    
    public static List<Taxi> listaTaxis(Connection conexion, String tabla, Taxista u1){
        List<Taxi> sal = new ArrayList<>();
        String sql = String.format("SELECT matricula,tipo FROM %s WHERE apodotaxi = \'%s\'" , tabla, u1.getApodo());
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
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
     * 
     * @param conexion
     * @param tabla
     * @return 
     */
    public static List<Taxista> listaTaxistas(Connection conexion, String tabla){
        List<Taxista> sal = new ArrayList<>();
        String sql = String.format("SELECT apodo,nombre,apellidos,zona FROM %s", tabla);
        /*
        //Esta forma es para hacerlo como nos lo pide otro
        String tabla = "EMPLEADOS";
        String sql2 = String.format("SELECT * FROM %S", tabla);
        */
        try{
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String apodo = res.getString("apodo");
                String nombre = res.getString("nombre");
                String ape = res.getString("apellidos");
                String z = res.getString("zona");
                String mat = res.getString("mtaxi");
                Taxista e = new Taxista (apodo,nombre,ape,z,mat);
                sal.add(e);
            }
        }catch(SQLException e){
            System.out.println("e.getMessage()");
        }
        return sal;
    }
    
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
            PreparedStatement pstmt = conex.prepareCall(sql);
            
            pstmt = conex.prepareStatement(sql);
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
            
            if(tipo!= ""){
                String sql=String.format("UPDATE taxi SET tipo=? WHERE matricula=?");
                try{
                    PreparedStatement pstmt = conexion.prepareCall(sql);

                    pstmt = conexion.prepareStatement(sql);
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
            String sql=String.format("DELETE FROM %s WHERE %s = \'%s\'",tabla, columna, mat);
            
            try{
                PreparedStatement pstmt = conexion.prepareCall(sql);
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
     * 
     * @param conex
     * @param u1
     * @return 
     */
    public static boolean crearReserva(Connection conex, Usuario u1){
        boolean sal = false;
        
        System.out.print("¿Adonde que zona quiere ir?");
        String zFinal = new Scanner(System.in).useLocale(Locale.US).nextLine();
        
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
        
        
        //INSERT INTO `reserva`(`ID`, `apodo`, `fecha`, `ZonaInicio`, `ZonaFinal`, `Aceptado`) VALUES 
        String sql=String.format("INSERT INTO reserva(apodo, fecha, ZonaInicio, ZonaFinal, aceptado,tipotaxi) "
                + "VALUES (?,?,?,?,?,?)");
        try{
            PreparedStatement pstmt = conex.prepareCall(sql);
            
            pstmt = conex.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            pstmt.setObject(2, ahora);
            pstmt.setString(3, u1.getLugarS());
            pstmt.setString(4, zFinal);
            pstmt.setInt(5, 0);
            pstmt.setString(6, tipo);
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return sal;
    }
    
    /**
     * 
     * @param conex
     * @param apo
     * @return 
     */
    public static boolean crearUsu(Connection conex, String apo){
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
        //controlar que la zona existe
        apo = apo.toLowerCase();
        String sql=String.format("INSERT INTO usuario(apodo, clave, nombre, apellidos, email, zona) "
                + "VALUES (\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",apo, cla, nom, ape, email,z);
        
        //Esta opción es otra para hacer la consulta de insert en prueba
        //String sql = "INSERT INTO Prueba (id,nombre,direccion,fecha) VALUES(?,?,?,?)";
        try{
            PreparedStatement pstmt = conex.prepareCall(sql);
            /*
            pstmt = conex.preparedStatement
            pstmt.setString(1, Code);
            pstmt.setString(2, nombre);
            pstmt.setString(3, direccion);
            pstmt.setString(4, aux);
            */
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    /**
     * 
     * @param conex
     * @param apo
     * @return 
     */
    public static boolean crearTaxista(Connection conex, String apo){
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
        //Controlar que la zona existe
        int disp = 0;
        apo = apo.toLowerCase();
        String sql=String.format("INSERT INTO taxista(apodo, clave, nombre, apellidos, email, disponible, zona) "
                + "VALUES (\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',%d,\'%s\')",apo, cla, nom, ape, email,disp,z);
        
        //Esta opción es otra para hacer la consulta de insert en prueba
        //String sql = "INSERT INTO Prueba (id,nombre,direccion,fecha) VALUES(?,?,?,?)";
        try{
            PreparedStatement pstmt = conex.prepareCall(sql);
            /*
            pstmt.setString(1, Code);
            pstmt.setString(2, nombre);
            pstmt.setString(3, direccion);
            pstmt.setString(4, aux);
            */
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
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
        String sql=String.format("SELECT COUNT(*) FROM %s WHERE %s = \'%s\'",tabla, nomCol, apo);
        try{
            PreparedStatement pstmt = conex.prepareStatement(sql);
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
    
    
    
    /*************************************************/
    /*************************************************/
    /************* Funciones basicas *****************/
    /*************************************************/
    /*************************************************/
    
    /**
     * 
     * @return 
     */
    public static Connection conectarBD(){
        
        
        
        Connection conexion = null;
        
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
     * 
     * @param conexion 
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
