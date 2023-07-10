/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Data.DCategoria;
import Data.DPaciente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class NCategoria implements INegocio{
    private DCategoria dato;

    public NCategoria() {
        dato= new DCategoria();
    }
    

    public static final String[] headers={ 
             "id","nombre","descripcion","estado","created_at","updated_at"};
      /*categoria add<identificador;nombre;descripcion;estado>
    categoria add<1;leche;lacteos;disponible>
    categoria delete<1>
    categoria modify<1;leche;lacteos;agotado>
    categoria list<>
    categoria ver<1>
   */  
    
     @Override
    public void insertar(List<String> parametros,String email) {
        try {

            dato.setId(Integer.valueOf(parametros.get(0)));
            dato.setNombre(parametros.get(1));
            dato.setDescripcion(parametros.get(2));
            dato.setEstado(Boolean.valueOf(parametros.get(3)));
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
            dato.setNombre(parametros.get(1));
            dato.setDescripcion(parametros.get(2));
            dato.setEstado(Boolean.valueOf(parametros.get(3)));
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
