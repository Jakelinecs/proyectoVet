/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

/**
 *
 * @author HP
 */
import coneccionsocket.ClientPsql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DInventario {
    ClientPsql conn;
    DUsers us;
    String correo;
    private int id;
    private int idactivo;
    private int idproducto;
    private String detalle;

    public static final String[] headers = {"id", "idactivo", "idproducto", "detalle", "created_at", "updated_at"};

    public DInventario() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdactivo(int idactivo) {
        this.idactivo = idactivo;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    // Getters y setters para los atributos
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", idactivo=" + idactivo +
                ", idproducto=" + idproducto +
                ", detalle='" + detalle + '\'' +
                '}';
    }

    // Métodos para insertar, editar, eliminar, listar y ver los elementos del inventario

    public void insertar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "INSERT INTO inventarios (idactivo, idproducto, detalle, created_at, updated_at) " +
                    "VALUES (?, ?, ?, now(), now())";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idactivo);
            ps.setInt(2, idproducto);
            ps.setString(3, detalle);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al insertar el elemento del inventario.");
                throw new SQLException();
            }
        }
    }

    public void editar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "UPDATE inventarios SET idactivo = ?, idproducto = ?, detalle = ?, updated_at = now() " +
                    "WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idactivo);
            ps.setInt(2, idproducto);
            ps.setString(3, detalle);
            ps.setInt(4, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al editar el elemento del inventario.");
                throw new SQLException();
            }
        }
    }

    public void eliminar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "DELETE FROM inventarios WHERE id = ?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Ocurrió un error al eliminar el elemento del inventario.");
                throw new SQLException();
            }
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "SELECT * FROM inventarios";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                lista.add(new String[]{
                        String.valueOf(set.getInt("id")),
                        String.valueOf(set.getInt("idactivo")),
                        String.valueOf(set.getInt("idproducto")),
                        set.getString("detalle"),
                        String.valueOf(set.getDate("created_at")),
                        String.valueOf(set.getDate("updated_at"))
                });
            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] elemento = null;
        String sql = "SELECT * FROM inventarios WHERE id = ?";
        PreparedStatement ps = conn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set = ps.executeQuery();

        if (set.next()) {
            elemento = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idactivo")),
                    String.valueOf(set.getInt("idproducto")),
                    set.getString("detalle"),
                    String.valueOf(set.getDate("created_at")),
                    String.valueOf(set.getDate("updated_at"))
            };
        } else {
            System.err.println("No se encontró el elemento del inventario con el ID especificado.");
            throw new SQLException();
        }
        return elemento;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConnection();
        }
    }
        public void iniciarUser(){
        if(us== null){
            this.us = new DUsers();
        }
    }

}
