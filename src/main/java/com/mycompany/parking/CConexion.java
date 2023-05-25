/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parking;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author x-man
 */
public class CConexion {
    Connection conectar = null;
    
    String usuario = "postgres";  
    String contrasenia = "admin";
    String bd = "parkingDB";
    String ip = "localhost";
    String puerto = "5432";
    
    String cadena = "jdbc:postgresql://" + ip + ":" + puerto + "/" + bd;
    
    public Connection establecerConexion(){
        
        try{
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
            
            //JOptionPane.showMessageDialog(null, "Se conecto correctamnete a la base de datos");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e.toString());
        }
        return conectar; 
    }
}
