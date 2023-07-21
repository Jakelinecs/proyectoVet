/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

/**
 *
 * @author HP
 */
import Utlis.DateString;
import coneccionsocket.ClientPsql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DActivo {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private String nombre;
    private String detalle;
//    private Date f_adquisicion;
    private String f_adquisicion;
 //   private Date f_mantenimiento;
    private String f_mantenimiento;
    private boolean estado;
    String created_at, updated_at;

    public static final String[] headers = {"id", "nombre", "detalle", "f_adquisicion", "f_mantenimiento", "estado", "created_at", "updated_at"};

    public DActivo() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setF_adquisicion(String f_adquisicion) {
        //this.f_adquisicion = getDate(f_adquisicion);
        this.f_adquisicion = f_adquisicion;
        
    }

    public void setF_mantenimiento(String f_mantenimiento) {
//        this.f_mantenimiento = getDate(f_mantenimiento);
        this.f_mantenimiento = f_mantenimiento;
    }

    // Getters y setters para los atributos
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
        return "Activo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", detalle='" + detalle + '\'' +
                ", f_adquisicion=" + f_adquisicion +
                ", f_mantenimiento=" + f_mantenimiento +
                ", estado=" + estado +
                '}';
    }

    // Métodos para insertar, editar, eliminar, listar y ver los activos

    public void insertar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "INSERT INTO activos (nombre, detalle, f_adquisicion, f_mantenimiento, estado, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, detalle);
            ps.setDate(3, getDate(f_adquisicion));
            ps.setDate(4, getDate(f_mantenimiento));
            ps.setBoolean(5, estado);
            ps.setDate(6, new java.sql.Date(getDate(created_at).getTime()));
            ps.setDate(7, new java.sql.Date(getDate(updated_at).getTime()));

        us.desconectar();
            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al insertar el activo.");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "UPDATE activos SET nombre = ?, detalle = ?, f_adquisicion = ?, f_mantenimiento = ?, estado = ?, updated_at = ? " +
                    "WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, detalle);
            ps.setDate(3, getDate(f_adquisicion));
            ps.setDate(4, getDate(f_mantenimiento));
            ps.setBoolean(5, estado);
            ps.setDate(6, new java.sql.Date(getDate(updated_at).getTime()));
            ps.setInt(7, id);

        us.desconectar();
            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al editar el activo.");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "DELETE FROM activos WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, id);

        us.desconectar();
            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al eliminar el activo.");
                throw new SQLException();
            }
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "SELECT * FROM activos";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                lista.add(new String[]{
                        String.valueOf(set.getInt("id")),
                        set.getString("nombre"),
                        set.getString("detalle"),
                        String.valueOf(set.getDate("f_adquisicion")),
                        String.valueOf(set.getDate("f_mantenimiento")),
                        String.valueOf(set.getBoolean("estado")),
                        String.valueOf(set.getDate("created_at")),
                        String.valueOf(set.getDate("updated_at"))
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] activo = null;
        String sql = "SELECT * FROM activos WHERE id = ?";
        PreparedStatement ps = conn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            activo = new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("nombre"),
                    set.getString("detalle"),
                    String.valueOf(set.getDate("f_adquisicion")),
                    String.valueOf(set.getDate("f_mantenimiento")),
                    String.valueOf(set.getBoolean("estado")),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at"))
            };
                    us.desconectar();

        } else {
            System.err.println("No se encontró el activo con el ID especificado.");
            throw new SQLException();
        }
        return activo;
    }
    public void desconectar() {
        if (conn != null) {
            conn.closeConnection();
        }
    }
    
    public java.sql.Date getDate(String date){
    Calendar c = DateString.StringToDate(date);
    long x = c.getTimeInMillis();
      System.out.println(x);
      java.sql.Date dateSQL =new java.sql.Date(x);
        System.out.println(dateSQL.toString());
    return dateSQL;
    }
    
    public java.sql.Date getDateTime(String date){
    Calendar c = DateString.StringToDateTime(date);
    long x = c.getTimeInMillis();
      System.out.println(x);
      java.sql.Date dateSQL =new java.sql.Date(x);
        System.out.println(dateSQL.toString());
    return dateSQL;
    }
        public void iniciarUser(){
        if(us== null){
            this.us = new DUsers();
        }
    }

}
