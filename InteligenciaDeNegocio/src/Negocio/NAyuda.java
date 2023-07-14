
package Negocio;

import Data.DAyuda;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class NAyuda {
    private DAyuda dato;

    public NAyuda() {
        dato= new DAyuda();
    }
    

    public static final String[] headers={ 
             "id","comando","operacion","ejemplo"};

    public void insertar(List<String> parametros) {
        try {

            dato.setComando(parametros.get(0));
            dato.setOperacion(parametros.get(1));
            dato.setEjemplo(parametros.get(2));
            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String[]> listar(List<String> parametros) {
       List<String[]> lista = new ArrayList<>();
        try {
            lista = dato.listar(parametros.get(0));
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    public List<String[]> listar() {
       List<String[]> lista = new ArrayList<>();
        try {
            lista = dato.listar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public String[] ver(List<String> parametros) {
        String[] d = null;
        try {
            dato.setOperacion(parametros.get(0));
            
            d = dato.ver();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
    public String[]  headers() {
        return dato.headers;
    }

}
