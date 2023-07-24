/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author HP
 */

import Data.DUsers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NUsers implements INegocio {
    private DUsers dato;

    public NUsers() {
        dato = new DUsers();
    }
    /*Users add<identificador,nombre,email,contraseña,estilo>
    Users add<1,Marina,marinamejj123@gmail.com,12345678,normal>
    Users list<>
    Users ver<2>
   */  
    @Override
    public void insertar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setName(parametros.get(1));
            dato.setEmail(parametros.get(2));
            dato.setEmail_verified_at("");
            dato.setPassword(parametros.get(3));
            dato.setEstilo(parametros.get(4));
            dato.setFuente("img/perfil/logo_v1.png");
            dato.setRemember_token("");
            dato.setCreated_at();
            dato.setUpdated_at();

            dato.insertar();
            dato.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void editar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));
            dato.setName(parametros.get(1));
            dato.setEmail(parametros.get(2));
            dato.setEmail_verified_at(null);
            dato.setPassword(parametros.get(3));
            dato.setEstilo(parametros.get(4));
            dato.setFuente("img/perfil/logo_v1.png");
            dato.setRemember_token(null);
            dato.setUpdated_at();

            dato.editar();
            dato.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void eliminar(List<String> parametros, String email) {
        try {
            dato.setId(Integer.parseInt(parametros.get(0)));

            dato.eliminar();
            dato.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<String[]> listar(String email) {
        List<String[]> lista = new ArrayList<>();
        try {
            lista = dato.listar();
            dato.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    

    public int getIdByEmail(String email) {
        int id = -1;
        try {
            id = dato.getIdByEmail(email);
            dato.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public String hashPassword(String password) {
        try {
            // Obtener la instancia del algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Calcular el hash MD5 de la cadena de texto
            byte[] hashBytes = md.digest(password.getBytes());

            // Convertir el hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xFF & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Imprimir el hash MD5 en formato hexadecimal
            System.out.println(hexString.toString());

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] ver(List<String> parametros, String email) {
        String[] d = null;
        try {
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
