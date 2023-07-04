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

public class DTipoServicio {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private String servicio;
    private String detalle;
    private BigDecimal costo;
    private int idProducto;
    String created_at, updated_at;

    public static final String[] headers = {"id", "servicio", "detalle", "costo", "idProducto", "created_at", "updated_at"};

    public DTipoServicio() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    // Getters y setters para los atributos
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
        return "TipoServicio{" +
                "id=" + id +
                ", servicio='" + servicio + '\'' +
                ", detalle='" + detalle + '\'' +
                ", costo=" + costo +
                ", idProducto=" + idProducto +
                '}';
    }

    // Métodos para insertar, editar, eliminar, listar y ver los tipos de servicios

    public void insertar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "INSERT INTO tipo_servicios (servicio, detalle, costo, idProducto, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setString(1, servicio);
            ps.setString(2, detalle);
            ps.setBigDecimal(3, costo);
            ps.setInt(4, idProducto);
            ps.setDate(5, new java.sql.Date(getDate(created_at).getTime()));
            ps.setDate(6, new java.sql.Date(getDate(updated_at).getTime()));

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al insertar el tipo de servicio.");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "UPDATE tipo_servicios SET servicio = ?, detalle = ?, costo = ?, idProducto = ?, updated_at = ? " +
                    "WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setString(1, servicio);
            ps.setString(2, detalle);
            ps.setBigDecimal(3, costo);
            ps.setInt(4, idProducto);
            ps.setDate(5, new java.sql.Date(getDate(updated_at).getTime()));
            ps.setInt(6, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al editar el tipo de servicio.");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "DELETE FROM tipo_servicios WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al eliminar el tipo de servicio.");
                throw new SQLException();
            }
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        int usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "SELECT * FROM tipo_servicios";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                lista.add(new String[]{
                        String.valueOf(set.getInt("id")),
                        set.getString("servicio"),
                        set.getString("detalle"),
                        String.valueOf(set.getBigDecimal("costo")),
                        String.valueOf(set.getInt("idProducto")),
                        String.valueOf(set.getDate("created_at")),
                        String.valueOf(set.getDate("updated_at"))
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] tipoServicio = null;
        String sql = "SELECT * FROM tipo_servicios WHERE id = ?";
        PreparedStatement ps = conn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            tipoServicio = new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("servicio"),
                    set.getString("detalle"),
                    String.valueOf(set.getBigDecimal("costo")),
                    String.valueOf(set.getInt("idProducto")),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at"))
            };
        } else {
            System.err.println("No se encontró el tipo de servicio con el ID especificado.");
            throw new SQLException();
        }
        return tipoServicio;
    }

    public Date getDate(String date) {
        Calendar c = DateString.StringToDate(date);
        return new Date(c.getTimeInMillis());
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
}
