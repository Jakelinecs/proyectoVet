/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DContrato;
import java.sql.Date;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author HP
 */
public class NContrato implements INegocio {
    private DContrato dato;

    public NContrato() {
        dato = new DContrato();
    }

    public static final String[] headers = {
            "id", "detalle", "f_ini", "f_fin", "idpersonal", "iduser", "estado", "created_at", "updated_at"
    };
    /*contrato add<identificador;detalle;fechaIncio;fechaFin;identificadorPersonal;usuario;estado>
    contrato add<1;contrato fijo;2019-06-8;2020-08-12;4;Marina; pendiente>
    contrato delete<1>
    contrato modify<1;contrato Indefinido;2019-06-8;2020-08-12;4;Marina; pendiente>
    contrato list<>
    contrato ver<1>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setDetalle(parametros.get(1));
            dato.setF_ini(Date.valueOf(parametros.get(2)));
            dato.setF_fin(Date.valueOf(parametros.get(3)));
            dato.setIdpersonal(Integer.valueOf(parametros.get(4)));
            dato.setIduser(Integer.valueOf(parametros.get(5)));
            dato.setEstado(Boolean.valueOf(parametros.get(6)));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);
            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setDetalle(parametros.get(1));
            dato.setF_ini(Date.valueOf(parametros.get(2)));
            dato.setF_fin(Date.valueOf(parametros.get(3)));
            dato.setIdpersonal(Integer.valueOf(parametros.get(4)));
            dato.setIduser(Integer.valueOf(parametros.get(5)));
            dato.setEstado(Boolean.valueOf(parametros.get(6)));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);
            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setCorreo(email);
            dato.eliminar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public String[] ver(List<String> parametros, String email) {
        String[] d = null;
        try {
            dato.setCorreo(email);
            dato.setId(Integer.valueOf(parametros.get(0)));
            d = dato.ver();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
        @Override
    public String[]  headers() {
        return dato.headers;
    }

}
