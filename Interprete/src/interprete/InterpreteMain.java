/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interprete;

import Negocio.*;
import Negocio.INegocio;
import Negocio.NPersona;
import analex.Interpreter;
import analex.interfaces.ItokenEvenListener;
import analex.utils.Token;
import events.TokenEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author HP
 */
public class InterpreteMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String comando = "paciente_add(Firulay,canina,salchicha,macho,megro,12-05-2020,1,null)";
        String comando = "atencion list<> ";
        String correo = "jakeli1997.jcs@gmail.com";
//        NPersona bi = new NPersona();
//        INegocio bitacora = new NBitacora();
        Interpreter interprete = new Interpreter(comando, correo);

        interprete.setListener(new ItokenEvenListener() {
            @Override
            public void user(TokenEvent event) {
                System.out.println("CU: Users");
                System.out.println(event);
            }

            @Override
            public void persona(TokenEvent event) {
                System.out.println("CU: Persona");
                System.out.println(event);
                switch (event.getAction()) {
                    case Token.add:
                  //      bi.insertar(event.getParams(), event.getSender());
                        break;
                    case Token.modify:
                   // bi.editar(event.getParams(), event.getSender());
                        break;
                    case Token.delete:
                    //    bi.eliminar(event.getParams(), event.getSender());
                        break;
                    case Token.list:
                        System.out.println("Listar");
                     //   List<String[]> lista = bi.listar(event.getSender());
//                        for (String[] dato : lista) {
//                            System.out.println(Arrays.toString(dato));
//                        }
                        break;
                    case Token.ver:
//                        String[] x = bi.ver(event.getParams(), event.getSender());
//                        System.out.println("Ver");
//                        System.out.println(Arrays.toString(x));
                        break;
                    case Token.help:
                        System.out.println("ayuda");
//                        String[] x = bi.ver(event.getParams(), event.getSender());
//                        System.out.println("Ver");
//                        System.out.println(Arrays.toString(x));
                        break;
                    default:
                        System.out.println("Acción inválida en el caso de uso \n persione : persona help \n "
                            + "para ver todas las acciones del caso de uso");
                }
//                List<String> a = new ArrayList<>();
//                a.add(comando);
//                bitacora.insertar( a, event.getSender());
            }

            @Override
            public void paciente(TokenEvent event) {
                System.out.println("CU: Paciente");
                System.out.println(event);
            }

            @Override
            public void contrato(TokenEvent event) {
                System.out.println("CU: Paciente");
                System.out.println(event);
            }

            @Override
            public void categoria(TokenEvent event) {
                System.out.println("CU: contrato");
                System.out.println(event);
            }

            @Override
            public void producto(TokenEvent event) {
                System.out.println("CU: producto");
                System.out.println(event);
            }

            @Override
            public void tipoServicio(TokenEvent event) {
                System.out.println("CU: tipoServicio");
                System.out.println(event);
            }

            @Override
            public void servicio(TokenEvent event) {
                System.out.println("CU: servicio");
                System.out.println(event);
            }

            @Override
            public void receta(TokenEvent event) {
                System.out.println("CU: receta");
                System.out.println(event);
            }

            @Override
            public void pago(TokenEvent event) {
                System.out.println("CU: pago");
                System.out.println(event);
            }

            @Override
            public void detalleServicio(TokenEvent event) {
                System.out.println("CU: detalleServicio");
                System.out.println(event);
            }

            @Override
            public void atencion(TokenEvent event) {
                System.out.println("CU: atencion");
                System.out.println(event);
            }

            @Override
            public void detalleAtencion(TokenEvent event) {
                System.out.println("CU: detalleAtencion");
                System.out.println(event);
            }

            @Override
            public void error(TokenEvent event) {
                System.out.println("CU: error");
                System.out.println("Comando: "+event.getParams(0)+"\n No reconocido Intente: \n ayuda list  \n para listar todos los comandos");
                System.out.println(event);
            }

            @Override
            public void detallereceta(TokenEvent event) {
                System.out.println("CU: detallereceta");
                System.out.println(event);
            }

            @Override
            public void ayuda(TokenEvent event) {
                System.out.println("CU: ayuda");
                System.out.println(event);
            }

        });

        Thread thread = new Thread(interprete);
        thread.setName("Interprete Thread");
        thread.start();

    }

}
