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
public class CAdministradores {

    int id;
    String usuario;
    String Clave;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }
    
    public void MostrarAdmins(JTable ParamTablaTotalAdmins){
        CConexion objetoConexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        modelo.addColumn("Id");
        modelo.addColumn("Usuario");
        modelo.addColumn("Clave");
        
        ParamTablaTotalAdmins.setModel(modelo);
        
        sql = "select * from Administradores order by id ASC";
        
        String [] datos = new String[3];
        Statement st; 
        
        try{
            st= objetoConexion.establecerConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                
                modelo.addRow(datos);
            }
            
            ParamTablaTotalAdmins.setModel(modelo);
            
        } catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error: "+ e.toString());
            
        }
    }
    
    public void insertarLugar(JTextField paramUsuario, JTextField paramClave){
        setUsuario(paramUsuario.getText());
        setClave(paramClave.getText());
        CConexion objetoConexion = new CConexion();
        
        String consulta = "insert into Administradores (usuario, clave) values (?, ?);";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getUsuario());
            cs.setString(2, getClave());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se inserto Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
    public void seleccionarAdministrador(JTable paramTablaAdmins, JTextField paramId, JTextField paramUsuario, JTextField paramClave){    
    
        try {
            int fila  = paramTablaAdmins.getSelectedRow();
            if (fila >= 0 ){
                paramId.setText(paramTablaAdmins.getValueAt(fila, 0).toString());
                paramUsuario.setText(paramTablaAdmins.getValueAt(fila, 1).toString());
                paramClave.setText(paramTablaAdmins.getValueAt(fila, 2).toString());
            }
            
            else 
            {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
    public void eliminarAdminstrador(JTextField paramId){
        setId(Integer.parseInt(paramId.getText()));
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "delete from Administradores where Lugares.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, getId());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se elimino Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
    public void modificarAdmin(JTextField paramId, JTextField paramUsuario, JTextField paramClave){
        setId(Integer.parseInt(paramId.getText()));
        setUsuario(paramUsuario.getText());
        setClave(paramClave.getText().toString());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "update Administradores set usuario = ? , clave = ? where Administradores.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getUsuario());
            cs.setString(2, getClave());
            cs.setInt(3, getId());
            
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modifico Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
    
}
