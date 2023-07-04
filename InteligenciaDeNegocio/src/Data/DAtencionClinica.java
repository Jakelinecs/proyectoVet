

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
public class DAtencionClinica {
 

ClientPsql coneccion;
DUsers us;//campo de usuario, reconocedor de correo
int id,idcliente,idpaciente;

String  fecha,hora,motivo;
String correo;
public static final String[]headers=
{
  "id","idcliente","idpaciente","fecha","hora","motivo", 
};

    public DAtencionClinica() {
        coneccion= new ClientPsql();
    }

  

    public void setId(int id) {
        this.id = id;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }



    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    

public void insertar() throws SQLException, ParseException{

    int dato;
    dato= us.getIdByEmail(correo);//compara si existe
    if (dato!=-1){
        String sql="insert into atencionClinica(id,idcliente,idpaciente,fecha,hora,motivo)" + " Values(?,?,?,?,?,?)";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setInt(2, idcliente);
    pr.setInt(3, idpaciente);
    pr.setDate(4,getDate(fecha));
    pr.setString(5, hora);
    pr.setString(8, motivo);

    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
    
}
public void editar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql=" hora=?, motivo=?"
                + "update atencionCliente set fecha=?,"
                + "where id=?";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
    pr.setInt(2, idcliente);
    pr.setInt(3, idpaciente);
    pr.setDate(4,getDate(fecha));
    pr.setString(5, hora);
    pr.setString(8, motivo);
    
    
    
    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
}
public void eliminar() throws SQLException{
    int dato;
    dato = us.getIdByEmail(correo);
    if (dato!=-1){
        String sql="delete from atencionClinica where"+ "id=?";
    PreparedStatement pr= new ClientPsql().conectar().prepareStatement(sql);
    pr.setInt(1, id);
 
  
    
    if(pr.executeUpdate()==0){
        System.out.println("Ocurrio un error");
    }
    
    }
}
       /*
    int id,idcliente,idpaciente;
String  fecha,hora, direccion,telefono,motivo;*/
public List<String[]>listar() throws SQLException{
    List<String[]>lista = new ArrayList<>();
    int dato;
    dato= us.getIdByEmail(correo);
    if(dato!=-1){
        String sql="select *from atencionClinica";
        PreparedStatement ps = new ClientPsql().conectar().prepareStatement(sql);
        ResultSet set= ps.executeQuery();
        while(set.next()){
        lista.add(new String[]{
            String.valueOf(set.getInt("id")),
            String.valueOf(set.getInt("idcliente")),
            String.valueOf(set.getInt("idpaciente")),
             set.getString("fecha"),
            set.getString("hora"),
            set.getString("motivo")
            
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
        String sql="select * from atencionClinica WHERE id=?";
        PreparedStatement ps= new ClientPsql().conectar().prepareStatement(sql);
        ps.setInt(0,id);
        ResultSet set= ps.executeQuery();
        if(set.next()){
            usuario= new String[]{
             String.valueOf(set.getInt("id")),
            String.valueOf(set.getInt("idcliente")),
            String.valueOf(set.getInt("idpaciente")),
             set.getString("fecha"),
            set.getString("hora"),
            set.getString("motivo")
                
                
                
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

    public void setCorreo(String correo) {
            this.correo = correo;
    }

    public void desconectar() {
             if ( coneccion != null) {
             coneccion.closeConection();
        }
    }

 


    

}
