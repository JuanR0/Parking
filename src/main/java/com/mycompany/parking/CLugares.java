/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parking;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author x-man
 */
public class CLugares {

    int id;
    String seccion;
    int numero;
    String tipo;
    String estado;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
     public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void MostrarLugares(JTable ParamTablaTotalLugares){
        CConexion objetoConexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        modelo.addColumn("Id");
        modelo.addColumn("Seccion");
        modelo.addColumn("Numero");
        modelo.addColumn("Tipo");
        modelo.addColumn("Estado");
        
        ParamTablaTotalLugares.setModel(modelo);
        
        sql = "select * from Lugares order by seccion ASC, numero";
        
        String [] datos = new String[5];
        Statement st; 
        
        try{
            st= objetoConexion.establecerConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                
//                if(datos[3].equals("UwU")){
//                    modelo.addRow(datos);
//                }
                modelo.addRow(datos);
            }
            
            ParamTablaTotalLugares.setModel(modelo);
            
        } catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error: "+ e.toString());
            
        }
    }
    
    public void insertarLugar(JTextField paramSeccion, JTextField paramNumero, JComboBox paramTipo, JComboBox paramEstado){
        setSeccion(paramSeccion.getText());
        setNumero(Integer.parseInt(paramNumero.getText()));
        setTipo(paramTipo.getSelectedItem().toString());
        setEstado(paramEstado.getSelectedItem().toString());
        CConexion objetoConexion = new CConexion();
        
        String consulta = "insert into Lugares (seccion, numero, tipo, estado) values (?, ?, ?, ?);";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getSeccion());
            cs.setInt(2, getNumero());
            cs.setString(3, getTipo());
            cs.setString(4, getEstado());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se inserto Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
    public void seleccionarLugar(JTable paramTablaLugares, JTextField paramId, JTextField paramSeccion, JTextField paramNumero, JComboBox paramTipo, JComboBox paramEstado){    
    
        try {
            int fila  = paramTablaLugares.getSelectedRow();
            if (fila >= 0 ){
                paramId.setText(paramTablaLugares.getValueAt(fila, 0).toString());
                paramSeccion.setText(paramTablaLugares.getValueAt(fila, 1).toString());
                paramNumero.setText(paramTablaLugares.getValueAt(fila, 2).toString());
                paramTipo.setSelectedItem(paramTablaLugares.getValueAt(fila, 3).toString());
                paramEstado.setSelectedItem(paramTablaLugares.getValueAt(fila, 4).toString());
            }
            
            else 
            {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
    public void eliminarLugar(JTextField paramId){
        setId(Integer.parseInt(paramId.getText()));
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "delete from lugares where Lugares.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se elimino Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
    public void modificarLugar(JTextField paramId, JTextField paramSeccion, JTextField paramNumero,  JComboBox paramTipo, JComboBox paramEstado){
        setId(Integer.parseInt(paramId.getText()));
        setSeccion(paramSeccion.getText());
        setNumero(Integer.parseInt(paramNumero.getText()));
        setTipo(paramTipo.getSelectedItem().toString());
        setEstado(paramEstado.getSelectedItem().toString());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "update Lugares set seccion = ? , numero = ?, tipo = ?, estado = ? where Lugares.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getSeccion());
            cs.setInt(2, getNumero());
            cs.setString(3, getTipo());
            cs.setString(4, getEstado());
            cs.setInt(5, getId());
            
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
}
