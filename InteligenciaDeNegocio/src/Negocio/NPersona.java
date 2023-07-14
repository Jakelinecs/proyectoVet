package Negocio;

import Data.DPersona;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *
 * @author HP
 */
public class NPersona implements INegocio{
    private DPersona dato;

    public NPersona() {
        dato= new DPersona();
    }
    
    //persona add<identificador;nombre;apellidopaterno y materno;sexo;fecha de nacimiento ;celunar;direccion>
    //persona add<5;daniel;taquichiri soraide;Hombre;21-08-12;78654123;ichilo santa fe>
    
    //persona add<identificador;nombre;apellidopaterno y materno;sexo;fecha de nacimiento ;celunar;direccion>
    
    //persona delete<identificador>
    //persona delete<5>
    //persona list<>
    //persona ver<5>
    
    @Override
    public void insertar(List<String> parametros,String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setApp_apm(parametros.get(2));
            dato.setSexo(parametros.get(3));
            dato.setF_nacimiento(parametros.get(4));
            dato.setCelular(parametros.get(5));
            dato.setDireccion(parametros.get(6));
            dato.setCreated_at();
            dato.setUpdated_at();
            dato.setCorreo(email);
            
            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //persona modify<5;daniel;taquichiri soraide;Hombre;21-08-12;78654123;ichilo santa fe>
    @Override
    public void editar(List<String> parametros,String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setApp_apm(parametros.get(2));
            dato.setSexo(parametros.get(3));
            dato.setF_nacimiento(parametros.get(4));
            dato.setCelular(parametros.get(5));
            dato.setDireccion(parametros.get(6));
            dato.setUpdated_at();
            dato.setCorreo(email);
            
            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
        @Override
    public String[]  headers() {
        return dato.headers;
    }

}
