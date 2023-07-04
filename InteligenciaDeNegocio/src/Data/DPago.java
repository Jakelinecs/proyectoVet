
package Data;

import Utlis.DateString;
import coneccionsocket.ClientPsql;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Marina
 */
public class DPago {
    ClientPsql conn;
    DUsers us;
    int id, numeroReferencia, monto;
    String correo,fecha, nombre, metodoPago,descripcion,estadoPago;

        public static final String[] headers=
            { 
             "id","correo","numeroReferencia","monto","fecha","nombre","metodoPago","descricpion","estadoPago"};

 
    public DPago() {
        conn= new ClientPsql();
    }
      public void setCorreo(String correo) {
        this.correo = correo;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroReferencia(int numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    
   
    
    public void insertar() throws SQLException, ParseException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql="INSERT INTO personas(id,numeroReferencia,monto,fecha,nombre,metodoPago,descripcion,estadoPago)"+
                    " Values(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, numeroReferencia);
            ps.setInt(3, monto);
            ps.setDate(5, getDate(fecha));
            ps.setString(6, nombre);
            ps.setString(7, metodoPago);
            ps.setString(8,descripcion);
            ps.setString(9, estadoPago);

            if(ps.executeUpdate()==0){
                System.err.println("Class DPago.java dice: "
                +"Ocurrio un error al insertar Persona insertar()");
                throw new SQLException();
            } 
        }
    }
    
    
    public void editar() throws SQLException{
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {

            String sql="UPDATE personas SET numeroReferencia=?, monto=?, "
                    + "fecha=?, nombre=?, metodoPago=?, "
                    + "descripcion=?, estadoPago=? "+
                    " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, numeroReferencia);
            ps.setInt(3, monto);
            ps.setDate(5, getDate(fecha));
            ps.setString(6, nombre);
            ps.setString(7, metodoPago);
            ps.setString(8,descripcion);
            ps.setString(9, estadoPago);

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
            String sql="DELETE FROM pago WHERE"+
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
            String sql="SELECT * FROM pagos";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set= ps.executeQuery();

            while (set.next()) {            
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("numeroReferencia"),
                    set.getString("monto"),
                    set.getString("fecha"),
                    set.getString("nombre"),
                    set.getString("metodoPago"),
                    set.getString("descripcion"),
                    set.getString("estadoPago"),
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
            String sql="SELECT * FROM users WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            ResultSet set= ps.executeQuery();

            if(set.next()){
                usuario=new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("numeroReferencia"),
                    set.getString("monto"),
                    set.getString("fecha"),
                    set.getString("nombre"),
                    set.getString("metodoPago"),
                    set.getString("descripcion"),
                    set.getString("estadoPago"),
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
