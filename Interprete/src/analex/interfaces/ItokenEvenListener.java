/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analex.interfaces;

import events.TokenEvent;

/**
 *
 * @author HP
 */
public interface ItokenEvenListener {
 
    ///metodos de caso de uso 

    void user(TokenEvent event);
    void persona(TokenEvent event);
    void paciente(TokenEvent event);
    void contrato(TokenEvent event);
    void categoria(TokenEvent event);
    void producto(TokenEvent event);
    void tipoServicio(TokenEvent event);
    void servicio(TokenEvent event);
    void detalleServicio(TokenEvent event);
    void atencion(TokenEvent event);
    void detalleAtencion(TokenEvent event);
    void pago(TokenEvent event);
    void receta(TokenEvent event);
    void detallereceta(TokenEvent event);
    void error(TokenEvent event);
    void ayuda(TokenEvent event);
}
