/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyecto.taxiruedas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                case 1->{
                    while(fallos<3){
                        if(iSesion(conexion)){
                            
                        }else{
                            System.out.println("Ese usuario no existe");
                            fallos++;
                        }
                    }
                }
                case 2->{
                    System.out.print("Usuario:");
                    String usu = new Scanner(System.in).useLocale(Locale.US).nextLine();
                    if(!existeReg(conexion, usu, "usuario","apodo")){
                        if(crearUsu(conexion,usu)){
                            System.out.println("Usuario creado");
                        }
                    }
                }
            }
        }while(op!=4);
    }
    
    public static boolean iSesion(Connection conexion){
        boolean sal = false;
        System.out.print("Usuario: ");
        String usu = new Scanner(System.in).useLocale(Locale.US).nextLine();
        usu = usu.toLowerCase();
        if(existeReg(conexion,usu,"usuario","apodo")){
            System.out.print("Contraseña: ");
            String clave = new Scanner(System.in).useLocale(Locale.US).nextLine();
            clave = clave.toLowerCase();
            if(existeReg(conexion, clave,"usuario","clave")){
                sal=true;
            }
        }
        return sal;
    }
    
    //INSERT INTO `usuario`(`apodo`, `clave`, `nombre`, `apellidos`, `email`, `zona`) VALUES ()
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
        String sql=String.format("INSERT INTO usuario(apodo, clave, nombre, apellidos, email, zona) "
                + "VALUES (\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",apo, cla, nom, ape, email,z);
        
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
    
    public static boolean existeReg(Connection conex, String apo, String tabla, String apodoTabla){
        String sql=String.format("SELECT COUNT(*) FROM %s WHERE %s = \'%s\'",tabla, apodoTabla, apo);
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
    
    public static Connection conectarBD(){
        //jddc:mysql es el driver usado
        //localhost:3306/p1 es donde esta la base de datos
        
        
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
