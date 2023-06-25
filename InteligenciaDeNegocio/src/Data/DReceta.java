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
       /*paciente
    edad
    fecha
    diagnostico: tipo de enfermedad
    
    indicaciones: descripcion*/ 
    //colocar el id de la atencionClinica
ClientPsql coneccion;
DUsers us;//campo de usuario, reconocedor de correo
int id;
int idpaciente;
int idmedico;
int idservicio;
int idDetalleReceta;
String paciente;
int edad;
String fecha;
String indicaciones;

String correo;

//esto define una lista pero para que?
public static final String[]headers=
{
  "id","idpaciente","idmedico","idservicio","idDetalleReceta","paciente","edad","fecha","diagnostico","indicaciones"  
};

public DReceta(){
    coneccion= new ClientPsql();
}

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public void setIdmedico(int idmedico) {
        this.idmedico = idmedico;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public void setIdDetalleReceta(int idDetalleReceta) {
        this.idDetalleReceta = idDetalleReceta;
    }
public void setId(int id){
    this.id=id;
}
public void setPaciente(String paciente){
    this.paciente=paciente;
}
public void setEdad(int edad){
    this.edad=edad;
}
public void setFecha(String fecha){
    this.fecha=fecha;
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
        String sql="insert into recetas(id,idpaciente,idmedico,idservicio,idDetalleReceta,paciente,edad, fecha, diagnostico,indicaciones)" + " Values(?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setInt(2, idpaciente);
    pr.setInt(3, idmedico);
    pr.setInt(4, idservicio);
    pr.setInt(5, idDetalleReceta);
    pr.setString(6, paciente);
    pr.setInt(7, edad);
    pr.setDate(8,getDate(fecha));
    pr.setString(10, indicaciones);

    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
    
}
public void editar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql="update recetas set paciente=?"
                + "edad=?, fecha=?, diagnostico=?"
                + "where id=?";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setInt(2, idpaciente);
    pr.setInt(3, idmedico);
    pr.setInt(4, idservicio);
    pr.setInt(5, idDetalleReceta);
    pr.setString(6, paciente);
    pr.setInt(7, edad);
    pr.setDate(8,getDate(fecha));
    
    pr.setString(10, indicaciones);
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
        String sql="select *from reservas";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set= ps.executeQuery();
        while(set.next()){
        lista.add(new String[]{
            String.valueOf(set.getInt("id")),
            String.valueOf(set.getInt("idpaciente")),
            String.valueOf(set.getInt("idmedico")),
            String.valueOf(set.getInt("idservicio")),
            String.valueOf(set.getInt("idDetalleReceta")),
            set.getString("paciente"),
            set.getString("edad"),
            set.getString("fecha"),
            set.getString("diagnostico"),
            set.getString("indicaciones")
            
        });
            
        }
    }
return lista;    
}


/*   pr.setInt(1, id);
    pr.setInt(2, idpaciente);
    pr.setInt(3, idmedico);
    pr.setInt(4, idservicio);
    pr.setInt(5, idDetalleReceta);
    pr.setString(6, paciente);
    pr.setInt(7, edad);
    pr.setDate(8,getDate(fecha));
    pr.setString(9, diagnostico);
    pr.setString(10, indicaciones);*/
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
                String.valueOf(set.getInt("idpaciente")),
                String.valueOf(set.getInt("idmedico")),
                String.valueOf(set.getInt("idservicio")),
                String.valueOf(set.getInt("idDetalleReceta")),
                set.getString("paciente"),
                set.getString("edad"),
                set.getString("fecha"),
                set.getString("diagnostico"),
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


