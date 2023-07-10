/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DPaciente;
import Data.DPersona;
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
public class NPaciente implements INegocio{
    private DPaciente dato;

    public NPaciente() {
        dato= new DPaciente();
    }
    

    @Override
    public void insertar(List<String> parametros,String email) {
        try {

            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setEspecie(parametros.get(2));
            dato.setRaza(parametros.get(3));
            dato.setSexo(parametros.get(4));
            dato.setColor(parametros.get(5));
            dato.setF_nacimiento(parametros.get(6));
            dato.setPropietario(Integer.valueOf(parametros.get(7)));
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
    public void editar(List<String> parametros,String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setEspecie(parametros.get(2));
            dato.setRaza(parametros.get(3));
            dato.setSexo(parametros.get(4));
            dato.setColor(parametros.get(5));
            dato.setF_nacimiento(parametros.get(6));
            dato.setPropietario(Integer.valueOf(parametros.get(7)));
            dato.setPerfil(parametros.get(8));
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
    public void eliminar(List<String> parametros,String email) {
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
    public String[] ver(List<String> parametros,String email) {
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
