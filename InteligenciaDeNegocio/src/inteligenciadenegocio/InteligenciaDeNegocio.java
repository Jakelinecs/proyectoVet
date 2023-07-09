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
        INegocio negocio = new NBitacora();

        List<String> data = new ArrayList<>();
        List<String[]> lista = negocio.listar(email);
        for (String[] dato : lista) {
            System.out.println(Arrays.toString(dato));
        }
        System.out.println("---------------------");
        String[] l = negocio.ver(data, email);
        System.out.println(Arrays.toString(l));

    }

}
