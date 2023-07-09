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
public class DBitacora {
    
    ClientPsql conn;
    DUsers us;
    int id;
    String comando,correo,fecha_hr;

        public static final String[] headers=
            { 
             "id","comando","correo","fecha_hr"};

    public DBitacora() {
        conn= new ClientPsql();
        us = new DUsers();
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_hr() {
        return fecha_hr;
    }

    public void setFecha_hr(String fecha_hr) {
        this.fecha_hr = fecha_hr;
    }


    public void setFecha_hr() {
        this.fecha_hr = DateString.StringToDateActual();
    }
    
    
    
    
    public void insertar() throws SQLException, ParseException{
        int usId = 1;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="INSERT INTO bitacora_email(comando,correo,fecha_hr)"+
                    " Values(?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, comando);
            ps.setString(2, correo);
            ps.setString(3, fecha_hr);

            if(ps.executeUpdate()==0){
                System.err.println("Class DBitacora.java dice: "
                +"Ocurrio un error al insertar Bitacora insertar()");
                throw new SQLException();
            } 
        }
    }
    
    
    public void editar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {

            String sql="UPDATE bitacora_email SET comando=?, correo=?, "
                    + "fecha_hr=?"+
                    " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, comando);
            ps.setString(2, correo);
            ps.setString(3, fecha_hr);
            ps.setInt(4, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DBitacora.java dice: "
                +"Ocurrio un error al editar Bitacora editar()");
                throw new SQLException();
            } 
        }
    }
        
    public void eliminar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="DELETE FROM bitacora_email WHERE"+
                    " id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DBitacora.java dice: "
                +"Ocurrio un error al eliminar Bitacora eliminar()");
                throw new SQLException();
            } 
        }
    }

    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="SELECT * FROM bitacora_email";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("comando"),
                    set.getString("correo"),
                    set.getString("fecha_hr"),
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
            String sql="SELECT * FROM (SELECT * FROM bitacora_email  ORDER BY fecha_hr DESC) WHERE ROWNUM = 1;";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet set= ps.executeQuery();

            if(set.next()){
                usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("comando"),
                    set.getString("correo"),
                    set.getString("fecha_hr"),
                };

            }else{
                System.err.println("Class DBitacora.java dice: "
                +"Ocurrio un error al ver Bitacora ver()");
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
