/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */
import Data.DActivo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NActivo implements INegocio {
    private DActivo dato;

    public NActivo() {
        dato = new DActivo();
    }

    public static final String[] headers = {"id", "nombre", "detalle", "f_adquisicion", "f_mantenimiento", "estado", "created_at", "updated_at"};

    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setNombre(parametros.get(0));
            dato.setDetalle(parametros.get(1));
            dato.setF_adquisicion(parametros.get(2));
            dato.setF_mantenimiento(parametros.get(3));
            dato.setEstado(Boolean.parseBoolean(parametros.get(4)));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NActivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setDetalle(parametros.get(2));
            dato.setF_adquisicion(parametros.get(3));
            dato.setF_mantenimiento(parametros.get(4));
            dato.setEstado(Boolean.parseBoolean(parametros.get(5)));
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NActivo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NActivo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NActivo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NActivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
}
