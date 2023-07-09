/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import Util.Email;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author HP
 */
public class EnvioSMS {
    
        String servidor = "mail.tecnoweb.org.bo";
        //String servidor="172.20.172.254";
        String user_emisor = "grupo09sa@tecnoweb.org.bo";
        String line;
        String comando = "";
        int puerto = 25;

    public EnvioSMS(Email email) {
        setMessaj(email.getTo(),email.getSubject(), email.getMessage());
    }
        
        
        public void setMessaj(String email,String subject, String data){
            try {
                //se establece conexion abriendo un socket especificando el servidor y puerto SMTP
                Socket socket = new Socket(servidor, puerto);

                BufferedReader entrada;
                DataOutputStream salida;
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new DataOutputStream(socket.getOutputStream());

                // Escribimos datos en el canal de salida establecido con el puerto del protocolo SMTP del servidor
                if (socket != null && entrada != null && salida != null) {

                    System.out.println("Conectado a " + servidor + " Puerto " + puerto + " succesfull!!..");
                    System.out.println("Escuchando ...........");
                    System.out.println("S: " + entrada.readLine());

                    //saludo al servidor
                    comando = "HELO " + servidor + "\r\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    System.out.println("S : " + entrada.readLine());
                    //System.out.println("S : "+getMultiline(entrada));


                    //validar coreo emisor 
                    comando = "MAIL FROM : " + user_emisor + "\r\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    System.out.println("S : " + entrada.readLine());


                    comando = "RCPT TO : " + email + "\r\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    System.out.println("S : " + entrada.readLine());


                    comando = "DATA\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    System.out.println("S : " + getMultiline(entrada));

                    comando = subject+"\r\n" + data + ".\r\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    System.out.println("S : " + entrada.readLine());

                    comando = "QUIT\r\n";
                    System.out.print("C : " + comando);
                    salida.writeBytes(comando);
                    System.out.println("S : " + entrada.readLine());
                }
                // Cerramos los flujos de salida y de entrada y el socket cliente
                salida.close();
                entrada.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.out.println(" S : No se pudo conectar con el servidor indicado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        

    }
//Permite Leer multiples lineas del Protocolo SMTP

    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.charAt(3) == ' ') {
                lines = lines + "\n" + line;
                // No more lines in the server response
                break;
            }
            // Add read line to the list of lines
            lines = lines + "\n" + line;
        }
        return lines;
    }
}
