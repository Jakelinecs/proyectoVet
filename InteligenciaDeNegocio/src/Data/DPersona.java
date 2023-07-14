package Data;

import Utlis.DateString;
import coneccionsocket.ClientPsql;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Calendar;
/**
 *
 * @author HP
 */
public class DPersona {
    
    ClientPsql conn;
    DUsers us;
    int id;
    String correo,nombre,app_apm,sexo,celular,direccion,f_nacimiento,created_at,updated_at;

        public static final String[] headers=
            { 
             "id","nombre","app_apm","sexo","f_nacimiento","celular","direccion","created_at","updated_at"};

    public DPersona() {
        conn= new ClientPsql();
        us = new DUsers();
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public void setF_nacimiento(String f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApp_apm(String app_apm) {
        this.app_apm = app_apm;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCreated_at() {
        this.created_at = DateString.StringToDateActual();
    }

    public void setUpdated_at() {
        this.updated_at = DateString.StringToDateActual();
    }
    
    
    
    
    public void insertar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="INSERT INTO personas(id,nombre,app_apm,sexo,f_nacimiento,celular,direccion,created_at,updated_at)"+
                    " Values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, app_apm);
            ps.setString(4, sexo);
            ps.setDate(5, getDate(f_nacimiento));
            ps.setString(6, celular);
            ps.setString(7, direccion);
            ps.setDate(8, getDateTime(created_at));
            ps.setDate(9, getDateTime(updated_at));

            if(ps.executeUpdate()==0){
                System.err.println("Class DPersona.java dice: "
                +"Ocurrio un error al insertar Persona insertar()");
                throw new SQLException();
            } 
        }
    }
    
    
    public void editar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {

            String sql="UPDATE personas SET nombre=?, app_apm=?, "
                    + "sexo=?, f_nacimiento=?, celular=?, "
                    + "direccion=?, updated_at=? "+
                    " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, app_apm);
            ps.setString(3, sexo);
            ps.setDate(4,getDate(f_nacimiento));
            ps.setString(5, celular);
            ps.setString(6, direccion);
            ps.setDate(7,getDateTime(updated_at));
            ps.setInt(8, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DPersona.java dice: "
                +"Ocurrio un error al editar usuario editar()");
                throw new SQLException();
            } 
        }
    }
        
    public void eliminar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="DELETE FROM personas WHERE"+
                    " id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DPersona.java dice: "
                +"Ocurrio un error al eliminar usuario eliminar()");
                throw new SQLException();
            } 
        }
    }

    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="SELECT * FROM personas";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("app_apm"),
                    set.getString("sexo"),
                    set.getString("f_nacimiento"),
                    set.getString("celular"),
                    set.getString("direccion"),
                    set.getString("created_at"),
                    set.getString("updated_at"),
                });
            }
        }
        return lista;
    }
    
    public String[] ver() throws SQLException{
        String[] usuario=null;
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="SELECT * FROM personas WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet set= ps.executeQuery();

            if(set.next()){
                usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("app_apm"),
                    set.getString("sexo"),
                    set.getString("f_nacimiento"),
                    set.getString("celular"),
                    set.getString("direccion"),
                    set.getString("created_at"),
                    set.getString("updated_at"),
                };

            }else{
                System.err.println("Class DPersona.java dice: "
                +"Ocurrio un error al ver usuario ver()");
                throw new SQLException();
            } 
        }
        return usuario;
    }
    
    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
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
    
}
