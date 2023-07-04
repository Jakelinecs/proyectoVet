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
    ClientPsql coneccion;
    DUsers us;//campo de usuario, reconocedor de correo
    //agregar pago,detalleprocedimiento
    int id, idAtencionClinica, idReceta;
    String pago, detalleProcedimiento;
    String correo;

    public static final String[] headers
            = {
                "id", "idAtncionClinica", "idReceta"};

    public DDetalleAtencion() {
        coneccion = new ClientPsql();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAtencionClinica(int idAtencionClinica) {
        this.idAtencionClinica = idAtencionClinica;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public void setDetalleProcedimiento(String detalleProcedimiento) {
        this.detalleProcedimiento = detalleProcedimiento;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void insertar() throws SQLException, ParseException {

        int dato;

        dato = us.getIdByEmail(correo);//compara si existe
        if (dato != -1) {
            String sql = "insert into detalleAtencion(id,idAtencionClinica,idReceta,pago,detalleProcedimiento)" + " Values(?,?,?,?,?)";
            PreparedStatement pr = new ClientPsql().conectar().prepareStatement(sql);
            pr.setInt(1, id);
            pr.setInt(2, idAtencionClinica);
            pr.setInt(3, idReceta);
            pr.setString(4, pago);
            pr.setString(5, detalleProcedimiento);

            if (pr.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }

    }

    public void editar() throws SQLException {
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
            String sql = "update detalleAtencion set pago=?"
                    + "detalleProcedimiento=?"
                    + "where id=?";
            PreparedStatement pr = new ClientPsql().conectar().prepareStatement(sql);
            pr.setInt(1, id);
            pr.setInt(2, idAtencionClinica);
            pr.setInt(3, idReceta);
            pr.setString(4, pago);
            pr.setString(5, detalleProcedimiento);

            if (pr.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }
    }

    public void eliminar() throws SQLException {
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
            String sql = "delete from detalleAtencion where" + "id=?";
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
            String sql = "select *from detalleAtencion";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                lista.add(new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idpaciente")),
                    String.valueOf(set.getInt("idmedico")),
                    set.getString("pago"),
                    set.getString("detalleProcedimiento")

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
            String sql = "select * from detalleAtencion WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(0, id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                usuario = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idAtencionClinica")),
                    String.valueOf(set.getInt("idReceta")),
                    set.getString("pago"),
                    set.getString("detalleProcedimiento")
                };
            } else {
                System.err.println("Class DPersona.java dice: "
                        + "Ocurrio un error al ver usuario ver()");

            }

        }
        return usuario;
    }

    public void desconectar() {
        if (coneccion != null) {
            coneccion.closeConection();
        }
    }
}
