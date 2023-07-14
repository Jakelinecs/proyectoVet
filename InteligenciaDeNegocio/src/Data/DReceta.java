/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import coneccionsocket.ClientPsql;
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
public class DReceta {

    ClientPsql conn;
    DUsers us;//campo de usuario, reconocedor de correo
    int id, idatencion;
    int numeroRecetario;
    String estado;
    String correo;

//esto define una lista pero para que?
    public static final String[] headers
            = {
                "id", "idatencion", "numero_recetario", "estado"
            };

    public DReceta() {
        conn = new ClientPsql();
        us= new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroRecetario(int numeroRecetario) {
        this.numeroRecetario = numeroRecetario;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setIdatencion(int idatencion) {
        this.idatencion = idatencion;
    }

    public void insertar() throws SQLException {

        int dato;
        dato = us.getIdByEmail(correo);//compara si existe
        if (dato != -1) {
            String sql = "INSERT INTO recetas(idatencion, numero_recetario, estado)"
                    + "VALUES (?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idatencion);
            ps.setInt(2, numeroRecetario);
            ps.setString(3, estado);

            if (ps.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }

    }

    public void editar() throws SQLException {
        int dato;
        dato = us.getIdByEmail(correo);
        if (dato != -1) {
            String sql = "UPDATE recetas SET idatencion=?, numero_recetario=?, estado=?"
                    + "WHERE id=?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idatencion);
            ps.setInt(2, numeroRecetario);
            ps.setString(3, estado);
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
            String sql = "delete from recetas where" + "id=?";
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
            String sql = "select *from recetas";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                lista.add(new String[]{
                     String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idatencion")),
                    String.valueOf(set.getInt("numero_recetario")),
                    set.getString("estado")
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
            String sql = "select * from recetas WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                usuario = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idatencion")),
                    String.valueOf(set.getInt("numero_recetario")),
                    set.getString("estado")
                };
            } else {
                System.err.println("Class DReceta.java dice: "
                        + "Ocurrio un error al ver usuario ver()");

            }

        }
        return usuario;
    }

}
