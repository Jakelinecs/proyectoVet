package analex.utils;

/**
 *
 * @author HP
 */
public class Cinta {
    
    public static final char EOF = 0;
    
    private String cinta;
    
    private int i;
    
    public Cinta(String cinta){
        this.cinta = cinta;
        this.i = 0;
    }
    
    public void forward(){
        if(i<= cinta.length()){
            i++;
        }else{
            System.err.println("Class Cinta.forward dice: \n"
            +"No se puede avanzar mas el cabezal");
        }
    }
    
    public char cc(){
        if (i<cinta.length()) {
            return cinta.charAt(i);
        }
        return EOF;
    }
    
    public void init(){
        i=0;
    }
}
