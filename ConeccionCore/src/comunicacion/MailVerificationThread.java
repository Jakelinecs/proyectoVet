/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;
    
import interfaces.IEmailEventListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import Util.Command;
import Util.Email;
import Util.Extractor;

/**
 *
 * @author HP
 */
public class MailVerificationThread implements Runnable {

    private final static  int PORT_POP= 110;
    private final static  String HOST= "mail.tecnoweb.org.bo";
    private final static  String user= "grupo09sa";
    private final static  String password= "grup009grup009";


    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    private IEmailEventListener emailEventListener;

    public MailVerificationThread() {
        socket = null;
        input = null;
        output = null;
    }

    public IEmailEventListener getEmailEventListener() {
        return emailEventListener;
    }

    public void setEmailEventListener(IEmailEventListener emailEventListener) {
        this.emailEventListener = emailEventListener;
    }

    private void authUser(String email, String password) throws IOException, AuthenticationException {
        if (socket != null && input != null && output != null) {
            input.readLine();
            output.writeBytes(Command.user(email));
            input.readLine();
            output.writeBytes(Command.pass(password));

            String message = input.readLine();
            if (message.contains("-ERR")) {

                throw new AuthenticationException();
            }
        }
    }

    private int getEmailCount() throws IOException {
        output.writeBytes(Command.stat());
        String line = input.readLine();
        String[] data = line.split(" ");
        return Integer.parseInt(data[1]);
    }

    private List<Email> getEmails(int count) throws IOException {
        List<Email> emails = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            output.writeBytes(Command.retr(i));
            String text = readMultiline();
            
            emails.add(Extractor.getEmail(text));
        }

        return emails;
    }

    private void deleteEmails(int email) throws IOException {
        for (int i = 1; i <= email; i++) {
            output.writeBytes(Command.dele(i));
        }
    }

    private String readMultiline() throws IOException {
        String lines = "";
        while (true) {

            String line = input.readLine();
            if (line == null) {
                throw new IOException("Server no responde(ocurrio un error al abrir el correo)");
            }
            if (line.equals(".")) {
                break;
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }

    @Override
  public void run() {

        while (true) {
            List<Email> emails = null;
            try {
                socket= new Socket(HOST,PORT_POP);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new DataOutputStream(socket.getOutputStream());
                
                authUser(user, password);
                
                System.out.println("*******************conexion establecida**********************");
                
                // output.writeBytes(Command.stat());
                //int count = getEmailCount(input.readLine());
                int count = getEmailCount();
                if (count>0) {
                    emails=getEmails(count);
                    System.out.println(emails);
                    deleteEmails(count);
                }
                
                output.writeBytes(Command.quit());
                input.readLine();
                input.close();  
                output.close();
                socket.close();



                System.out.println("*******************conexion CERRADA**********************");

                if (count>0) {
                    emailEventListener.onRecieveEmailEvent(emails);
                }
                
                Thread.sleep(5000);
                
            } catch (IOException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AuthenticationException ex) {
                Logger.getLogger(MailVerificationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
