package analex.utils;

import java.awt.Color;

/**
 *
 * @author HP
 */
public class Utils {

    public static String fieldLeft(String s, int ancho) {
        return s + espacios(ancho - s.length());
    }

    public static String fieldRight(String s, int ancho) {
        return espacios(ancho - s.length()) + s;
    }

    public static String fieldCenter(String s, int ancho) {
        if (s.length() > ancho) {
            return s.substring(0, ancho - 1);
        }

        int padding = (ancho - s.length()) / 2;
        s = espacios(padding) + s;

        return s + espacios(ancho - s.length());
    }

    private static String espacios(int n) {
        final char blank = 32;
        return runLength(blank, n);
    }


    public static String runLength(char c, int n) {
        String s = "";
        for (int i = 1; i <= n; i++) {
            s += c;
        }
        return s;
    }

}
