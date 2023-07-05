package Negocio;

import Data.DPago;
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
public class NPago implements INegocio {

    private DPago dato;

    public NPago() {
        dato = new DPago();
    }

    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            // "id","correo","numeroReferencia","monto","fecha","nombre","metodoPago","descricpion","estadoPago"};

            dato.setIdservicio(Integer.valueOf(parametros.get(0)));
            dato.setNumero_referencia(parametros.get(1));
            dato.setNombre(parametros.get(2));
            dato.setMonto(Double.valueOf(parametros.get(3)));
            dato.setFecha(parametros.get(4));
            dato.setMetodo_pago(parametros.get(5));
            dato.setDescripcion(parametros.get(6));
            dato.setEstado_pago(parametros.get(7));

            dato.setCorreo(email);

            dato.insertar();
            dato.desconectar();

        } catch (SQLException ex) {
            Logger.getLogger(NPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNumero_referencia(parametros.get(1));
            dato.setMonto(Double.valueOf(parametros.get(2)));
            dato.setFecha(parametros.get(3));
            dato.setMetodo_pago(parametros.get(4));
            dato.setDescripcion(parametros.get(5));
            dato.setEstado_pago(parametros.get(6));

            dato.setCorreo(email);

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(NPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
