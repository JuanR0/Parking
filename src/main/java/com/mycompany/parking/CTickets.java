/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parking;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author x-man
 */
public class CTickets {

    int id;
    Timestamp fechaInicio;
    Timestamp fechaFin;
    int lugar_id;
    String estado;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public int getLugar_id() {
        return lugar_id;
    }

    public void setLugar_id(int lugar_id) {
        this.lugar_id = lugar_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void MostrarTickets(JTable ParamTablaTotalTickets){
        CConexion objetoConexion = new CConexion();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        modelo.addColumn("Id");
        modelo.addColumn("Fecha Inicio");
        modelo.addColumn("Fecha Fin");
        modelo.addColumn("ID Lugar");
        modelo.addColumn("Estado");
        
        ParamTablaTotalTickets.setModel(modelo);
        
        sql = "select * from Tickets order by id ASC";
        
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
                
                modelo.addRow(datos);
            }
            
            ParamTablaTotalTickets.setModel(modelo);
            
        } catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error: "+ e.toString());
            
        }
    }
    
    public void generarTicketNormal(){
        long millis = System.currentTimeMillis()- 3600 * 1000;
        Timestamp fechaT = new Timestamp(millis);
        setFechaInicio(fechaT);
        setLugar_id(obtenerLugar());
        String estadoT = "En uso";
        setEstado(estadoT);
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "insert into Tickets (fechaInicio, lugar_id, estado) values (?, ?, ?);"
                + "update Lugares set estado = 'Ocupado' where Lugares.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setTimestamp(1, getFechaInicio());
            cs.setInt(2, getLugar_id());
            cs.setString(3, getEstado());
            cs.setInt(4, getLugar_id());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se inserto Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString() + getLugar_id());
        }
    }
    
    public void generarTicketEspecial(){
        long millis = System.currentTimeMillis()- 3600 * 1000;
        Timestamp fechaT = new Timestamp(millis);
        setFechaInicio(fechaT);
        setLugar_id(obtenerLugarEspecial());
        String estadoT = "En uso";
        setEstado(estadoT);
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "insert into Tickets (fechaInicio, lugar_id, estado) values (?, ?, ?);"
                + "update Lugares set estado = 'Ocupado' where Lugares.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setTimestamp(1, getFechaInicio());
            cs.setInt(2, getLugar_id());
            cs.setString(3, getEstado());
            cs.setInt(4, getLugar_id());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se inserto Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString() + getLugar_id());
        }
    }
     
     public int obtenerLugar(){
        CConexion objetoConexion = new CConexion();
        String sql="";
        int lugarT=0;
        
        sql = "SELECT * FROM Lugares WHERE lugares.estado = 'Libre' AND lugares.tipo = 'Normal' ORDER BY seccion ASC, numero LIMIT 1";
        
        Statement st; 
        try{
            st= objetoConexion.establecerConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lugarT = rs.getInt(1);
            }
            
        } catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error: "+ e.toString());
            
        }
        return lugarT;
     }
     
     public int obtenerLugarEspecial(){
        CConexion objetoConexion = new CConexion();
        String sql="";
        int lugarT=0;
        
        sql = "SELECT * FROM Lugares WHERE lugares.estado = 'Libre' AND lugares.tipo = 'Especial' ORDER BY seccion ASC, numero LIMIT 1";
        
        Statement st; 
        try{
            st= objetoConexion.establecerConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lugarT = rs.getInt(1);
            }
            
        } catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error: "+ e.toString());
            
        }
        return lugarT;
     }
     
     public void seleccionarLugar(JTable paramTablaTickets, JTextField paramId, JTextField paramLugarId){    
    
        try {
            int fila  = paramTablaTickets.getSelectedRow();
            if (fila >= 0 ){
                paramId.setText(paramTablaTickets.getValueAt(fila, 0).toString());
                paramLugarId.setText(paramTablaTickets.getValueAt(fila, 3).toString());
            }
            
            else 
            {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
     
     public void finiquitarTicket(JTextField paramId, JTextField paramLugarId){
        setId(Integer.parseInt(paramId.getText()));
        setLugar_id(Integer.parseInt(paramLugarId.getText()));
        long millis = System.currentTimeMillis()- 3600 * 1000;
        Timestamp fechaT = new Timestamp(millis);
        setFechaFin(fechaT);
        
        
        CConexion objetoConexion = new CConexion();
        
        String consulta = "update Tickets set estado = 'Finiquitado', fechaFin = ? where Tickets.Id = ?;"
                + "update Lugares set estado = 'Libre' where Lugares.id = ?;";
        
        try {
        
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setTimestamp(1, getFechaFin());
            cs.setInt(2, getId());
            cs.setInt(3, getLugar_id());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se finiquito Correctamente");
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString() + getLugar_id());
        }
    }
     
      public void imprimirTicket(JTable paramTablaTickets, JTextField paramId)  
    {  
        //created PDF document instance   
        Document doc = new Document();  
        try  
        {  
            int num = Integer.parseInt(paramId.getText());
            //generate a PDF at the specified location  
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Tickets/Ticket"+num+".pdf"));  
            System.out.println("PDF created.");  
            JOptionPane.showMessageDialog(null, "Ticket Creado"); 
            //opens the PDF  
            doc.open();  
            //adds paragraph to the PDF file 
            PdfPTable tbl = new PdfPTable(5);
            tbl.addCell("Numro Ticket");
            tbl.addCell("Fecha de inicio");
            tbl.addCell("Fecha de fin");
            tbl.addCell("Estado");
            tbl.addCell("Lugar");
            num--;
            String ID = paramTablaTickets.getValueAt(num, 0).toString();
            String FechaI = paramTablaTickets.getValueAt(num, 1).toString();
            String FechaF;
            String LugarId = paramTablaTickets.getValueAt(num, 3).toString();
            String Estado = paramTablaTickets.getValueAt(num, 4).toString();
            if(Estado.equals("En uso")){
                FechaF = "N/A";
            }
            else{
                FechaF = paramTablaTickets.getValueAt(num, 2).toString();
            }
            tbl.addCell(ID);
            tbl.addCell(FechaI);
            tbl.addCell(FechaF);
            tbl.addCell(LugarId);
            tbl.addCell(Estado);
            doc.add(tbl);
            //close the PDF file  
             
            //closes the writer  
            writer.close();  
        }   
        catch (DocumentException e)  
        {  
            e.printStackTrace();  
        }   
        catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        doc.close(); 
    }  
     
}
