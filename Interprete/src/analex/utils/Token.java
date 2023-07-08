/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analex.utils;

/**
 * @author HP
 */
public class Token {

    private int name;
    private int atribute;

    ///constante numerica para manejar analex
    public static final int cu = 0;
    public static final int action = 1;
    public static final int params = 2;
    public static final int end = 3;
    public static final int error = 4;

//CU valores entre el 100-199
    // id de casos de uso 
    public static final int usuario = 100;
    public static final int paciente = 101;
    public static final int persona = 102;
    public static final int contrato = 103;
    public static final int categoria = 104;
    public static final int producto = 105;
    public static final int tipo_servicio = 106;
    public static final int servicio = 107;
    public static final int detalle_servicio = 108;
    public static final int atencion = 109;
    public static final int detalle_atencion = 110;
    public static final int pago = 111;
    public static final int receta = 112;
    public static final int detalle_receta = 113;
    public static final int ayuda = 114;
    
    
    
    ///valores de accion del caso de uso 200-299
    public static final int add = 200;
    public static final int delete = 201;
    public static final int modify = 202;
    public static final int get = 203;
    public static final int verify = 204;
    public static final int cancel = 205;
    public static final int report = 206;

    //identificadores de errores
    public static final int error_Command = 300;
    public static final int error_Character = 301;

    
    //constante linteral
    public static final String lexeme_CU = "caso de uso";
    public static final String lexeme_Action = "action";
    public static final String lexeme_Params = "params";
    public static final String lexeme_End = "end";
    public static final String lexeme_Error = "error";

    
    
    
    
    public static final String lexeme_User = "user";
    public static final String lexeme_Persona = "persona";
    public static final String lexeme_Paciente = "paciente";

    public static final String lexeme_contrato = "contrato" ;
    public static final String lexeme_categoria = "categoria" ;
    public static final String lexeme_producto = "producto" ;
    public static final String lexeme_tipo_servicio = "tipo_servicio" ;
    public static final String lexeme_servicio = "servicio" ;
    public static final String lexeme_detalle_servicio = "detalle_servicio" ;
    public static final String lexeme_atencion = "atencion" ;
    public static final String lexeme_detalle_atencion = "detalle_atencion" ;
    public static final String lexeme_pago = "pago" ;
    public static final String lexeme_receta = "receta" ;
    public static final String lexeme_detalle_receta = "detalle_receta" ;
    public static final String lexeme_ayuda = "ayuda" ;

    
    
    
    
    public static final String lexeme_Add = "add";
    public static final String lexeme_Delete = "delete";
    public static final String lexeme_Modify = "medify";
    public static final String lexeme_Get = "get";
    public static final String lexeme_Verify = "verify";
    public static final String lexeme_Cancel = "cancel";
    public static final String lexeme_Report = "report";
    public static final String lexeme_Agregar = "agregar";

    public static final String lexeme_Error_Command = "UNKNOWN COMMAND";
    public static final String lexeme_Error_Character = "UNKNOWN CHARACTER";

    public Token(String token) {
        int  id =  findByLenxeme(token);
        if (id != -1) {
            if (100 <= id && id < 200) {
                this.name = cu;
                this.atribute = id;
            } else if (200 <= id && id < 300) {
                this.name = action;
                this.atribute = id;
            }
        } else {
            this.name = error;
            this.atribute = error_Command;
            System.err.println("Class Token.constructor dice : }n"
                    + "El lexema enviado al contruir no es reconocido como un token");
        }
    }

    public Token(int name) {
        this.name = name;
    }

    public Token(int name, int atribute) {
        this.name = name;
        this.atribute = atribute;
    }

    public Token() {
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAtribute() {
        return atribute;
    }

    public void setAtribute(int atribute) {
        this.atribute = atribute;
    }

    public String toString() {
        if (0 <= name && name <= 1) {
            return "( " + getStringToken(name) + "," + getStringToken(atribute) + ")";
        } else if (name == 2) {
            return "( " + getStringToken(name) + "," + atribute + ")";
        } else if (name == 3) {
            return "( " + getStringToken(name) + "," + "____________)";
        } else if (name == 4) {
            return "( " + getStringToken(name) + "," + getStringToken(atribute) + ")";
        }
        return "(TOKEN, DESCONOCIDO)";
    }
    

    public String getStringToken(int token){
        switch (token) {
            case cu:
                return lexeme_CU;
            case action:
                return lexeme_Action;
            case params:
                return lexeme_Params;
            case end:
                return lexeme_End;
            case error:
                return lexeme_Error;


                    ///CU
            case usuario:
                return lexeme_User;
            case persona:
                return lexeme_Persona;
            case paciente:
                return lexeme_Paciente;
            case contrato:
                return lexeme_contrato;
            case categoria:
                return lexeme_categoria;
            case producto:
                return lexeme_producto;
            case tipo_servicio:
                return lexeme_tipo_servicio;
            case servicio:
                return lexeme_servicio;
            case detalle_servicio:
                return lexeme_detalle_servicio;
            case atencion:
                return lexeme_atencion;
            case detalle_atencion:
                return lexeme_detalle_atencion;
            case pago:
                return lexeme_pago;
            case receta:
                return lexeme_receta;
            case detalle_receta:
                return lexeme_detalle_receta;
            case ayuda:
                return lexeme_ayuda;





            case add:
                return lexeme_Add;
            case delete:
                return lexeme_Delete;
            case modify:
                return lexeme_Modify;
            case get:
                return lexeme_Get;
            case verify:
                return lexeme_Verify;
            case cancel:
                return lexeme_Cancel;
            case report:
                return lexeme_Report;

                
                

            case error_Command:
                return lexeme_Error_Command;
            case error_Character:
                return lexeme_Error_Character;

            default:
                return "N: "+token;
        }
    }
    
    
    public int findByLenxeme(String lexeme){
        switch (lexeme) {
            case lexeme_CU:
                return cu;
            case lexeme_Action:
                return  action;
            case lexeme_Params:
                return  params;
            case lexeme_End:
                return  end;
            case lexeme_Error:
                return error;


                ///CU
            case lexeme_User:
                return  usuario;
            case lexeme_Persona:
                return persona;
            case lexeme_Paciente:
                return paciente;
            case lexeme_contrato:
                return contrato;
            case lexeme_categoria:
                return categoria;
            case lexeme_producto:
                return producto;
            case lexeme_tipo_servicio:
                return tipo_servicio;
            case lexeme_servicio:
                return servicio;
            case lexeme_detalle_servicio:
                return detalle_servicio;
            case lexeme_atencion:
                return atencion;
            case lexeme_detalle_atencion:
                return detalle_atencion;
            case lexeme_pago:
                return pago;
            case lexeme_receta:
                return receta;
            case lexeme_detalle_receta:
                return detalle_receta;
            case lexeme_ayuda:
                return ayuda;






            case lexeme_Add:
                return  add;
            case lexeme_Delete:
                return  delete;
            case lexeme_Modify:
                return  modify;
            case lexeme_Get:
                return  get;
            case lexeme_Verify:
                return  verify;
            case lexeme_Cancel:
                return  cancel;
            case lexeme_Report:
                return  report;



            case lexeme_Error_Command:
                return error_Command;
            case lexeme_Error_Character:
                return error_Character;

            default:
                return -1;
        }
        
    }
}
