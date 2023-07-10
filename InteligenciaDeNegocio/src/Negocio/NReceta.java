/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Data.DReceta;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Marina
 */
public class NReceta implements INegocio {

    private DReceta dato;

    public NReceta() {
        dato = new DReceta();
    }
       /*
    receta add<identicadorAtencion;numeroRecetario;estado>
    receta add<1;45;disponible>
    receta delete<2>
    receta modify<2;48,disponible>
    receta list<>
    receta ver<2>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {

        try {
            dato.setIdatencion(Integer.valueOf(parametros.get(0)));
            dato.setNumeroRecetario(Integer.valueOf(parametros.get(1)));
            dato.setEstado(parametros.get(2));

            dato.setCorreo(email);

            dato.insertar();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {

            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNumeroRecetario(Integer.valueOf(parametros.get(1)));
            dato.setEstado(parametros.get(2));
            dato.editar();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public void eliminar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setCorreo(email);
            dato.eliminar();

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @Override
    public List<String[]> listar(String email) {
        List<String[]> lista = new ArrayList<>();
        dato.setCorreo(email);
        try {
            lista = dato.listar();

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);

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

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);
        }

        return d;
    }
    @Override
    public String[]  headers() {
        return dato.headers;
    }

}
