/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Utlis.DateString;
import coneccionsocket.ClientPsql;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Marina
 */
public class DReceta {

ClientPsql coneccion;
DUsers us;//campo de usuario, reconocedor de correo
int id;
int numeroRecetario;
String estado;
String correo;

//esto define una lista pero para que?
public static final String[]headers=
{
  "id","numeroRecetario","estado"  
};

public DReceta(){
    coneccion= new ClientPsql();
}

    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroRecetario(int numeroRecetario) {
        this.numeroRecetario = numeroRecetario;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setCorreo(String correo) {
              this.correo = correo;
    }


public void insertar() throws SQLException, ParseException{

    int dato;
    dato= us.getIdByEmail(correo);//compara si existe
    if (dato!=-1){
        String sql="insert into recetas(id,numeroRecetario,estado,)" + " Values(?,?,?)";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setInt(2, numeroRecetario);
    pr.setString(3, estado);
 

    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
    
}
public void editar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql="update recetas set numeroRecetario=?"
                + " estado=?"
                + "where id=?";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setInt(2, numeroRecetario);
    pr.setString(6, estado);
    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
}
public void eliminar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql="delete from recetas where"+ "id=?";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
 
  
    
    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
}
   
public List<String[]>listar() throws SQLException{
    List<String[]>lista = new ArrayList<>();
    int dato;
    dato= us.getIdByEmail(correo);
    if(dato!=-1){
        String sql="select *from recetas";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set= ps.executeQuery();
        while(set.next()){
        lista.add(new String[]{
            String.valueOf(set.getInt("id")),
            String.valueOf(set.getInt("numeroRecetario")),
            set.getString("estado"),
   
            
        });
            
        }
    }
return lista;    
}



public String[] ver() throws SQLException{
    String[] usuario=null;
    int dato;
    dato = us.getIdByEmail(correo);
    if(dato!=-1){
        String sql="select * from recetas WHERE id=?";
        PreparedStatement ps= new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(0,id);
        ResultSet set= ps.executeQuery();
        if(set.next()){
            usuario= new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getInt("numeroRecetario")),
                set.getString("estado"),
                
                
                
                
            };
        }else{
             System.err.println("Class DReceta.java dice: " 
                +"Ocurrio un error al ver usuario ver()"); 

        }
            
        }
    return usuario;
    }

  

  
}
