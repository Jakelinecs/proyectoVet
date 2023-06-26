
package Negocio;

import Data.DAtencionClinica;
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
public class NAtencionClinica implements INegocio{
    private DAtencionClinica dato;

    public NAtencionClinica() {
        dato = new DAtencionClinica();
    }
    /*
    id
    idcliente
    idpaciente
    fecha
    hora
    direccion
    telefono
    motivo
    */

    @Override
    public void insertar(List<String> parametros, String email) {
           try { 
 
            dato.setId(Integer.valueOf(parametros.get(0))); 
            dato.setIdcliente(Integer.valueOf(parametros.get(1))); 
            dato.setIdpaciente(Integer.valueOf(parametros.get(2)));     
            dato.setFecha(parametros.get(3));
            dato.setHora(parametros.get(4));
   
            dato.setMotivo(parametros.get(7));
            
            dato.insertar(); 
      
        } catch (SQLException ex) { 
            Logger.getLogger(NAtencionClinica.class.getName()).log(Level.SEVERE, null, ex); 
        } catch (ParseException ex) {
            Logger.getLogger(NAtencionClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
      try { 
            dato.setId(Integer.valueOf(parametros.get(0))); 
            dato.setIdcliente(Integer.valueOf(parametros.get(1))); 
            dato.setIdpaciente(Integer.valueOf(parametros.get(2)));     
            dato.setFecha(parametros.get(3));
            dato.setHora(parametros.get(4));
            dato.setMotivo(parametros.get(7));
             
            dato.editar(); 
         
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
