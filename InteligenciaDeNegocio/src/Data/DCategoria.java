package Data;

import Utlis.DateString;
import coneccionsocket.ClientPsql;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author HP
 */
public class DCategoria {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private String nombre;
    private String descripcion;
    private boolean estado;
    String created_at,updated_at;

    public static final String[] headers={ 
             "id","nombre","descripcion","estado","created_at","updated_at"};

    public DCategoria() {
        conn = new ClientPsql();
        us = new DUsers();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setCreated_at() {
        this.created_at = DateString.StringToDateActual();
    }

    public void setUpdated_at() {
        this.updated_at = DateString.StringToDateActual();
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
    
    

        public void insertar() throws SQLException{
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql="INSERT INTO cateogrias(id,nombre,descripcion,estado,created_at,updated_at)"+
                    " Values(?,?,?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setBoolean(4, true);
            ps.setDate(5, getDateTime(created_at));
            ps.setDate(6, getDateTime(updated_at));

            if(ps.executeUpdate()==0){
                System.err.println("Class DCategoria.java dice: "
                +"Ocurrio un error al insertar Categoria insertar()");
                throw new SQLException();
            } 
        }
    }
    
    public void editar() throws SQLException{
        
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {

            String sql="UPDATE cateogrias SET nombre=?, descripcion=?, estado=?, updated_at=? "+
                " WHERE id=?";
        
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setBoolean(3, estado);
            ps.setDate(8,getDateTime(updated_at));
            ps.setInt(9, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DCategoria.java dice: "
                +"Ocurrio un error al editar Categoria editar()");
                throw new SQLException();
            } 
        }
    }
    
    public void eliminar() throws SQLException{
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {

            String sql="DELETE FROM cateogrias WHERE"+
                    " id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DCategoria.java dice: "
                +"Ocurrio un error al eliminar Categoria eliminar()");
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
            String sql="SELECT * FROM categorias";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{
                                
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("descripcion"),
                    set.getString("estado"),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException{
        String[] usuario=null;
        String sql="SELECT * FROM categorias WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("descripcion"),
                    set.getString("estado"),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
            };

        }else{
            System.err.println("Class DCategoria.java dice: "
            +"Ocurrio un error al ver Categoria ver()");
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

