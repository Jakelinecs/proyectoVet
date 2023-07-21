/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class DDetalleReceta {
    //nombreproducto,indicaciones -> tabladetalleReceta

    ClientPsql conn;
    DUsers us;//campo de usuario, reconocedor de correo

    int id, idreceta;

    String nombreProducto;
    String indicaciones;

    String correo;

//esto define una lista pero para que?
    public static final String[] headers
            = {
                "id", "idreceta", "nombreProducto", "indicaciones"
            };

    public DDetalleReceta() {
        conn = new ClientPsql();
        us = new DUsers();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdreceta(int idreceta) {
        this.idreceta = idreceta;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void insertar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "INSERT INTO detalle_recetas(idreseta, nombre_producto, indicaciones)"
                    + "VALUES (?, ?, ?)";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idreceta);
            ps.setString(2, nombreProducto);
            ps.setString(3, indicaciones);

            if (ps.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }

    }

    public void editar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "UPDATE detalle_recetas SET idreseta=?, nombre_producto=?, indicaciones=?"
                    + "WHERE id=?";
            PreparedStatement ps = conn.conectar().prepareStatement(sql);
            ps.setInt(1, idreceta);
            ps.setString(2, nombreProducto);
            ps.setString(3, indicaciones);
            ps.setInt(4, id);

            if (ps.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }
    }

    public void eliminar() throws SQLException {
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "delete from detalle_recetas where" + "id=?";
            PreparedStatement pr = new ClientPsql().conectar().prepareStatement(sql);
            pr.setInt(1, id);

            if (pr.executeUpdate() == 0) {
                System.out.println("Ocurrio un error");
            }

        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "select *from detalle_recetas ";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                lista.add(new String[]{
                     String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idreseta")),
                    set.getString("nombre_producto"),
                    set.getString("indicaciones")
                });

            }
        }
        return lista;
    }

    public String[] ver() throws SQLException {
        String[] usuario = null;
        iniciarUser();
        int usId = us.getIdByEmail(correo);
        us.desconectar();
        if (usId != -1) {
            String sql = "select * from detalle_recetas WHERE id=?";
            PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                usuario = new String[]{
                    String.valueOf(set.getInt("id")),
                    String.valueOf(set.getInt("idreseta")),
                    set.getString("nombre_producto"),
                    set.getString("indicaciones")
                };
            } else {
                System.err.println("Class DPersona.java dice: "
                        + "Ocurrio un error al ver usuario ver()");

            }

        }
        return usuario;
    }

    public Date getDate(String date) {
        Calendar c = DateString.StringToDate(date);
        long x = c.getTimeInMillis();
        System.out.println(x);
        Date dateSQL = new Date(x);
        System.out.println(dateSQL.toString());
        return dateSQL;
    }
    public void iniciarUser(){
        if(us== null){
            this.us = new DUsers();
        }
    }

}
