/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analex;

import analex.models.TokenCommand;
import analex.interfaces.ItokenEvenListener;
import analex.utils.Token;
import events.TokenEvent;
import javax.swing.plaf.basic.BasicListUI;

/**
 *
 * @author HP
 */
public class Interpreter implements Runnable{

    private ItokenEvenListener listener;
    private Analex analex;
    
    
    private String command;
    private String sender;

    public Interpreter(String command, String sender) {
        this.command = command;
        this.sender = sender;
    }

    public ItokenEvenListener getListener() {
        return listener;
    }

    public void setListener(ItokenEvenListener listener) {
        this.listener = listener;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    private void filterEvent( TokenCommand token_command) {
        TokenEvent token_event = new TokenEvent (this,sender);
        
        token_event.setAction(token_command.getAction());
        
        int count_params = token_command.countParams();
        for (int i=0 ; i<count_params ; i++){
            int pos = token_command.getParams(i);
            token_event.addParams(analex.getParam(pos));
        }
        
        switch (token_command.getName()) {
            case Token.usuario:
                listener.user(token_event);
                 break;
            case Token.persona:
                listener.persona(token_event);
                 break;
            case Token.paciente:
                listener.paciente(token_event);
                 break;
            case Token.contrato:
                listener.contrato(token_event);
                 break;
            case Token.categoria:
                listener.categoria(token_event);
                 break;
            case Token.producto:
                listener.producto(token_event);
                 break;
            case Token.tipo_servicio:
                listener.tipoServicio(token_event);
                 break;
            case Token.servicio:
                listener.servicio(token_event);
                 break;
            case Token.detalle_servicio:
                listener.detalleServicio(token_event);
                 break;
            case Token.atencion:
                listener.atencion(token_event);
                 break;
            case Token.detalle_atencion:
                listener.detalleAtencion(token_event);
                 break;
            case Token.pago:
                listener.pago(token_event);
                 break;
            case Token.receta:
                listener.receta(token_event);
                 break;
            case Token.detalle_receta:
                listener.detallereceta(token_event);
                 break;
            case Token.ayuda:
                listener.ayuda(token_event);
                 break;
            default:
                throw new AssertionError();
        }
    }   
    
    
    
    @Override
    public void run() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
