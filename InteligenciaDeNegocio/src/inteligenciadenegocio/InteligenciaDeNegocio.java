/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inteligenciadenegocio;

import Negocio.NCategoria;
import Negocio.NPaciente;
import Negocio.NPersona;
import Negocio.NProducto;
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
        
        String email="jakeli1997.jcs@gmail.com";
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
        NProducto negocio = new NProducto();

        List<String[]> lista = negocio.listar(email);
        for (String[] dato : lista) {
            System.out.println(Arrays.toString(dato));
        }
    
    
    
        
    }
    
}
