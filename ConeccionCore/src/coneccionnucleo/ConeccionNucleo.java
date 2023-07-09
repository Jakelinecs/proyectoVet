/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coneccionnucleo;

import Negocio.*;
import Util.Email;
import analex.*;
import comunicacion.*;
import events.TokenEvent;
import interfaces.IEmailEventListener;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author HP
 */
public class ConeccionNucleo {
       

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        //prueva de escuchador de correos
        /*
        MailVerificationThread mail= new MailVerificationThread();
        
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onRecieveEmailEvent(List<Email> emails) {
                //evento
                for (Email email : emails) {
                    System.out.println(email);
                }
                
            }
        });
        Thread thread = new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();
            */
        
        ///prueba de  envio de correo
        //Email emailObjet = new Email("jakeli1997.jcs@gmail.com", "hola", "Peticion Prueva realizada correctamente");
        Email emailObjet = new Email("grupo05sa@tecnoweb.org.bo", Email.SUBJECT, "Peticion Prueva realizada correctamente");

        SendEmailThread sendEmail= new SendEmailThread(emailObjet);
        
        Thread thread = new Thread(sendEmail);
        thread.setName("Send Mail Thread");
        thread.start();
        
    }
    /**
    public static void interprete(Email email){
        Interpreter interprete = new Interpreter(email.getSubject(), email.getFrom());
        interprete.setListener(new ItokenEvenListener() {
            INegocio bi;
            @Override
            public void user(TokenEvent event) {
                System.out.println("CU: Users");
                System.out.println(event);
            }

            @Override
            public void persona(TokenEvent event) {
                this.bi = new NPersona();
                System.out.println("CU: Persona");
                System.out.println(event);
                switch (event.getAction()) {
                    case Token.add -> bi.insertar(event.getParams(), event.getSender());
                    case Token.modify -> bi.editar(event.getParams(), event.getSender());
                    case Token.delete -> bi.eliminar(event.getParams(), event.getSender());
                    case Token.list -> {
                        System.out.println("Listar");
                        List<String[]> lista = bi.listar(event.getSender());
                        for (String[] dato : lista) {
                            System.out.println(Arrays.toString(dato));
                        }
                    }
                    case Token.ver -> {
                        String[] x = bi.ver(event.getParams(), event.getSender());
                        System.out.println("Ver");
                        System.out.println(Arrays.toString(x));
                    }
                    default -> System.out.println("Accion invalida en el caso de uso ");
                }
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
**/    
}
