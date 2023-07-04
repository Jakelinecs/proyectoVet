/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Data.DDetalleReceta;
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
public class NDetalleReceta {
    //nombreproducto,indicaciones -> tabladetalleReceta
        private DDetalleReceta dato;

    public NDetalleReceta() {
        dato = new DDetalleReceta();
    }
     
    public void insertar(List<String> parametros, String email) {
  
          
            try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombreProducto(parametros.get(1));
            dato.setIndicaciones(parametros.get(2));
                dato.insertar();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(NDetalleReceta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
             java.util.logging.Logger.getLogger(NDetalleReceta.class.getName()).log(Level.SEVERE, null, ex);
         }
            
    
        
    }

    public void editar(List<String> parametros, String email) {
    try{
        
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombreProducto(parametros.get(1));
            dato.setIndicaciones(parametros.get(2));
            dato.editar();
    }catch(SQLException ex){
     java.util.logging.Logger.getLogger(NDetalleReceta.class.getName()).log(Level.SEVERE, null, ex);

    }
    }

    public void eliminar(List<String> parametros, String email) {
        try{
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setCorreo(email);
            dato.eliminar();
            
        }catch(SQLException ex){
        java.util.logging.Logger.getLogger(NDetalleReceta.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public List<String[]> listar(String email) {
        List<String[]> lista = new ArrayList<>();
        dato.setCorreo(email);
        try{
            lista = dato.listar();
            
        }catch(SQLException ex){
         java.util.logging.Logger.getLogger(NDetalleReceta.class.getName()).log(Level.SEVERE, null, ex);

        }
        return lista;
        
    }

 
    public String[] ver(List<String> parametros, String email) {
        String[]d = null;
        try{
            dato.setCorreo(email);
            dato.setId(Integer.valueOf(parametros.get(0)));
            d= dato.ver();
            
        } catch (SQLException ex) {
             java.util.logging.Logger.getLogger(NDetalleReceta.class.getName()).log(Level.SEVERE, null, ex);
         }

        return d;
    }
}

