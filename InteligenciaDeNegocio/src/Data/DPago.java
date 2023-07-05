
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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DPago {
    ClientPsql conn;
    DServicio servicio;
    int id;
    int idservicio;
    String fecha;
    String correo;
    String nombre;
    String numero_referencia;
    double monto;
    String metodo_pago;
    String descripcion;
    String estado_pago;
    DUsers us;

    public static final String[] headers = {"id", "idservicio", "fecha", "nombre", "numero_referencia", "monto", "metodo_pago", "descripcion", "estado_pago"};

    public DPago() {
        conn = new ClientPsql();
        servicio = new DServicio();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumero_referencia(String numero_referencia) {
        this.numero_referencia = numero_referencia;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public void insertar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql = "INSERT INTO pagos(id, idservicio, fecha, nombre, numero_referencia, monto, metodo_pago, descripcion, estado_pago)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, idservicio);
            ps.setDate(3, getDate(fecha));
            ps.setString(4, nombre);
            ps.setString(5, numero_referencia);
            ps.setDouble(6, monto);
            ps.setString(7, metodo_pago);
            ps.setString(8, descripcion);
            ps.setString(9, estado_pago);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DPago.java dice: Ocurrió un error al insertar Pago insertar()");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql = "UPDATE pagos SET idservicio=?, fecha=?, nombre=?, numero_referencia=?, monto=?, metodo_pago=?, descripcion=?, estado_pago=?" +
                    "WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, idservicio);
            ps.setDate(2, getDate(fecha));
            ps.setString(3, nombre);
            ps.setString(4, numero_referencia);
            ps.setDouble(5, monto);
            ps.setString(6, metodo_pago);
            ps.setString(7, descripcion);
            ps.setString(8, estado_pago);
            ps.setInt(9, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DPago.java dice: Ocurrió un error al editar pago editar()");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        String sql = "DELETE FROM pagos WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DPago.java dice: Ocurrió un error al eliminar pago eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set = ps.executeQuery();

        while (set.next()) {
            lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idservicio")),
                    set.getString("fecha"),
                    set.getString("nombre"),
                    set.getString("numero_referencia"),
                    set.getBigDecimal("monto").toString(),
                    set.getString("metodo_pago"),
                    set.getString("descripcion"),
                    set.getString("estado_pago"),
            });
        }

        return lista;
    }

    public String[] ver() throws SQLException {
        String[] pago = null;
        String sql = "SELECT * FROM pagos WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            pago = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idservicio")),
                    set.getString("fecha"),
                    set.getString("nombre"),
                    set.getString("numero_referencia"),
                    set.getBigDecimal("monto").toString(),
                    set.getString("metodo_pago"),
                    set.getString("descripcion"),
                    set.getString("estado_pago"),
            };
        } else {
            System.err.println("Class DPago.java dice: Ocurrió un error al ver pago ver()");
            throw new SQLException();
        }

        return pago;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }

    public java.sql.Date getDate(String date) {
        java.util.Calendar c = DateString.StringToDate(date);
        long x = c.getTimeInMillis();
        java.sql.Date dateSQL = new java.sql.Date(x);
        return dateSQL;
    }
}
