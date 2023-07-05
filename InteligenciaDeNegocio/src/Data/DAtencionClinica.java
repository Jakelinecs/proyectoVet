

package Data;

import coneccionsocket.ClientPsql;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Marina
 */
public class DAtencionClinica {
 

    ClientPsql conn;
    DUsers us;//campo de usuario, reconocedor de correo
    String correo;
    
    int id;
    int iddetalle_servicio;
    String motivo;
    String hr;

    public static final String[] headers = {"id", "iddetalleServicio", "motivo", "hr"};

    public DAtencionClinica() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIddetalleServicio(int iddetalleServicio) {
        this.iddetalle_servicio = iddetalleServicio;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public void insertar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql = "INSERT INTO atencion_clinica(id, iddetalle_servicio, motivo, hr)" +
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, java.sql.Types.INTEGER);
            ps.setInt(2, iddetalle_servicio);
            ps.setString(3, motivo);
            ps.setString(4, hr);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DAtencionClinica.java dice: Ocurrió un error al insertar AtencionClinica insertar()");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId!= -1) {
            String sql = "UPDATE atencion_clinica SET iddetalle_servicio=?, motivo=?, hr=?" +
                    "WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, iddetalle_servicio);
            ps.setString(2, motivo);
            ps.setString(3, hr);
            ps.setInt(4, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DAtencionClinica.java dice: Ocurrió un error al editar atencion clinica editar()");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        String sql = "DELETE FROM atencion_clinica WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DAtencionClinica.java dice: Ocurrió un error al eliminar atencion clinica eliminar()");
            throw new SQLException();
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM atencion_clinica";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set = ps.executeQuery();

        while (set.next()) {
            lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("iddetalle_servicio")),
                    set.getString("motivo"),
                    set.getString("hr")
            });
        }

        return lista;
    }

    public String[] ver() throws SQLException {
        String[] atencionClinica = null;
        String sql = "SELECT * FROM atencion_clinica WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            atencionClinica = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("iddetalle_servicio")),
                    set.getString("motivo"),
                    set.getString("hr")
            };
        } else {
            System.err.println("Class DAtencionClinica.java dice: Ocurrió un error al ver atencion clinica ver()");
            throw new SQLException();
        }

        return atencionClinica;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
}


