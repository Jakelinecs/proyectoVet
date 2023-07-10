/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import coneccionsocket.ClientPsql;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author HP
 */
public class DUsers {
    
    ClientPsql conn;
    int id;
    String name,email,email_verified_at,password,estilo,fuente,remember_token,created_at,updated_at;
    
        public static final String[] headers = {"id", "name", "email", "email_verified_at", "password", "estilo",
            "fuente", "remember_token", "created_at", "updated_at"};

    
    
    public void insertar() throws SQLException{
        String sql="INSERT INTO users(id,name,email,email_verified_at,password,estilo,fuente,remember_token,created_at,updated_at)"+
                " Values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, email);
        ps.setString(4, email_verified_at);
        ps.setString(5, password);
        ps.setString(6, estilo);
        ps.setString(7, fuente);
        ps.setString(8, remember_token);
        ps.setString(9, created_at);
        ps.setString(10, updated_at);
        
        if(ps.executeUpdate()==0){
            System.err.println("Class DUsers.java dice: "
            +"Ocurrio un error al insertar usuario insertar()");
            throw new SQLException();
        } 
    }
    
    
    public void editar() throws SQLException{
        String sql="UPDATE users SET name=?, email=?, email_verified_at=?, password=?, estilo=? "
                + ",fuente=?, remember_token=?, created_at=?, updated_at=? "+
                " WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, email_verified_at);
        ps.setString(4, password);
        ps.setString(5, estilo);
        ps.setString(6, fuente);
        ps.setString(7, remember_token);
        ps.setString(8, created_at);
        ps.setString(9, updated_at);
        ps.setInt(10, id);
        
        if(ps.executeUpdate()==0){
            System.err.println("Class DUsers.java dice: "
            +"Ocurrio un error al editar usuario editar()");
            throw new SQLException();
        } 
    }
    
    
    
    public void eliminar() throws SQLException{
        String sql="DELETE FROM users WHERE"+
                " id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(0, id);
        
        if(ps.executeUpdate()==0){
            System.err.println("Class DUsers.java dice: "
            +"Ocurrio un error al eliminar usuario eliminar()");
            throw new SQLException();
        } 
    }

    public List<String[]> listar() throws SQLException{
        List<String[]> lista= new ArrayList<>();
        String sql="SELECT * FROM users";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set= ps.executeQuery();
        while (set.next()) {            
            lista.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("name"),
                set.getString("email"),
                set.getString("email_verified_at"),
                set.getString("password"),
                set.getString("estilo"),
                set.getString("fuente"),
                set.getString("remember_token"),
                set.getString("created_at"),
                set.getString("updated_at"),
            });
        }
        return lista;
    }

    
    
    
    public String[] ver() throws SQLException{
        String[] usuario=null;
        String sql="SELECT * FROM users WHERE id=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            usuario=new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("name"),
                set.getString("email"),
                set.getString("email_verified_at"),
                set.getString("password"),
                set.getString("estilo"),
                set.getString("fuente"),
                set.getString("remember_token"),
                set.getString("created_at"),
                set.getString("updated_at"),
            };

        }else{
            System.err.println("Class DUsers.java dice: "
            +"Ocurrio un error al ver usuario ver()");
            throw new SQLException();
        } 
        return usuario;
    }
    
    public String[] verCorreo() throws SQLException{
        String[] usuario=null;
        String sql="SELECT * FROM users WHERE email=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            usuario=new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("name"),
                set.getString("email"),
                set.getString("email_verified_at"),
                set.getString("password"),
                set.getString("estilo"),
                set.getString("fuente"),
                set.getString("remember_token"),
                set.getString("created_at"),
                set.getString("updated_at"),
            };

        }else{
            System.err.println("Class DUsers.java dice: "
            +"Ocurrio un error al ver usuario ver()");
            throw new SQLException();
        } 
        return usuario;
    }
    
    
    public int getIdByEmail(String email) throws SQLException{
        int i=-1;
        String sql="SELECT * FROM users WHERE email=?";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ps.setString(1, email);
        ResultSet set= ps.executeQuery();

        if(set.next()){
            i= set.getInt("id");

        }else{
            System.err.println("Class DUsers.java dice: "
            +"Ocurrio un error al buscar el id usuario getIdByEmail()");
            throw new SQLException();
        } 
        return i;
    }
    
    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public void setPassword(String password) {
        this.password = password;
        
        
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
            
            this.password = hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void desconectar() {
        if (conn != null) {
            conn.closeConection();
        }
    }
    
}
