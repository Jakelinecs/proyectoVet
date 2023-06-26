/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DVacunaAnt;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class NVacunaAnt implements INegocio {
    private DVacunaAnt dato;
    
    public NVacunaAnt(){
        dato = new DVacunaAnt();
    }

    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setFecha(parametros.get(2));
            dato.setFechaP(parametros.get(3));
            dato.setDosis(Integer.valueOf(parametros.get(4)));
            dato.setCantidad(Integer.valueOf(parametros.get(5)));
            dato.setCodigo(Integer.valueOf(parametros.get(6)));
           
            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editar(List<String> parametros, String email) {
        try{
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setFecha(parametros.get(2));
            dato.setFechaP(parametros.get(3));
            dato.setDosis(Integer.valueOf(parametros.get(4)));
            dato.setCantidad(Integer.valueOf(parametros.get(5)));
            dato.setCodigo(Integer.valueOf(parametros.get(6)));
           
            dato.editar();
            dato.desconectar();
            } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(email);
            dato.eliminar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<String[]> listar(String email) {
        List<String[]> lista = new ArrayList<>();
        dato.setNombre(email);
        try {
            lista = dato.listar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public String[] ver(List<String> parametros, String email) {
        String[] d = null;
        try {
            dato.setNombre(email);
            dato.setId(Integer.valueOf(parametros.get(0)));
            
            d = dato.ver();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
}
