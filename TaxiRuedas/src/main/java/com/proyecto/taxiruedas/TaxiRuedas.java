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
            System.out.println("Introduce una opción");
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
            System.out.println("1. Lista de reservas");
            System.out.println("2. Historial de viajes.");
            System.out.println("3. Lista de comentarios.");
            System.out.println("4. Cerrar Sesion");
            op = new Scanner(System.in).useLocale(Locale.US).nextInt();
            switch(op){
                case 1 ->{//Lista de reservas
                }
                case 2 ->{//Lista de viajes
                    
                }
                case 3 ->{//Lista de comentarios
                    
                }
                case 4 ->{//Cerrar sesion
                    System.out.println("Saliendo de la sesión.");
                }
                default->{
                    System.out.println("Opcion no aceptada");
                }
            }
        }while(op != 4);
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
     * @return 
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
     * @return 
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
     * 
     * @param conexion
     * @param apo 
     */
    public static void disponible(Connection conexion, String apo){
       //INSERT INTO `reserva`(`ID`, `apodo`, `fecha`, `ZonaInicio`, `ZonaFinal`, `Aceptado`) VALUES 
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
        
        LocalDate ahora = LocalDate.now();
        
        
        //INSERT INTO `reserva`(`ID`, `apodo`, `fecha`, `ZonaInicio`, `ZonaFinal`, `Aceptado`) VALUES 
        String sql=String.format("INSERT INTO reserva(apodo, fecha, ZonaInicio, ZonaFinal, aceptado) "
                + "VALUES (?,?,?,?,?)");
        try{
            PreparedStatement pstmt = conex.prepareCall(sql);
            
            pstmt = conex.prepareStatement(sql);
            pstmt.setString(1, u1.getApodo());
            pstmt.setObject(2, ahora);
            pstmt.setString(3, u1.getLugarS());
            pstmt.setString(4, zFinal);
            pstmt.setInt(5, 0);
            
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
     * 
     * @param conex
     * @param apo
     * @param tabla
     * @param nomCol
     * @return 
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
