/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class DProducto {
    ClientPsql conn;
    DUsers us;
    String correo;
            
    private int id; 
    private int idcategoria; 
    private String codigo;
    private String nombre;
    private double precio_venta;
    private int stock;
    private String descripcion;
    private boolean estado;
    String created_at,updated_at;

    public static final String[] headers={ 
             "id","idcategoria","codigo","nombre","precio_venta","stock",
        "descripcion","estado","created_at","updated_at"};

    public DProducto() {
        conn = new ClientPsql();
        us = new DUsers();

    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    

        public void insertar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="INSERT INTO productos(id,idcategoria,codigo,nombre,precio_venta,stock,"
                    + "descripcion,estado,created_at,updated_at)"+
                    " Values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, idcategoria);
            ps.setString(3, codigo);
            ps.setString(4, nombre);
            ps.setDouble(5, precio_venta);
            ps.setInt(6, stock);
            ps.setString(7, descripcion);
            ps.setBoolean(8, true);
            ps.setDate(9, getDateTime(created_at));
            ps.setDate(10, getDateTime(updated_at));

            if(ps.executeUpdate()==0){
                System.err.println("Class DProducto.java dice: "
                +"Ocurrio un error al insertar Producto insertar()");
                throw new SQLException();
            } 
        }
    }
    

    public void editar() throws SQLException{
        
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {

            String sql="UPDATE productos SET nombre=?, descripcion=?, estado=?,idcategoria=?,"
                    + "codigo=?,precio_venta=?,stock=?, updated_at=? "+
                " WHERE id=?";
        
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setBoolean(3, estado);
            ps.setInt(3, idcategoria);
            ps.setString(3, codigo);
            ps.setDouble(3, precio_venta);
            ps.setInt(3, stock);
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
                int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {

            String sql="DELETE FROM productos WHERE"+
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
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="SELECT * FROM productos ";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{

                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idcategoria")),
                    set.getString("codigo"),
                    set.getString("nombre"),
                    String.valueOf(set.getDouble("precio_venta")),
                    String.valueOf(set.getInt("stock")),
                    set.getString("descripcion"),
                    String.valueOf(set.getBoolean("estado")),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException{
        String[] usuario=null;
        String sql="SELECT * FROM productos WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(0, id);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idcategoria")),
                    set.getString("codigo"),
                    set.getString("nombre"),
                    String.valueOf(set.getDouble("precio_venta")),
                    String.valueOf(set.getInt("stock")),
                    set.getString("descripcion"),
                    String.valueOf(set.getBoolean("estado")),
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
            conn.closeConection();
        }
    }
    

}

