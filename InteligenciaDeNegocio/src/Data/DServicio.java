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
public class DServicio {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private String responsable;
    private int idpaciente;
    private int idmedico;
    private Date fecha;
    private double  total;
    String created_at, updated_at;

    public static final String[] headers = {"id", "responsable", "idpaciente", "idmedico", "fecha", "total", "created_at", "updated_at"};

    public DServicio() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public void setIdmedico(int idmedico) {
        this.idmedico = idmedico;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // Getters y setters para los atributos
    public void setTotal(double total) {
        this.total = total;
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
        return "Servicio{" +
                "id=" + id +
                ", responsable='" + responsable + '\'' +
                ", idpaciente=" + idpaciente +
                ", idmedico=" + idmedico +
                ", fecha=" + fecha +
                ", total=" + total +
                '}';
    }

    // Métodos para insertar, editar, eliminar, listar y ver los servicios
public void insertar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "INSERT INTO servicios (responsable, idpaciente, idmedico, fecha, total, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setString(1, responsable);
            ps.setInt(2, idpaciente);
            ps.setInt(3, idmedico);
            ps.setDate(4, new java.sql.Date(fecha.getTime()));
            ps.setDouble(5, total);
            ps.setDate(6, new java.sql.Date(getDate(created_at).getTime()));
            ps.setDate(7, new java.sql.Date(getDate(updated_at).getTime()));

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al insertar el servicio.");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "UPDATE servicios SET responsable = ?, idpaciente = ?, idmedico = ?, fecha = ?, total = ?, updated_at = ? " +
                    "WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setString(1, responsable);
            ps.setInt(2, idpaciente);
            ps.setInt(3, idmedico);
            ps.setDate(4, new java.sql.Date(fecha.getTime()));
            ps.setDouble(5, total);
            ps.setDate(6, new java.sql.Date(getDate(updated_at).getTime()));
            ps.setInt(7, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al editar el servicio.");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "DELETE FROM servicios WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al eliminar el servicio.");
                throw new SQLException();
            }
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "SELECT * FROM servicios";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                lista.add(new String[]{
                        String.valueOf(set.getInt("id")),
                        set.getString("responsable"),
                        String.valueOf(set.getInt("idpaciente")),
                        String.valueOf(set.getInt("idmedico")),
                        String.valueOf(set.getDate("fecha")),
                        set.getString("total"),
                        String.valueOf(set.getDate("created_at")),
                        String.valueOf(set.getDate("updated_at")),
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] servicio = null;
        String sql = "SELECT * FROM servicios WHERE id = ?";
        PreparedStatement ps = conn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            servicio = new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("responsable"),
                    String.valueOf(set.getInt("idpaciente")),
                    String.valueOf(set.getInt("idmedico")),
                    String.valueOf(set.getDate("fecha")),
                    set.getString("total"),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
            };
        } else {
            System.err.println("No se encontró el servicio con el ID especificado.");
            throw new SQLException();
        }
        return servicio;
    }

    
    
    
    
    
    public Date getDate(String date) {
        Calendar c = DateString.StringToDate(date);
        long x = c.getTimeInMillis();
        Date dateSQL = new Date(x);
        return dateSQL;
    }

    public Date getDateTime(String date) {
        Calendar c = DateString.StringToDateTime(date);
        long x = c.getTimeInMillis();
        Date dateSQL = new Date(x);
        return dateSQL;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
}
