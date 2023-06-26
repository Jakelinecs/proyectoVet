
package Negocio;

import Data.DDetalleAtencion;
import Data.DPaciente;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marina
 */
public class NDetalleAtencion implements INegocio{
    private DDetalleAtencion dato;

    public NDetalleAtencion() {
    dato= new DDetalleAtencion();
    }

    @Override
    public void insertar(List<String> parametros, String email) {
      try { 
 
            dato.setId(Integer.valueOf(parametros.get(0))); 
            dato.setIdAtencionClinica(Integer.valueOf(parametros.get(1))); 
            dato.setIdReceta(Integer.valueOf(parametros.get(2))); 
            dato.insertar(); 
            dato.desconectar(); 
        } catch (SQLException ex) { 
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex); 
        } catch (ParseException ex) {
            Logger.getLogger(NDetalleAtencion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
          try { 
            dato.setId(Integer.valueOf(parametros.get(0))); 
            dato.setId(Integer.valueOf(parametros.get(1))); 
            dato.setId(Integer.valueOf(parametros.get(2))); 
 
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
    
    
}
//nombreproducto,indicaciones -> tabladetalleReceta