/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class DProducto {
    ClientPsql conn;
    DUsers us;
    int id;
    String Nombre ; 
    int cantidad ,codigo ;
    
    public static final String[] headers = {
      "id","Nombre","cantidad","codigo"  
    };
    
        public void setId(int id) {
            this.id = id;
        }
        
        public void setNombre(String Nombre){
            this.Nombre=Nombre;
        }
        
        public void setCantidad(int cantidad ){
            this.cantidad = cantidad ;
        }
        
        public void setCodigo(int codigo){
           this.codigo = codigo;
        }
        
        public void insertar() throws SQLException, ParseException{
        int usId;
        usId = us.getIdByEmail(Nombre);
        if (usId!= -1) {
            String sql="INSERT INTO productos(id,Nombre,cantidad,codigo)"+
                    " Values(?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, Nombre);
            ps.setInt(3, cantidad);
            ps.setInt(4, codigo);
            if(ps.executeUpdate()==0){
                System.err.println("Class DProducto.java dice: "
                +"Ocurrio un error al insertar Persona insertar()");
                throw new SQLException();
                } 
            }
        }
        
        public void editar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(Nombre);
        if (usId!= -1) {
            String sql="UPDATE productos SET Nombre=?, cantidad=?, "
                    + "codigo=?"+
                    " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, Nombre);
            ps.setInt(3, cantidad);
            ps.setInt(4, codigo);
            if(ps.executeUpdate()==0){
                System.err.println("Class DProducto.java dice: "
                +"Ocurrio un error al insertar Persona editar()");
                throw new SQLException();
                } 
            }
        }
        
    public void eliminar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(Nombre);
        if (usId!= -1) {
            String sql="DELETE FROM productos WHERE"+
                    " id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);

            if(ps.executeUpdate()==0){
                System.err.println("Class DProduto.java dice: "
                +"Ocurrio un error al eliminar Producto eliminar()");
                throw new SQLException();
            } 
        }
    }
        
    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        int usId;
        usId = us.getIdByEmail(Nombre);
        if (usId!= -1) {
            String sql="SELECT * FROM productos";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("Nombre"),
                    String.valueOf(set.getInt("cantidad")),
                    String.valueOf(set.getInt("codigo")),
                });
            }
        }
        return lista;
    }
    
    public String[] ver() throws SQLException{
        String[] producto=null;
        int usId;
        usId = us.getIdByEmail(Nombre);
        if (usId!= -1) {
            String sql="SELECT * FROM productos WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            ResultSet set= ps.executeQuery();

            if(set.next()){
                producto=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("Nombre"),
                    String.valueOf(set.getInt("cantidad")),
                    String.valueOf(set.getInt("codigo")),
                };
            }else{
                System.err.println("Class DProdctos.java dice: "
                +"Ocurrio un error al ver Producto ver()");
                throw new SQLException();
            } 
        }
        return producto;
    }
    
    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
    
    public int getIdByProduc(String prod) throws SQLException{
        int i=-1;
        String sql="SELECT * FROM productos WHERE nombre=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setString(1, prod);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            i= set.getInt("id");

        }else{
            System.err.println("Class DProducto.java dice: "
            +"Ocurrio un error al buscar el id producto getIdByProduc()");
            throw new SQLException();
        } 
        return i;
    }
}
