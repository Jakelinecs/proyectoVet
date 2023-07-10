/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */
import Data.DDetalleServicio;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NDetalleServicio implements INegocio {
    private DDetalleServicio dato;

    public NDetalleServicio() {
        dato = new DDetalleServicio();
    }

    public static final String[] headers = {"id", "idservicio", "tipo_servicio", "nro_servicio", "costo", "created_at", "updated_at"};
       /*detalleServicio add<identificadorServicio;tipoServicio;numeroServico;costo>
    detalleServicio add<1;peluqueria;4;85>
    detalleServicio delete<1>
    detalleServicio modify<1;desparasitacion;4;105>
    detalleServicio list<>
    detalleServicio ver<1>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setIdservicio(Integer.parseInt(parametros.get(0)));
            dato.setTipo_servicio(parametros.get(1));
            dato.setNro_servicio(Integer.parseInt(parametros.get(2)));
            dato.setCosto(new BigDecimal(parametros.get(3)));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setIdservicio(Integer.parseInt(parametros.get(1)));
            dato.setTipo_servicio(parametros.get(2));
            dato.setNro_servicio(Integer.parseInt(parametros.get(3)));
            dato.setCosto(new BigDecimal(parametros.get(4)));
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
        @Override
    public String[]  headers() {
        return dato.headers;
    }

}
