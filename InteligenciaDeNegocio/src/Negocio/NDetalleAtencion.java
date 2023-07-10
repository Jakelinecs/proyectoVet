package Negocio;

/**
 *
 * @author HP
 */
import Data.DDetalleAtencion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NDetalleAtencion implements INegocio {
    private DDetalleAtencion dato;

    public NDetalleAtencion() {
        dato = new DDetalleAtencion();
    }

    public static final String[] headers = {"id", "idservicio", "tipo_servicio", "nro_servicio", "costo", "created_at", "updated_at"};
      /*detalleAtencion add<identificadorAtencion,detalleProcedimiento,costo>
    detalleAtencion add<1;4;colocar una vez al dia,40>
    detalleAtencion delete<1>
    detalleAtencion modify<1;4;colocar una vez al dia,80>
    detalleAtencion list<>
    detalleAtencion ver<1>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setIdAtencion(Integer.parseInt(parametros.get(0)));
            dato.setDetalleProcedimiento(parametros.get(1));
            dato.setCosto(Double.valueOf(parametros.get(2)));
            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setIdAtencion(Integer.parseInt(parametros.get(1)));
            dato.setDetalleProcedimiento(parametros.get(2));
            dato.setCosto(Double.valueOf(parametros.get(3)));
            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
        @Override
    public String[]  headers() {
        return dato.headers;
    }

}
