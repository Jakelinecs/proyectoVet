package analex.utils;

import java.util.ArrayList;
import jdk.jshell.execution.Util;

/**
 *
 * @author HP
 */
public class TSParams {

    private static String TITLE = "TSParams";

    private ArrayList<String> L;

    public TSParams() {
        L = new ArrayList<String>();
    }
    //inicio de lectura de parametros = 0
    public void init() {
        L.clear();
    }

    public int length() {
        return L.size();
    }

    public int Existe(String Str) {
        for (int i = 0; i <= (length() - 1); i++) {
            if (L.get(i).equals(Str)) {
                return i;
            }
        }
        return -1;
    }

    public int add(String Str) {
        int pos = Existe(Str);
        if (pos != -1) {
            return pos;
        }
        if (Str.isEmpty()) {
            return -1;
        }
        L.add(Str);
        return L.size() - 1;
    }

    public String getStr(int index) {
        if (!posValida(index)) {
            System.err.println("la Class TSParams en el metodo TSS.getStr : Posicion invalida.");
            return "";
        }
        return L.get(index);
    }

    public boolean posValida(int index) {
        return ((0 <= index) && (index <= (length() - 1)));
    }

    @Override
    public String toString() {
        if (length() == 0) {
            return "(" + TITLE + "Vacia";
        }

        final String lf = "\n";
        final String paddLeft = "  ";

        String result;
        int n = longitudFila();

        String bordeSup = paddLeft + ' ' + Utils.runLength('_', n);
        String Titulo = paddLeft + '|' + Utils.fieldCenter(TITLE, n)+'|';
        String bordeInd = paddLeft + '+' + Utils.runLength('-', n)+ '+';

        result = bordeSup + lf + Titulo + lf + bordeInd + lf;
        int fieldPos = paddLeft.length();
        for (int i = 0; i < (length() - 1); i++) {
            String posicion = Utils.fieldRight("" + i, fieldPos);
            String fila = '|'+ Utils.fieldLeft('"'+L.get(i)+'"', n) + '|';
            result += posicion + fila + lf;
        }
        return result + bordeInd + lf;
    }

    private int longitudFila() {
        int may = TITLE.length();
        for (int i = 0; i < (length() - 1); i++) {
            int lonStr = L.get(i).length();
            if (lonStr > may) {
                may = lonStr;
            }
        }

        return may + 2;
    }

}
