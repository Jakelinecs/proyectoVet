package sistemacentralemail;
// importar modelos 

import Data.*;
import static Data.DPersona.headers;
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

    public NUsers bUsuario = new NUsers();
    public NPaciente bPaciente = new NPaciente();
    public NContrato bContrato = new NContrato();
    public NCategoria bCategoria = new NCategoria();
    public NProducto bProducto = new NProducto();
    public NTipoServicio bTipo_servicio = new NTipoServicio();
    public NServicio bServicio = new NServicio();
    public NDetalleServicio bDetalle_servicio = new NDetalleServicio();
    public NAtencionClinica bAtencion = new NAtencionClinica();
    public NDetalleAtencion bDetalle_atencion = new NDetalleAtencion();
    public NPago bPago = new NPago();
    public NReceta bReceta = new NReceta();
    public NDetalleReceta bDetalle_receta = new NDetalleReceta();
    //public NAyuda NAyuda = new NAyuda();

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
                Email emailObject = null;
                @Override
                public void user(TokenEvent event) {
                    System.out.println("CU: Users");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bUsuario.insertar(event.getParams(), event.getSender());
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bUsuario.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Personas", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bUsuario.ver(event.getParams(), event.getSender());
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
                public void persona(TokenEvent event) {
                    System.out.println("CU: Persona");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bPersona.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Persona Registrada Corectamente");
                            break;
                        case Token.modify:
                            bPersona.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Persona Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bPersona.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Persona Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            ArrayList<String[]> lista = (ArrayList<String[]>) bPersona.listar(event.getSender());
                            
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Personas", bPersona.headers() , lista);
                            break;
                        case Token.ver:
                            String[] x = bPersona.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Personas", bPersona.headers() , x);
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
                    switch (event.getAction()) {
                        case Token.add:
                            bPaciente.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Paciente Registrada Corectamente");
                            break;
                        case Token.modify:
                            bPaciente.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Paciente Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bPaciente.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Paciente Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bPaciente.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Pacientes", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bPaciente.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Paciente", bPersona.headers() , x);
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
                public void contrato(TokenEvent event) {
                    System.out.println("CU: Contrato");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bContrato.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Contrato Registrada Corectamente");
                            break;
                        case Token.modify:
                            bContrato.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Contrato Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bContrato.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Contrato Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bContrato.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Contratos", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bContrato.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Contrato", bPersona.headers() , x);
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
                public void categoria(TokenEvent event) {
                    System.out.println("CU: Categoria");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bCategoria.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Categoria Registrada Corectamente");
                            break;
                        case Token.modify:
                            bCategoria.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Categoria Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bCategoria.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Categoria Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bCategoria.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Categorias ", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bCategoria.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Categoria ", bPersona.headers() , x);
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
                public void producto(TokenEvent event) {
                    System.out.println("CU: producto");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bProducto.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Producto Registrada Corectamente");
                            break;
                        case Token.modify:
                            bProducto.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Producto Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bProducto.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Producto Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bProducto.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Productos", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bProducto.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Producto", bPersona.headers() , x);
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
                public void tipoServicio(TokenEvent event) {
                    System.out.println("CU: tipoServicio");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bTipo_servicio.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Tipo de Servicio Registrada Corectamente");
                            break;
                        case Token.modify:
                            bTipo_servicio.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Tipo de Servicio Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bTipo_servicio.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Tipo de Servicio Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bTipo_servicio.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Tipo de Servicios", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bTipo_servicio.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Tipo de Servicio", bPersona.headers() , x);
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
                public void servicio(TokenEvent event) {
                    System.out.println("CU: servicio");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bServicio.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Servicio Registrado Corectamente");
                            break;
                        case Token.modify:
                            bServicio.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Servicio Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bServicio.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Servicio Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bServicio.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Servicios", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bServicio.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Servicio", bPersona.headers() , x);
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
                public void receta(TokenEvent event) {
                    System.out.println("CU: receta");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bReceta.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Receta Registrada Corectamente");
                            break;
                        case Token.modify:
                            bReceta.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Receta Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bReceta.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Receta Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bReceta.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Recetas", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bReceta.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Receta", bPersona.headers() , x);
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
                public void pago(TokenEvent event) {
                    System.out.println("CU: pago");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bPago.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Pago Registrada Corectamente");
                            break;
                        case Token.modify:
                            bPago.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Pago Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bPago.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Pago Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bPago.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Pagos", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bPago.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Pago", bPersona.headers() , x);
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
                public void detalleServicio(TokenEvent event) {
                    System.out.println("CU: detalleServicio");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bDetalle_servicio.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de Servicio Registrado Corectamente");
                            break;
                        case Token.modify:
                            bDetalle_servicio.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de Servicio Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bDetalle_servicio.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de Servicio Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bDetalle_servicio.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Detalle de Servicios", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bDetalle_servicio.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Servicio", bPersona.headers() , x);
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
                public void atencion(TokenEvent event) {
                    System.out.println("CU: atencion");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bAtencion.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Atencion Clinica Registrada Corectamente");
                            break;
                        case Token.modify:
                            bAtencion.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Atencion Clinica Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bAtencion.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Atencion Clinica Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bAtencion.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Atencion Clinica", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bAtencion.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Atencion Clinica", bPersona.headers() , x);
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
                public void detalleAtencion(TokenEvent event) {
                    System.out.println("CU: detalleAtencion");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bDetalle_atencion.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de la atencion Clinica Registrada Corectamente");
                            break;
                        case Token.modify:
                            bDetalle_atencion.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de la atencion Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bDetalle_atencion.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de la atencion Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bDetalle_atencion.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Detalle de la atencions", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bDetalle_atencion.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de la atencion", bPersona.headers() , x);
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
                public void error(TokenEvent event) {
                    System.out.println("CU: error");
                    System.out.println(event);
                }

                @Override
                public void detallereceta(TokenEvent event) {
                    System.out.println("CU: detallereceta");
                    System.out.println(event);
                    switch (event.getAction()) {
                        case Token.add:
                            bDetalle_receta.insertar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de Receta Registrada Corectamente");
                            break;
                        case Token.modify:
                            bDetalle_receta.editar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de Receta Actualizada Corectamente");
                            break;
                        case Token.delete:
                            bDetalle_receta.eliminar(event.getParams(), event.getSender());
                            simpleNotifySuccess(event.getSender(), "Detalle de Receta Eliminada Corectamente");
                            break;
                        case Token.list:
                            System.out.println("Listar");
                            List<String[]> lista = bDetalle_receta.listar(event.getSender());
                            for (String[] dato : lista) {
                                System.out.println(Arrays.toString(dato));
                            }
                            tableNotifySuccess(event.getSender(), "Lista de Detalle de Receta", bPersona.headers() , (ArrayList<String[]>) lista);
                            break;
                        case Token.ver:
                            String[] x = bDetalle_receta.ver(event.getParams(), event.getSender());
                            System.out.println("Ver");
                            System.out.println(Arrays.toString(x));
                            simpleTableNotifySuccess(event.getSender(), "Ver Detalle de Detalle de Receta", bPersona.headers() , x);
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
        Email emailObject = new Email(email, "grupo09sa@tecnoweb.org.bo", Email.SUBJECT+"\r\nContent-Type: text/html\r\n",
                HtmlBuilder.generateText(new String[]{
            "Peticion realizada correctamente",
            message
        }));
        sendEmail(emailObject);
    }

    private void simpleNotify(String email, String title, String topic, String message) {
        Email emailObject = new Email(email, "grupo09sa@tecnoweb.org.bo",Email.SUBJECT+"\r\nContent-Type: text/html\r\n",
                HtmlBuilder.generateText(new String[]{
            title, topic, message
        }));
        sendEmail(emailObject);
    }

    private void tableNotifySuccess(String email, String title, String[] headers, ArrayList<String[]> data) {
        Email emailObject = new Email(email, "grupo09sa@tecnoweb.org.bo", Email.SUBJECT+"\r\nContent-Type: text/html\r\n",
                HtmlBuilder.generateTable(title, headers, data));
        sendEmail(emailObject);
    }

    private void simpleTableNotifySuccess(String email, String title, String[] headers, String[] data) {
        Email emailObject = new Email(email, "grupo09sa@tecnoweb.org.bo", Email.SUBJECT+"\r\nContent-Type: text/html\r\n",
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
        EnvioSMS sendEmail = new EnvioSMS(email);
        //EnvioSMS sendEmail= new EnvioSMS(email);

    }

}
