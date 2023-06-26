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
public class DVacunaAnt {
    ClientPsql conn;
    DProducto us;
    int id;
    String nombre,fecha,fecha_P,correoPac,correoPers ; 
    int dosis , cantidad, codigo ;
    
    public static final String[] headers ={
      "id","nombre","fecha","fecha_P","dosis","cantidad","codigo"
    };
    
     public void setId(int id) {
            this.id = id;
        }

        public void setFecha(String fecha){
            this.fecha=fecha;
        }
        
        public void setFechaP(String fecha_P){
            this.fecha_P=fecha_P;
        }
                
        public void setNombre(String nombre){
            this.nombre=nombre;
        }
        
        public void setCantidad(int cantidad ){
            this.cantidad = cantidad ;
        }
        
        public void setCodigo(int codigo){
           this.codigo = codigo;
        }
    
        public void setDosis(int dosis){
           this.dosis= dosis;
        }
        
    public void insertar() throws SQLException, ParseException{
        int idProd;
        idProd=us.getIdByProduc(nombre);
        if (idProd != -1) {
                String sql="INSERT INTO productos(id,nombre,fecha,fecha_P,dosis,cantidad,codigo)"+
                        " Values(?,?,?,?,?,?,?)";
                PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, nombre);
                ps.setString(3, fecha);
                ps.setString(4, fecha_P);
                ps.setInt(5, dosis);
                ps.setInt(6, cantidad);
                ps.setInt(7, codigo);
                if(ps.executeUpdate()==0){
                    System.err.println("Class DVacunaAnt.java dice: "
                    +"Ocurrio un error al insertar VacunasAnt insertar()");
                    throw new SQLException();
                    } 
                }
    }
        
    public void editar() throws SQLException{
        int idProd;
        idProd=us.getIdByProduc(nombre);
        if (idProd != -1) {
            String sql="UPDATE productos SET nombre = ?, fecha=?, "
                    + "fecha_P = ?,dosis = ?,"+
                    " cantidad = ?,codigo = ?" + 
                    " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setDate(3,getDate(fecha));
            ps.setDate(4,getDate(fecha_P));
            ps.setInt(5, dosis);
            ps.setInt(6, cantidad);
            ps.setInt(7, codigo);
            if(ps.executeUpdate()==0){
                System.err.println("Class DVacunaAnt.java dice: "
                +"Ocurrio un error al actualizar VacunaAnt editar()");
                throw new SQLException();
                } 
            }
    }        
        
    public void eliminar() throws SQLException{
        int idProd;
        idProd=us.getIdByProduc(nombre);
        if (idProd != -1) {
                String sql="DELETE FROM VacunaAnts WHERE"+
                        " id=?";
                PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
                ps.setInt(0, id);

                if(ps.executeUpdate()==0){
                    System.err.println("Class DVacunaAnt.java dice: "
                    +"Ocurrio un error al eliminar VacunaAnt eliminar()");
                    throw new SQLException();
                } 
            }
    }
        
    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        int idProd;
        idProd=us.getIdByProduc(nombre);
        if (idProd != -1) {
            String sql="SELECT * FROM VacunaAnts";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("fecha"),
                    set.getString("fecha_P"),
                    set.getString("dosis"),
                    set.getString("cantidad"),
                    set.getString("codigo"),
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException{
        String[] usuario=null;
        int idProd;
        idProd=us.getIdByProduc(nombre);
        if (idProd != -1) {
            String sql="SELECT * FROM VacunaAnts WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            ResultSet set= ps.executeQuery();

            if(set.next()){
                usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("fecha"),
                    set.getString("fecha_P"),
                    set.getString("dosis"),
                    set.getString("cantidad"),
                    set.getString("codigo"),
                };

            }else{
                System.err.println("Class DVacunaAnt.java dice: "
                +"Ocurrio un error al ver VacunasAnt ver()");
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
