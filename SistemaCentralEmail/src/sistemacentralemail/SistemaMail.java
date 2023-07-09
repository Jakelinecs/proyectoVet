package sistemacentralemail;
// importar modelos 

import Data.DPersona;
import Negocio.*;
import Negocio.NPersona;
import Util.Email;
import analex.Interpreter;
import analex.interfaces.ItokenEvenListener;
import analex.utils.Token;
import comunicacion.MailVerificationThread;
import comunicacion.SendEmailThread;
import events.TokenEvent;
import interfaces.IEmailEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.HtmlBuilder;


/**
 *
 * @author HP
 */
public abstract class SistemaMail implements IEmailEventListener, ItokenEvenListener{
    public static final int constant_Error = -2;
    public static final int numer_format_Error = -3;
    public static final int index_Out_Of_Bount_Error = -4;
    public static final int parse_Error = -5;
    public static final int autorization_Error = -6;
    
    
    private MailVerificationThread mailVerificationThread;
    
    private NPersona bPersona;
    private INegocio bi;
    private INegocio bitacora = new NBitacora();
    
    String comando;
    public SistemaMail(){
        mailVerificationThread= new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(SistemaMail.this);
        //bUser= new BUser();
    }

    public void start(){
        Thread thread= new Thread(mailVerificationThread);
        thread.setName("mail Verification Theread");
        thread.start();
    }
    @Override
    public void onRecieveEmailEvent(List<Email> emails) {
        for (Email email : emails) {
            comando =email.getSubject();
            Interpreter interprete = new Interpreter(email.getSubject(),email.getFrom());
            interprete.setListener(SistemaMail.this);
            Thread thread = new Thread(interprete);
            thread.setName("Interpreter Thread");
            thread.start();
        }
    }

    @Override
    public void user(TokenEvent event) {
        try{
            //if (bUser.existeUser(event.getSender())){
                switch(event.getAction()){
                    case Token.add:
                        //bUser.guardar(event.getParams());
                        simpleNotifySuccess(event.getSender(),"Ususario guardado Corectamente");
                        break;
                    case Token.get:
                        //tableNotifySuccess(event.getSender(),"lsitado de usuarios",DUser.headers, (ArrayList<String[]>) bUser.listar());
                        break;
                }
            //}else{
                handleError(autorization_Error, event.getSender(),null);
            //}
        }catch(NumberFormatException ex){
                handleError(numer_format_Error, event.getSender(),null);
                handleError(constant_Error, event.getSender(),null);
        }catch(IndexOutOfBoundsException ex){
                handleError(index_Out_Of_Bount_Error, event.getSender(),null);
        }
        
    }

    public abstract void miMetodoAbstracto(String[] dato);
 
    @Override
    public void error(TokenEvent event) {
        handleError(event.getAction(),event.getSender(),event.getParams());
    }

    private void handleError(int type,String email,List<String> arg){
        Email emailObject = null;
        switch(type){
            case Token.error_Character:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Caracter desconocido",
                            "No se pudo ejecutar el comando ["+ arg.get(0) + "] debido a: ",
                            "El caracter \"" + arg.get(1) + "\"como un comando valido" 
                     }));
            break;
            case Token.error_Command:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Caracter desconocido",
                            "No se pudo ejecutar el comando ["+ arg.get(0) + "] debido a: ",
                            "No se reconoce la palabra \"" + arg.get(1) + "\"como un comando valido" 
                     }));
            break;
            case constant_Error:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Error al interactuar con la base de datos",
                            "Referencia a informacion inexistente" 
                     }));
            break;
            case numer_format_Error:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Error en el tipo de parametro",
                            "El tipo de uno de los parametros es incorrecto" 
                     }));
            break;
            case index_Out_Of_Bount_Error:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Cantidad en el tipo de parametro",
                            "El tipo de uno de los parametros es incorrecto" 
                     }));
            break;
            case parse_Error:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Error al procesar la fecha",
                            "La fecha introducida posee un formato incorecto" 
                     }));
            break;
            case autorization_Error:
                emailObject=new Email(email, Email.SUBJECT, 
                        HtmlBuilder.generateText(new String[]{
                            "Accesos denegado",
                            "Usted no posee los permisos necesario para realizar la accion solicitada" 
                     }));
            break;
        }
        sendEmail(emailObject);
    }

    private void simpleNotifySuccess(String email, String message){
        Email emailObject = new Email(email,Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
                    "Peticion realizada correctamente",
                    message
                })); 
        sendEmail(emailObject);
    }
   
    private void simpleNotify(String email, String title, String topic, String message){
        Email emailObject = new Email(email,Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
                    title, topic, message
                })); 
        sendEmail(emailObject);
    }
    
    private void tableNotifySuccess(String email, String title, String[] headers, ArrayList<String[]> data){
        Email emailObject = new Email(email,Email.SUBJECT,
                HtmlBuilder.generateTable(title, headers, data)); 
        sendEmail(emailObject);
    }

    private void simpleTableNotifySuccess(String email, String title, String[] headers, String[] data){
        Email emailObject = new Email(email,Email.SUBJECT,
                HtmlBuilder.generateTableForSimpleData(title, headers, data)); 
        sendEmail(emailObject);
    }
    
    private void sendEmail(Email email){
        SendEmailThread sendEmail = new SendEmailThread(email);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }

   
    @Override
    public void paciente(TokenEvent event) {
        try{
                switch(event.getAction()){
                    case Token.add:
                        
                         bPersona.insertar(event.getParams(),event.getSender());
                        simpleNotifySuccess(event.getSender(),"Ususario guardado Corectamente");
                        break;
                    case Token.get:
                        tableNotifySuccess(event.getSender(),"lsitado de usuarios",DPersona.headers, (ArrayList<String[]>) bPersona.listar(event.getSender()));
                        break;
                }
                handleError(autorization_Error, event.getSender(),null);
        }catch(NumberFormatException ex){
                handleError(numer_format_Error, event.getSender(),null);
                handleError(constant_Error, event.getSender(),null);
        }catch(IndexOutOfBoundsException ex){
                handleError(index_Out_Of_Bount_Error, event.getSender(),null);
        }
        
    }

    @Override
    public void contrato(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void categoria(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void producto(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void tipoServicio(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void servicio(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void detalleServicio(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void atencion(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void detalleAtencion(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void pago(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void receta(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void detallereceta(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void ayuda(TokenEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void persona(TokenEvent event) {
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
        List<String> a = new ArrayList<>();
        a.add(comando);
        bitacora.insertar( a, event.getSender());
        String[]dato = bitacora.ver( a, event.getSender());

        miMetodoAbstracto(dato);
        handleError(autorization_Error, event.getSender(),null);
    }
}
