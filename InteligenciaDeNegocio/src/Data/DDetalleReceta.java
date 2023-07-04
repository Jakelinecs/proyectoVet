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
public class DDetalleReceta {
      //nombreproducto,indicaciones -> tabladetalleReceta
    
    ClientPsql coneccion;
DUsers us;//campo de usuario, reconocedor de correo

int id;

String nombreProducto;
String indicaciones;

String correo;

//esto define una lista pero para que?
public static final String[]headers=
{
  "id","nombreProducto","indicaciones"  
};

    public DDetalleReceta() {
          coneccion= new ClientPsql();
    }
    
   
    
      public void setId(int id) {
        this.id = id;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
  
public void setIndicaciones(String indicaciones){
    this.indicaciones=indicaciones;
}

public void setCorreo(String correo){
    this.correo=correo;
}

public void insertar() throws SQLException, ParseException{

    int dato;
    dato= us.getIdByEmail(correo);//compara si existe
    if (dato!=-1){
        String sql="insert into detalleReceta(id,nombreProducto,indicaciones)" + " Values(?,?,?)";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setString(2, nombreProducto);
    pr.setString(3, indicaciones);

    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
    
}
public void editar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql="update detalleReceta set nombreProducto=?"
                + "indicaciones=?"
                + "where id=?";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setString(2, nombreProducto);
    pr.setString(3, indicaciones);
    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
}
public void eliminar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql="delete from detalleReceta where"+ "id=?";
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
        String sql="select *from detalleReceta";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set= ps.executeQuery();
        while(set.next()){
        lista.add(new String[]{
            String.valueOf(set.getInt("id")),
            set.getString("nombreProducto"),
            set.getString("indicaciones")
            
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
        String sql="select * from detalleReceta WHERE id=?";
        PreparedStatement ps= new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(0,id);
        ResultSet set= ps.executeQuery();
        if(set.next()){
            usuario= new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombreProducto"),
                set.getString("indicaciones"),
                
                
                
            };
        }else{
             System.err.println("Class DPersona.java dice: " 
                +"Ocurrio un error al ver usuario ver()"); 

        }
            
        }
    return usuario;
    }

      public Date getDate(String date){
    Calendar c = DateString.StringToDate(date);
    long x = c.getTimeInMillis();
      System.out.println(x);
      Date dateSQL =new Date(x);
        System.out.println(dateSQL.toString());
    return dateSQL;
    }

  

    


}
