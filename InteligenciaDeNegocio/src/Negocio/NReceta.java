

package Negocio;

import Data.DReceta;
import com.sun.istack.internal.logging.Logger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Marina
 */
public class NReceta implements INegocio{
     private DReceta dato;
     
     public NReceta(){
         dato = new DReceta();
     }
     /*
pr.setInt(1, id);
    pr.setInt(2, idpaciente);
    pr.setInt(3, idmedico);
    pr.setInt(4, idservicio);
    pr.setInt(5, idDetalleReceta);
    pr.setString(6, paciente);
    pr.setInt(7, edad);
    pr.setDate(8,getDate(fecha));
    pr.setString(9, diagnostico);
    pr.setString(10, indicaciones);*/
    @Override
    public void insertar(List<String> parametros, String email) {
  
          
            try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setIdpaciente(Integer.valueOf(parametros.get(1)));
            dato.setIdmedico(Integer.valueOf(parametros.get(2)));
            dato.setIdservicio(Integer.valueOf(parametros.get(3)));
            dato.setIdDetalleReceta(Integer.valueOf(parametros.get(4)));
            
            dato.setPaciente(parametros.get(5));
            dato.setEdad(Integer.valueOf(parametros.get(6)));
            dato.setFecha(parametros.get(7));
            dato.setIndicaciones(parametros.get(9));
                //solo esto cambia en todos?
                dato.insertar();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
             java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);
         }
            
    
        
    }

    @Override
    public void editar(List<String> parametros, String email) {
    try{
        
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setIdpaciente(Integer.valueOf(parametros.get(1)));
            dato.setIdmedico(Integer.valueOf(parametros.get(2)));
            dato.setIdservicio(Integer.valueOf(parametros.get(3)));
            dato.setIdDetalleReceta(Integer.valueOf(parametros.get(4)));
            
            dato.setPaciente(parametros.get(5));
            dato.setEdad(Integer.valueOf(parametros.get(6)));
            dato.setFecha(parametros.get(7));
            dato.setIndicaciones(parametros.get(9));
            dato.editar();
    }catch(SQLException ex){
     java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);

    }
    }

    @Override
    public void eliminar(List<String> parametros, String email) {
        try{
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setCorreo(email);
            dato.eliminar();
            
        }catch(SQLException ex){
        java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @Override
    public List<String[]> listar(String email) {
        List<String[]> lista = new ArrayList<>();
        dato.setCorreo(email);
        try{
            lista = dato.listar();
            
        }catch(SQLException ex){
         java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);

        }
        return lista;
        
    }

    @Override
    public String[] ver(List<String> parametros, String email) {
        String[]d = null;
        try{
            dato.setCorreo(email);
            dato.setId(Integer.valueOf(parametros.get(0)));
            d= dato.ver();
            
        } catch (SQLException ex) {
             java.util.logging.Logger.getLogger(NReceta.class.getName()).log(Level.SEVERE, null, ex);
         }

        return d;
    }
    
    
}
