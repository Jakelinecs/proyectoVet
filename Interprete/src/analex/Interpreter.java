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
            case Token.usuario -> listener.user(token_event);
            case Token.persona -> listener.persona(token_event);
            case Token.paciente -> listener.paciente(token_event);
            case Token.contrato -> listener.contrato(token_event);
            case Token.categoria -> listener.categoria(token_event);
            case Token.producto -> listener.producto(token_event);
            case Token.tipo_servicio -> listener.tipoServicio(token_event);
            case Token.servicio -> listener.servicio(token_event);
            case Token.detalle_servicio -> listener.detalleServicio(token_event);
            case Token.atencion -> listener.atencion(token_event);
            case Token.detalle_atencion -> listener.detalleAtencion(token_event);
            case Token.pago -> listener.pago(token_event);
            case Token.receta -> listener.receta(token_event);
            case Token.detalle_receta -> listener.detallereceta(token_event);
            case Token.ayuda -> listener.ayuda(token_event);
        }
    }   
    
    
    private void tokenError(Token token, String error){
        TokenEvent token_event = new TokenEvent(this, sender);
        token_event.setAction(token.getAtribute());
        token_event.addParams(command);
        token_event.addParams(error);
        listener.error(token_event);
        
    }
    
    
    @Override
    public void run() {
        analex = new Analex(command);
        TokenCommand token_command = new TokenCommand();
        Token token;
        
        
        while((token = analex.preAnalisis()).getName() != Token.end && token.getName() != Token.error){
            if(token.getName() == Token.cu){
                token_command.setName(token.getAtribute());// id del CU
            } else if(token.getName() == Token.action){
                token_command.setAction(token.getAtribute());// id de la accion
            } else if(token.getName() == Token.params){
                token_command.addParams(token.getAtribute());// la posicion del parametro en el tsp
            }
            analex.next();
        }
        
        if(token.getName() == Token.end){
            filterEvent(token_command);// se analizo el comando con exito
        } else if(token.getName() == Token.error){
            tokenError(token, analex.lexeme()); // se produjo un error en el analisis
        }
        
    }
}
