/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */

import Data.DInventario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NInventario implements INegocio {
    private DInventario dato;

    public NInventario() {
        dato = new DInventario();
    }

    public static final String[] headers = {"id", "idactivo", "idproducto", "detalle", "created_at", "updated_at"};
    /*inventario add<identificadorActivo;identificadorProducto;Detalle>
    inventario add<1;2; productos>
    inventario delete<1>
    inventario modify<1;2; lacteos>
    inventario list<>
    inventario ver<1>
   */
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setIdactivo(Integer.parseInt(parametros.get(0)));
            dato.setIdproducto(Integer.parseInt(parametros.get(1)));
            dato.setDetalle(parametros.get(2));
            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setIdactivo(Integer.parseInt(parametros.get(1)));
            dato.setIdproducto(Integer.parseInt(parametros.get(2)));
            dato.setDetalle(parametros.get(3));
            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setCorreo(email);

            dato.eliminar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<String[]> listar(String email) {
        List<String[]> lista = new ArrayList<>();
        dato.setCorreo(email);
        try {
            lista = dato.listar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public String[] ver(List<String> parametros, String email) {
        String[] d = null;
        try {
            dato.setCorreo(email);
            dato.setId(Integer.parseInt(parametros.get(0)));

            d = dato.ver();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
        @Override
    public String[]  headers() {
        return dato.headers;
    }

}
