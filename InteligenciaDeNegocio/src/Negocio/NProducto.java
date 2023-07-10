/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DProducto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class NProducto implements INegocio{
    private DProducto dato;

    public NProducto() {
        dato= new DProducto();
    }
    
    public static final String[] headers={ 
             "id","idcategoria","codigo","nombre","precio_venta","stock",
        "descripcion","estado","created_at","updated_at"};

    @Override
    public void insertar(List<String> parametros,String email) {
        try {

            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setIdcategoria(Integer.valueOf(parametros.get(1)));
            dato.setCodigo(parametros.get(2));
            dato.setNombre(parametros.get(3));
            dato.setPrecio_venta(Double.valueOf(parametros.get(4)));
            dato.setStock(Integer.valueOf(parametros.get(5)));
            dato.setDescripcion(parametros.get(6));
            dato.setEstado(Boolean.valueOf(parametros.get(7)));
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
            dato.setIdcategoria(Integer.valueOf(parametros.get(1)));
            dato.setCodigo(parametros.get(2));
            dato.setNombre(parametros.get(3));
            dato.setPrecio_venta(Double.valueOf(parametros.get(4)));
            dato.setStock(Integer.valueOf(parametros.get(5)));
            dato.setDescripcion(parametros.get(6));
            dato.setEstado(Boolean.valueOf(parametros.get(7)));
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
