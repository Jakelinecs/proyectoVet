package inteligenciadenegocio;

import Negocio.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author HP
 */
public class InteligenciaDeNegocio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String email = "jakeli1997.jcs@gmail.com";
        /*
        NPersona negocio = new NPersona();
        
        List<String> persona= new ArrayList<>();
        persona.add("3");
        persona.add("Angelica");
        persona.add("Condori Soraide");
        persona.add("Mujer");
        persona.add("1995-02-05");
        persona.add("75069277");
        persona.add("B/ la fortaleza");
        negocio.insertar(persona,email);
         */
 /*    
        NPaciente negocioP = new NPaciente();
        
        List<String> paciente= new ArrayList<>();
        
        
   
        
        paciente.add("4");
        paciente.add("kaiser");
        paciente.add("canino");
        paciente.add("mestizo");
        paciente.add("macho");
        paciente.add("megro");
        paciente.add("2018-09-14");
        paciente.add("1");
        negocioP.insertar(paciente,email);
         */
//        NProducto negocio = new NProducto();
        //   NUsers negocio = new NUsers();
        NUsers negocio = new NUsers();
  /*activo add<nombre;detalle;fechaAdquiscion;fechaMantenimiento;estado>
    activo add<silla;roble;2019-05-09;2020-01-06;reservado>
    activo delete<>
    activo modify<silla;palo maria;2019-05-09;2020-01-06;reservado>
    activo list<>
    activo ver<1>
   */ 
 /*atencionClinica add<identificadorDetalleServicio;motivo;hora>
    atencionClinica add<1;4;fiebre;10:00>
    atencionClinica delete<1>
    atencionClinica modify<1;4;rabia;10:00>
    atencionClinica list<>
    atencionClinica ver<1>
   */  
 
     /*user add&lt;identificador;nombre;email;contraseña;estilo&gt;
    user add&lt;1,Marina,marinamejj123@gmail.com,12345678,normal&gt;
    user list&lt;&gt;
    user ver&lt;2&gt;
   */ 
     
         //persona add&lt;identificador;nombre;apellidopaterno y materno;sexo;fecha de nacimiento ;celunar;direccion&lt;
    //persona add&lt;5;daniel;taquichiri soraide;Hombre;21-08-12;78654123;ichilo santa fe&lt;
    
    //persona add&lt;identificador;nombre;apellidopaterno y materno;sexo;fecha de nacimiento ;celunar;direccion&lt;
    
    //persona delete&lt;identificador&lt;
    //persona delete&lt;5&lt;
    //persona list&lt;&lt;
    //persona ver&lt;5&lt;
/**
 *     
 * paciente add&lt;identificador;;nombre;especie;raza;sexo;color;fecha nacimoento;propietario&lt;
    paciente add&lt;4;kaiser;canino;mestizo;macho;macho;megro;2018-09-14;1&lt;
    paciente delete&lt;1&lt;
    paciente list&lt;&lt;
    paciente ver&lt;1&gt;
    * 

 */
    /*contrato add&lt;identificador;detalle;fechaIncio;fechaFin;identificadorPersonal;usuario;estado&gt;
    contrato add&lt;1;contrato fijo;2019-06-8;2020-08-12;4;Marina; pendiente&gt;
    contrato delete&lt;1&gt;
    contrato modify&lt;1;contrato Indefinido;2019-06-8;2020-08-12;4;Marina; pendiente&gt;
    contrato list&lt;&gt;
    contrato ver&lt;1&gt;
user add<8;Fernando;grupo11sa@tecnoweb.org.bo;4651658;normal>
*/  

        List<String> data = new ArrayList<>();
        data.add("8");
        data.add("Fernando");
        data.add("grupo11sa@tecnoweb.org.bo");
        data.add("4651658");
        data.add("normal");
        
        negocio.insertar(data,email);
        
        List<String[]> lista = negocio.listar(email);
        for (String[] dato : lista) {
            System.out.println(Arrays.toString(dato));
        }
        System.out.println("---------------------");

    }

}
