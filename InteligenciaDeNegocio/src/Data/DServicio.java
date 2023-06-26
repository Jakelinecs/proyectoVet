/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import coneccionsocket.ClientPsql;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author HP
 */

public class DServicio {
    
    ClientPsql conn;
    DUsers us;
    
    int id,idpac,idpers;
    String nombre,detalle,fecha,tipo,correoPac,correoPers ;
    

    public static final String[] headers = {
           "id","idpac","idpers","nombre","detalle","fecha","tipo"
    };
    
    public DServicio(){
        conn = new ClientPsql();
    }
    
    public void setId(int id){
        this.id=id;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
/* 1ra opcion pára enviar las opcionnes de llaves foraneas */

    public void setIdpac(int idpac) {
        this.idpac = idpac;
    }

    public void setIdpers(int idpers) {
        this.idpers = idpers;
    }
    
/* 2da opcion para enviar llaves foraneas de persona y paciente*/
    
    public void setIdpac(String correoPac) throws SQLException {
        int pacId  ;
        pacId = us.getIdByEmail(correoPac);
        this.idpac = pacId;
    }

    public void setIdpers(String correoPers)throws SQLException {
        int perId;
        perId=us.getIdByEmail(correoPers);
        this.idpers = perId;
    }

 /*--------------------------------------------------------------*/   
    
    public void insertar() throws SQLException{
        int pacId,perId ;
        pacId=us.getIdByEmail(correoPac) ;
        perId=us.getIdByEmail(correoPers);
        if (pacId!= -1 && perId!= -1) {
            String sql="INSERT INTO pacientes(id,idpac,idpers,nombre,detalle,fecha,tipo)"+
                    " Values(?,?,?,?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, pacId);
            ps.setInt(3, perId);
            ps.setString(4, nombre);
            ps.setString(5, detalle);
            ps.setString(6, fecha);
            ps.setString(8, tipo);
            ps.setString(9, "");

            if(ps.executeUpdate()==0){
                System.err.println("Class DPaciente.java dice: "
                +"Ocurrio un error al insertar Paciente insertar()");
                throw new SQLException();
            } 
        }
    }

    public void editar() throws SQLException {
        int pacId,perId ;
        pacId=us.getIdByEmail(correoPac) ;
        perId=us.getIdByEmail(correoPers);
        if (pacId!= -1 && perId!= -1) {
            String sql="UPDATE servicios SET nombre=?, detalle=?,"
                    + "fecha=?, tipo=?,"
                    + "idpac=?, idpers=? "+
                    " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, detalle);
            ps.setString(3, fecha);
            ps.setString(4,tipo);
            ps.setInt(5, idpac);
            ps.setInt(6, idpers);
            ps.setInt(8, id);
            
            if(ps.executeUpdate()==0){
                System.err.println("Class DServicio.java dice: "
                +"Ocurrio un error al editar servicio editar()");
                throw new SQLException();
            } 
        }
    }
    
    public void eliminar() throws SQLException{
        int pacId,perId ;
        pacId=us.getIdByEmail(correoPac) ;
        perId=us.getIdByEmail(correoPers);
        if (pacId!= -1 && perId!= -1) {
            String sql="DELETE FROM servicios WHERE "
                    +"id=? ";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            
                if(ps.executeUpdate()==0){
                    System.err.println("Class DServicio.java dice: "
                    +"Ocurrio un error al eliminar servicio eliminar()");
                    throw new SQLException();
            } 
        }
        
    }
    
    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        int pacId,perId ;
        pacId=us.getIdByEmail(correoPac) ;
        perId=us.getIdByEmail(correoPers);
        if (pacId!= -1 && perId!= -1) {
            String sql="SELECT * FROM servicios";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();
            while(set.next()){
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("detalle"),
                    set.getString("fecha"),
                    set.getString("tipo"),
                    String.valueOf(set.getInt("idpers")),
                    String.valueOf(set.getInt("idpac")),
                    
                });
            }
        }
        return lista;
    }
           
    public String[] ver() throws SQLException{
        String[] serv=null;
        int pacId,perId ;
        pacId=us.getIdByEmail(correoPac) ;
        perId=us.getIdByEmail(correoPers);
        if (pacId!= -1 && perId!= -1) {
            String sql="SELECT * FROM servicios WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            ResultSet set= ps.executeQuery();
            if(set.next()){
                serv = new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("detalle"),
                    set.getString("fecha"),
                    set.getString("tipo"),
                    String.valueOf(set.getInt("idpers")),
                    String.valueOf(set.getInt("idpac")),
                };
            }else{
                System.err.println("Class DPersona.java dice: "
                +"Ocurrio un error al ver usuario ver()");
                throw new SQLException();
            } 
        }
        
        return serv;
    }
    
    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }

    
}
