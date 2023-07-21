/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */

import Data.DTipoServicio;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NTipoServicio implements INegocio {
    private DTipoServicio dato;

    public NTipoServicio() {
        dato = new DTipoServicio();
    }

    public static final String[] headers = {"id", "servicio", "detalle", "costo", "idProducto", "created_at", "updated_at"};
        /*tipoServicio add<servicio;detalle;costo;identificadorProducto>
    tipoServicio add<1;peluqueria;40;5>
    tipoServicio delete<2>
    tipoServicio modify<1;desparacitacion;40;4>
    tipoServicio list<>
    tipoServicio ver<2>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setServicio(parametros.get(0));
            dato.setDetalle(parametros.get(1));
            dato.setCosto(new BigDecimal(parametros.get(2)));
            dato.setIdProducto(Integer.parseInt(parametros.get(3)));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NTipoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setServicio(parametros.get(1));
            dato.setDetalle(parametros.get(2));
            dato.setCosto(new BigDecimal(parametros.get(3)));
            dato.setIdProducto(Integer.parseInt(parametros.get(4)));
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NTipoServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NTipoServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NTipoServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NTipoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
        @Override
    public String[]  headers() {
        return dato.headers;
    }

}
