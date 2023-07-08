/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interprete;

import Negocio.INegocio;
import Negocio.NPersona;
import analex.Interpreter;
import analex.interfaces.ItokenEvenListener;
import analex.utils.Token;
import events.TokenEvent;

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
        String comando="PACIENTE_ADD<Firulay,canina,salchicha,macho,megro,12-05-2020,1,null>";
        String correo="jakeli1997.jcs@gmail.com";
        INegocio bi= new NPersona();
        Interpreter interprete = new Interpreter(comando,correo);
        
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
                if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void paciente(TokenEvent event) {
                System.out.println("CU: Paciente");
                System.out.println(event);
                 if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void contrato(TokenEvent event) {
              System.out.println("CU: Contrato");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void categoria(TokenEvent event) {
                 System.out.println("CU: Categoria");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void producto(TokenEvent event) {
                  System.out.println("CU: Producto");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void tipoServicio(TokenEvent event) {
                System.out.println("CU: tipoServicio");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void servicio(TokenEvent event) {
                  System.out.println("CU: Servicio");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void receta(TokenEvent event) {
                    System.out.println("CU: Receta");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void pago(TokenEvent event) {
                    System.out.println("CU: Pago");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void detalleServicio(TokenEvent event) {
                   System.out.println("CU: detalleServicio");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void atencion(TokenEvent event) {
                     System.out.println("CU: Atencion");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void detalleAtencion(TokenEvent event) {
                        System.out.println("CU: detalleAtencion");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void error(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void detallereceta(TokenEvent event) {
           System.out.println("CU: detalleReceta");
                System.out.println(event);
                  if (event.getAction()==Token.add) {
                    bi.insertar(event.getParams(), event.getSender());
                } else if(event.getAction()==Token.modify){
                    
                }else if (event.getAction()==Token.delete){
                    
                }else {
                    System.out.println("Accion invalida en el caso de uso ");
                }
            }

            @Override
            public void ayuda(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

        });
    
        
        Thread thread = new Thread(interprete);
        thread.setName("Interprete Thread");
        thread.start();

    }
    
}
