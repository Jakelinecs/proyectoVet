/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio;

import java.util.List;

/**
 *
 * @author HP
 */
public interface INegocio {
    
public void insertar(List<String> parametros,String email);
public void editar(List<String> parametros,String email);
public void eliminar(List<String> parametros,String email);
public List<String[]> listar(String email);
public String[] ver(List<String> parametros,String email);

public String[]  headers();
}
