/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import Utlis.DateString;
import coneccionsocket.ClientPsql;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Calendar;
/**
 *
 * @author HP
 */
public class DPaciente {
    
    ClientPsql conn;
    DUsers us;
    int id,propietario;
    String correo,nombre,especie,raza,sexo,color,f_nacimiento,perfil,created_at,updated_at;

    public static final String[] headers=
            { 
             "id","nombre","especie","raza","sexo","color","f_nacimiento","propietario","perfil","created_at","updated_at"};

    public DPaciente() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setF_nacimiento(String f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setCreated_at() {
        this.created_at = DateString.StringToDateActual();
    }

    public void setUpdated_at() {
        this.updated_at = DateString.StringToDateActual();
    }
    
    
    
    public void insertar() throws SQLException{
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql="INSERT INTO pacientes(id,nombre,especie,raza,sexo,color,f_nacimiento,propietario,perfil,created_at,updated_at)"+
                    " Values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, especie);
            ps.setString(4, raza);
            ps.setString(5, sexo);
            ps.setString(6, color);
            ps.setDate(7, getDate(f_nacimiento));
            ps.setInt(8, propietario);
            ps.setString(9, "");
            ps.setDate(10, getDateTime(created_at));
            ps.setDate(11, getDateTime(updated_at));

            if(ps.executeUpdate()==0){
                System.err.println("Class DPaciente.java dice: "
                +"Ocurrio un error al insertar Paciente insertar()");
                throw new SQLException();
            } 
        }
    }
    
    public void editar() throws SQLException{
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql="UPDATE pacientes SET nombre=?, especie=?, raza=?, sexo=?, color=?, "
                + "f_nacimiento=?, propietario=?, updated_at=? "+
                " WHERE id=?";
        
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, especie);
            ps.setString(3, raza);
            ps.setString(4, sexo);
            ps.setString(5, color);
            ps.setDate(6,getDate(f_nacimiento));
            ps.setInt(7, propietario);
            ps.setDate(8,getDateTime(updated_at));
            ps.setInt(9, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DPaciente.java dice: "
                +"Ocurrio un error al editar pacientes editar()");
                throw new SQLException();
            } 
        }
    }
    
    public void eliminar() throws SQLException{
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {

            String sql="DELETE FROM pacientes WHERE"+
                    " id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DPaciente.java dice: "
                +"Ocurrio un error al eliminar paciente eliminar()");
                throw new SQLException();
            } 
        }
    }

    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql="SELECT * FROM pacientes";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();
            while (set.next()) {            
                lista.add(new String[]{
                                
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("especie"),
                    set.getString("raza"),
                    set.getString("sexo"),
                    set.getString("color"),
                    String.valueOf(set.getDate("f_nacimiento")),
                    String.valueOf(set.getInt("propietario")),
                    set.getString("perfil"),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException{
        String[] usuario=null;
        String sql="SELECT * FROM paciente WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("especie"),
                    set.getString("raza"),
                    set.getString("sexo"),
                    set.getString("color"),
                    String.valueOf(set.getDate("f_nacimiento")),
                    String.valueOf(set.getInt("propietario")),
                    set.getString("perfil"),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
            };

        }else{
            System.err.println("Class DPaciente.java dice: "
            +"Ocurrio un error al ver paciente ver()");
            throw new SQLException();
        } 
        return usuario;
    }
    
    public Date getDate(String date){
        Calendar c = DateString.StringToDate(date);
        long x = c.getTimeInMillis();
          System.out.println(x);
          Date dateSQL =new Date(x);
            System.out.println(dateSQL.toString());
        return dateSQL;
    }
    
    public Date getDateTime(String date){
        Calendar c = DateString.StringToDateTime(date);
        long x = c.getTimeInMillis();
          System.out.println(x);
          Date dateSQL =new Date(x);
            System.out.println(dateSQL.toString());
        return dateSQL;
    }
    
    public void desconectar() {
        if (conn != null) {
            conn.closeConnection();
        }
    }
        public void iniciarUser(){
        if(us== null){
            this.us = new DUsers();
        }
    }

}
