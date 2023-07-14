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
public class DAyuda {

    ClientPsql conn;
    DUsers us;
    int id;
    String comando, operacion, ejemplo;

    public static final String[] headers
            = {
                "id", "comando", "operacion", "ejemplo"};

    public DAyuda() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public void insertar() throws SQLException {
        String sql = "INSERT INTO ayuda(comando,operacion,ejemplo)"
                + " Values(?,?,?)";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setString(1, comando);
        ps.setString(2, operacion);
        ps.setString(3, ejemplo);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DAyuda.java dice: "
                    + "Ocurrio un error al insertar Ayuda insertar()");
            throw new SQLException();
        }
    }
/*
    public void editar() throws SQLException {
            String sql = "UPDATE bitacora_email SET comando=?, correo=?, "
                    + "fecha_hr=?"
                    + " WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, comando);
            ps.setString(2, operacion);
            ps.setString(3, ejemplo);
            ps.setInt(4, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DBitacora.java dice: "
                        + "Ocurrio un error al editar Bitacora editar()");
                throw new SQLException();
            }
    }

    public void eliminar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "DELETE FROM bitacora_email WHERE"
                    + " id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DBitacora.java dice: "
                        + "Ocurrio un error al eliminar Bitacora eliminar()");
                throw new SQLException();
            }
        }
    }
*/
    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM ayuda";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set = ps.executeQuery();

        while (set.next()) {
            lista.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("comando"),
                set.getString("operacion"),
                set.getString("ejemplo"),});
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] dato = null;
            String sql = "SELECT * FROM ayuda ";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            if (set.next()) {
                dato = new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("comando"),
                    set.getString("operacion"),
                    set.getString("ejemplo"),};

            } else {
                System.err.println("Class DAyuda.java dice: "
                        + "Ocurrio un error al ver Ayuda ver()");
                throw new SQLException();
            }
        return dato;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }

    public Date getDate(String date) {
        Calendar c = DateString.StringToDate(date);
        long x = c.getTimeInMillis();
        System.out.println(x);
        Date dateSQL = new Date(x);
        System.out.println(dateSQL.toString());
        return dateSQL;
    }

    public Date getDateTime(String date) {
        Calendar c = DateString.StringToDateTime(date);
        long x = c.getTimeInMillis();
        System.out.println(x);
        Date dateSQL = new Date(x);
        System.out.println(dateSQL.toString());
        return dateSQL;
    }

    public List<String[]> listar(String get) throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM ayuda WHERE comando = "+get+" ;";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            lista.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("comando"),
                set.getString("operacion"),
                set.getString("ejemplo"),});
        }
        return lista;
    }

}
