package Data;

import coneccionsocket.ClientPsql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marina
 */
public class DDetalleAtencion {

    /*
    id
    idAtencionClinica
    idReceta
 
     */
    ClientPsql conn;
    DUsers us;//campo de usuario, reconocedor de correo
    //agregar pago,detalleprocedimiento
    private int id;
    private int idAtencion;
    private String detalleProcedimiento;
    private double costo;
    String correo;

    public static final String[] headers
            = {
                "id", "idAtncionClinica", "detalle", "costo"};

    public DDetalleAtencion() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAtencion(int idAtencion) {
        this.idAtencion = idAtencion;
    }

    public void setDetalleProcedimiento(String detalleProcedimiento) {
        this.detalleProcedimiento = detalleProcedimiento;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void insertar() throws SQLException {

        int dato;

        dato = us.getIdByEmail(correo);//compara si existe
        if (dato != -1) {
            String sql = "INSERT INTO detalle_atencions (id, idatencion, detalle_procedimiento, costo) "
                    + "VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idAtencion);
            ps.setString(2, detalleProcedimiento);
            ps.setDouble(3, costo);

            if (ps.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }

    }

    public void editar() throws SQLException {
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
            String sql = "UPDATE detalle_atencions SET idatencion=?, detalle_procedimiento=?, costo=? "
                    + "WHERE id=?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idAtencion);
            ps.setString(2, detalleProcedimiento);
            ps.setDouble(3, costo);
            ps.setInt(4, id);

            if (ps.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }
    }

    public void eliminar() throws SQLException {
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
            String sql = "delete from detalle_atencions where " + "id=?";
            PreparedStatement pr = new ClientPsql().conectar().prepareStatement(sql);
            pr.setInt(1, id);

            if (pr.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
             String sql = "SELECT * FROM detalle_atencions";
        PreparedStatement ps = conn.conectar().prepareStatement(sql);
        ResultSet set = ps.executeQuery();

        while (set.next()) {
            lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idatencion")),
                    set.getString("detalle_procedimiento"),
                    String.valueOf(set.getDouble("costo"))
            });
        }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] usuario = null;
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
            String sql = "select * from detalle_atencions WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                usuario = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idatencion")),
                    String.valueOf(set.getInt("detalle_procedimiento")),
                    set.getString("costo"),
                };
            } else {
                System.err.println("Class DPersona.java dice: "
                        + "Ocurrio un error al ver usuario ver()");

            }

        }
        return usuario;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
}
