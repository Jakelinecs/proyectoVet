/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */
import Data.DServicio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NServicio implements INegocio {
    private DServicio dato;

    public NServicio() {
        dato = new DServicio();
    }

    public static final String[] headers = {"id", "responsable", "idpaciente", "idmedico", "fecha", "total", "created_at", "updated_at"};

    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setResponsable(parametros.get(0));
            dato.setIdpaciente(Integer.parseInt(parametros.get(1)));
            dato.setIdmedico(Integer.parseInt(parametros.get(2)));
            dato.setFecha(parametros.get(3));
            dato.setTotal(Double.parseDouble(parametros.get(4)));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setResponsable(parametros.get(1));
            dato.setIdpaciente(Integer.parseInt(parametros.get(2)));
            dato.setIdmedico(Integer.parseInt(parametros.get(3)));
            dato.setFecha(parametros.get(4));
            dato.setTotal(Double.parseDouble(parametros.get(5)));
            dato.setUpdated_at();
            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
}
