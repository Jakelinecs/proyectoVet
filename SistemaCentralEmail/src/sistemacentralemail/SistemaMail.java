package sistemacentralemail;
// importar modelos 

import Data.DPersona;
import Negocio.*;
import Negocio.NPersona;
import Util.Email;
import analex.Interpreter;
import analex.interfaces.ItokenEvenListener;
import analex.utils.Token;
import comunicacion.EnvioSMS;
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
public abstract class SistemaMail implements IEmailEventListener {

    public static final int constant_Error = -2;
    public static final int numer_format_Error = -3;
    public static final int index_Out_Of_Bount_Error = -4;
    public static final int parse_Error = -5;
    public static final int autorization_Error = -6;

    private MailVerificationThread mailVerificationThread;

    private NPersona bPersona = new NPersona();
    private INegocio bitacora = new NBitacora();

    String comando;
    String[] dato;
    public SistemaMail() {
        mailVerificationThread = new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(SistemaMail.this);
        //bUser= new BUser();
    }

    public void start() {
        Thread thread = new Thread(mailVerificationThread);
        thread.setName("mail Verification Theread");
        thread.start();
    }

    @Override
    public void onRecieveEmailEvent(List<Email> emails) {
        for (Email email : emails) {
            comando = email.getSubject();
            Email sms = email;
            Interpreter interprete = new Interpreter(email.getSubject(), email.getFrom());

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
                            bPersona.insertar(event.getParams(), event.getSender());
                            break;
                        case Token.modify:
                            bPersona.editar(event.getParams(), event.getSender());
                            break;
                        case Token.delete:
                            bPersona.eliminar(event.getParams(), event.getSender());
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bPersona.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            break;
                        case Token.ver:
                            String[] x = bPersona.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            break;
                        default:
                            System.out.println("Acción inválida en el caso de uso");
                    }
                    List<String> a = new ArrayList<>();
                    a.add(comando);
                    bitacora.insertar(a, event.getSender());
                    dato = bitacora.ver(a, event.getSender());
                    miMetodoAbstracto(dato);
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
    }

    public abstract void miMetodoAbstracto(String[] dato);

    private void handleError(int type, String email, List<String> arg) {
        Email emailObject = null;
        switch (type) {
            case Token.error_Character:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Caracter desconocido",
                    "No se pudo ejecutar el comando [" + arg.get(0) + "] debido a: ",
                    "El caracter \"" + arg.get(1) + "\"como un comando valido"
                }));
                break;
            case Token.error_Command:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Caracter desconocido",
                    "No se pudo ejecutar el comando [" + arg.get(0) + "] debido a: ",
                    "No se reconoce la palabra \"" + arg.get(1) + "\"como un comando valido"
                }));
                break;
            case constant_Error:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error al interactuar con la base de datos",
                    "Referencia a informacion inexistente"
                }));
                break;
            case numer_format_Error:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error en el tipo de parametro",
                    "El tipo de uno de los parametros es incorrecto"
                }));
                break;
            case index_Out_Of_Bount_Error:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Cantidad en el tipo de parametro",
                    "El tipo de uno de los parametros es incorrecto"
                }));
                break;
            case parse_Error:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Error al procesar la fecha",
                    "La fecha introducida posee un formato incorecto"
                }));
                break;
            case autorization_Error:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generateText(new String[]{
                    "Accesos denegado",
                    "Usted no posee los permisos necesario para realizar la accion solicitada"
                }));
                break;
        }
        sendEmail(emailObject);
    }

    private void simpleNotifySuccess(String email, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
            "Peticion realizada correctamente",
            message
        }));
        sendEmail(emailObject);
    }

    private void simpleNotify(String email, String title, String topic, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateText(new String[]{
            title, topic, message
        }));
        sendEmail(emailObject);
    }

    private void tableNotifySuccess(String email, String title, String[] headers, ArrayList<String[]> data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTable(title, headers, data));
        sendEmail(emailObject);
    }

    private void simpleTableNotifySuccess(String email, String title, String[] headers, String[] data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTableForSimpleData(title, headers, data));
        sendEmail(emailObject);
    }

    private void sendEmail(Email email) {
        /**
         * SendEmailThread sendEmail = new SendEmailThread(email); Thread thread
         * = new Thread(sendEmail); thread.setName("Send email Thread");
         * thread.start();
         *
         */
        //EnvioSMS sendEmail= new EnvioSMS(email);

    }

}
