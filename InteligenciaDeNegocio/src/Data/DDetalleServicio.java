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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DDetalleServicio {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private int idservicio;
    private String tipo_servicio;
    private int nro_servicio;
    private BigDecimal costo;
    String created_at, updated_at;

    public static final String[] headers = {"id", "idservicio", "tipo_servicio", "nro_servicio", "costo", "created_at", "updated_at"};

    public DDetalleServicio() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public void setNro_servicio(int nro_servicio) {
        this.nro_servicio = nro_servicio;
    }

    // Getters y setters para los atributos
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
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
        return "DetalleServicio{" +
                "id=" + id +
                ", idservicio=" + idservicio +
                ", tipo_servicio='" + tipo_servicio + '\'' +
                ", nro_servicio=" + nro_servicio +
                ", costo=" + costo +
                '}';
    }

    // Métodos para insertar, editar, eliminar, listar y ver los detalles de servicios

    public void insertar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "INSERT INTO detalle_servicios (idservicio, tipo_servicio, nro_servicio, costo, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idservicio);
            ps.setString(2, tipo_servicio);
            ps.setInt(3, nro_servicio);
            ps.setBigDecimal(4, costo);
            ps.setDate(5, new java.sql.Date(getDate(created_at).getTime()));
            ps.setDate(6, new java.sql.Date(getDate(updated_at).getTime()));

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al insertar el detalle de servicio.");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "UPDATE detalle_servicios SET idservicio = ?, tipo_servicio = ?, nro_servicio = ?, costo = ?, updated_at = ? " +
                    "WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idservicio);
            ps.setString(2, tipo_servicio);
            ps.setInt(3, nro_servicio);
            ps.setBigDecimal(4, costo);
            ps.setDate(5, new java.sql.Date(getDate(updated_at).getTime()));
            ps.setInt(6, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al editar el detalle de servicio.");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "DELETE FROM detalle_servicios WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al eliminar el detalle de servicio.");
                throw new SQLException();
            }
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "SELECT * FROM detalle_servicios";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                lista.add(new String[]{
                        String.valueOf(set.getInt("id")),
                        String.valueOf(set.getInt("idservicio")),
                        set.getString("tipo_servicio"),
                        String.valueOf(set.getInt("nro_servicio")),
                        String.valueOf(set.getBigDecimal("costo")),
                        String.valueOf(set.getDate("created_at")),
                        String.valueOf(set.getDate("updated_at")),
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] detalleServicio = null;
        String sql = "SELECT * FROM detalle_servicios WHERE id = ?";
        PreparedStatement ps = conn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            detalleServicio = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idservicio")),
                    set.getString("tipo_servicio"),
                    String.valueOf(set.getInt("nro_servicio")),
                    String.valueOf(set.getBigDecimal("costo")),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at")),
            };
        } else {
            System.err.println("No se encontró el detalle de servicio con el ID especificado.");
            throw new SQLException();
        }
        return detalleServicio;
    }

    public Date getDate(String date) {
        Calendar c = DateString.StringToDate(date);
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
