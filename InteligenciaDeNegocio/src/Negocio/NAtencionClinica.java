/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */
import Data.DAtencionClinica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NAtencionClinica implements INegocio {
    private DAtencionClinica dato;

    public NAtencionClinica() {
        dato = new DAtencionClinica();
    }

    public static final String[] headers = {"id", "idservicio", "tipo_servicio", "nro_servicio", "costo", "created_at", "updated_at"};
     /*atencionClinica add<identificadorDetalleServicio;motivo;hora>
    atencionClinica add<1;4;fiebre;10:00>
    atencionClinica delete<1>
    atencionClinica modify<1;4;rabia;10:00>
    atencionClinica list<>
    atencionClinica ver<1>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setIddetalleServicio(Integer.parseInt(parametros.get(0)));
            dato.setMotivo(parametros.get(1));
            dato.setHr(parametros.get(2));
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
            dato.setIddetalleServicio(Integer.parseInt(parametros.get(1)));
            dato.setMotivo(parametros.get(2));
            dato.setHr(parametros.get(3));
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
