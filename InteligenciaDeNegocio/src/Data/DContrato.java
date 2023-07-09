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
public class DContrato {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private String detalle;
    private Date f_ini;
    private Date f_fin;
    private int idpersonal;
    private int iduser;
    private boolean estado;
    private String created_at;
    private String updated_at;

    public static final String[] headers = {
            "id", "detalle", "f_ini", "f_fin", "idpersonal", "iduser", "estado", "created_at", "updated_at"
    };

    public DContrato() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getF_ini() {
        return f_ini;
    }

    public void setF_ini(Date f_ini) {
        this.f_ini = f_ini;
    }

    public Date getF_fin() {
        return f_fin;
    }

    public void setF_fin(Date f_fin) {
        this.f_fin = f_fin;
    }

    public int getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(int idpersonal) {
        this.idpersonal = idpersonal;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public boolean isEstado() {
        return estado;
    }

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
        return "Contrato{" +
                "id=" + id +
                ", detalle='" + detalle + '\'' +
                ", f_ini=" + f_ini +
                ", f_fin=" + f_fin +
                ", idpersonal=" + idpersonal +
                ", iduser=" + iduser +
                ", estado=" + estado +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public void insertar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "INSERT INTO contratos(id, detalle, f_ini, f_fin, idpersonal, iduser, estado, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, detalle);
            ps.setDate(3, f_ini);
            ps.setDate(4, f_fin);
            ps.setInt(5, idpersonal);
            ps.setInt(6, iduser);
            ps.setBoolean(7, estado);
            ps.setString(8, created_at);
            ps.setString(9, updated_at);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DContrato.java dice: Ocurrió un error al insertar Contrato insertar()");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "UPDATE contratos SET detalle = ?, f_ini = ?, f_fin = ?, idpersonal = ?, iduser = ?, estado = ?, " +
                    "updated_at = ? WHERE id = ?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setString(1, detalle);
            ps.setDate(2, f_ini);
            ps.setDate(3, f_fin);
            ps.setInt(4, idpersonal);
            ps.setInt(5, iduser);
            ps.setBoolean(6, estado);
            ps.setString(7, updated_at);
            ps.setInt(8, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DContrato.java dice: Ocurrió un error al editar Contrato editar()");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "DELETE FROM contratos WHERE id = ?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DContrato.java dice: Ocurrió un error al eliminar Contrato eliminar()");
                throw new SQLException();
            }
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        int usId;
        usId = us.getIdByEmail(correo);
        if (usId != -1) {
            String sql = "SELECT * FROM contratos";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                lista.add(new String[]{
                        String.valueOf(set.getInt("id")),
                        set.getString("detalle"),
                        String.valueOf(set.getDate("f_ini")),
                        String.valueOf(set.getDate("f_fin")),
                        String.valueOf(set.getInt("idpersonal")),
                        String.valueOf(set.getInt("iduser")),
                        String.valueOf(set.getBoolean("estado")),
                        String.valueOf(set.getDate("created_at")),
                        String.valueOf(set.getDate("updated_at"))
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] contrato = null;
        String sql = "SELECT * FROM contratos WHERE id = ?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            contrato = new String[]{
                    String.valueOf(set.getInt("id")),
                    set.getString("detalle"),
                    String.valueOf(set.getDate("f_ini")),
                    String.valueOf(set.getDate("f_fin")),
                    String.valueOf(set.getInt("idpersonal")),
                    String.valueOf(set.getInt("iduser")),
                    String.valueOf(set.getBoolean("estado")),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at"))
            };

        } else {
            System.err.println("Class DContrato.java dice: Ocurrió un error al ver Contrato ver()");
            throw new SQLException();
        }
        return contrato;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
    
}
