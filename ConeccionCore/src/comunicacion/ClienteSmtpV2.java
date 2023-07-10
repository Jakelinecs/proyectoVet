/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author la_be
 */
public class ClienteSmtpV2 {
    private static int UNILINE = 0;
    private static int BILINE = 1;
    private static int MULTILINE = 2;
    private static String Servidor = "mail.tecnoweb.org.bo";
    private static String Emisor="grupo03sa@mail.tecnoweb.org.bo";
    private static int Puerto = 25;
    private boolean bandera = true;

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
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
    
    private String ejecutarComando(BufferedReader entrada, DataOutputStream salida, String comando, int tipoRespuesta) {
        try {
            System.out.print("C : " + comando);
            salida.writeBytes(comando);
            String respuesta;
            if (tipoRespuesta == UNILINE) {
                respuesta = entrada.readLine();
            } else if (tipoRespuesta == BILINE) {
                respuesta = entrada.readLine();
                respuesta += "\n" + entrada.readLine();
            } else {
                respuesta = getMultiline(entrada);
            }
            System.out.println("S : " + respuesta + "\n");
            return respuesta;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean enviarCorreo(Email mensaje) {

        try {
            System.out.println(" S : Realizando el envio de correo");
            Socket socket = new Socket(Servidor, Puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            // Escribimos datos en el canal de salida establecido con el puerto del protocolo SMTP del servidor
            if (socket != null && entrada != null && salida != null) {
                String comando = "";
                String respuesta = "";
            //    System.out.println("S : " + entrada.readLine());

                comando = "HELO " + Servidor + " \r\n";
                respuesta = ejecutarComando(entrada, salida, comando, MULTILINE);

                comando = "MAIL FROM : " + Emisor + " \r\n";
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);
                if (respuesta.indexOf("250") == -1) {
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }

                comando = "RCPT TO : " + mensaje.getTo()+ " \r\n";
                System.out.println(comando);
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);
                if (respuesta.indexOf("250") == -1) {
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }

                comando = "DATA\n";
                respuesta = ejecutarComando(entrada, salida, comando, MULTILINE);

                    comando = "Content-Type: text/html;\nSubject:" + mensaje.getSubject() + "\r\n" + mensaje.getMessage()+ "\n" + ".\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);

                System.out.println("g:  " + respuesta);
                comando = "QUIT\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);
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
        } catch (Exception e) {

        }
        return true;
    }
}
