package Util;

public class Extractor {
    private static String GMAIL = "d=gmail";
    private static String HOTMAIL = "d=hotmail";
    private static String YAHOO = "d=yahoo";
    private static String OUTLOOK = "d=microsoft.com";
    private static String FICCT_UAGRM = "d=uagrm-edu-bo";
    private static String TECNO_WEB = "Received: from mail.tecnoweb.org.bo";
    private static String KREATIVADO = "d=kreativabo.com";


    public static Email getEmail(String plain_text) {
//        System.out.println("-----------------------------------------------");
//        System.out.println(plain_text);
//        System.out.println("-----------------------------------------------");
        //optiene el mensaje en bruto
        // String from, String to, String subject, String message
        String from = getFrom(plain_text);
        String to = getTo(plain_text);
        String subject = getSubject(plain_text);
        String mesaje = "";
        
        
        return new Email(from, to, subject, mesaje);
    }

    private static String getFrom(String plain_text) {
        String search = "Return-Path: <";
        int index_begin = plain_text.indexOf(search) + search.length();
        int index_end = plain_text.indexOf(">");
        return plain_text.substring(index_begin, index_end);
    }

    private static String getSubject(String plain_text) {
        String search = "Subject: ";
        int i = plain_text.indexOf(search) + search.length();
        String end_string = "";
        try {
            if (plain_text.contains(GMAIL)) {
                end_string = "To:";
            } else if (plain_text.contains(HOTMAIL) || plain_text.contains(OUTLOOK)) {
                end_string = "Thread-Topic";
            } else if (plain_text.contains(YAHOO)) {
                end_string = "MIME-Version:";
            } else if (plain_text.contains(FICCT_UAGRM)) {
                end_string = "To: ";
            } else if (plain_text.contains(TECNO_WEB)) {
                end_string = "User-Agent: ";
            } else if (plain_text.contains(KREATIVADO)) {
                end_string = "To: ";
            }
            int e = plain_text.indexOf(end_string, i);
            String r = plain_text.substring(i, e).replace("\n", "");
            return r;
        } catch (Exception exception) {
            if (plain_text.contains(FICCT_UAGRM)) {
                i = plain_text.indexOf(search) + search.length();
                end_string = "Message-ID: ";
                int e = plain_text.indexOf(end_string, i);
                return plain_text.substring(i, e).replace("\n", "");
            }
            if (plain_text.contains(TECNO_WEB)) {
                i = plain_text.indexOf(search) + search.length();
                end_string = "Message-ID: ";
                int e = plain_text.indexOf(end_string, i);
                return plain_text.substring(i, e).replace("\n", "");
            }
            System.out.println("Error in getSubject: " + exception);
            return "NO SUBJECT";
        }
    }

    private static String getTo(String plain_text) {
        String to = "";
        if (plain_text.contains(GMAIL) || plain_text.contains(FICCT_UAGRM)|| plain_text.contains(TECNO_WEB)) {
            to = getToFromGmail(plain_text);
        } else if (plain_text.contains(HOTMAIL) || plain_text.contains(KREATIVADO)) {
            to = getToFromHotmail(plain_text);
        } else if (plain_text.contains(YAHOO) || plain_text.contains(OUTLOOK)) {
            to = getToFromYahooOrOutlook(plain_text);
        }
        return to;
    }

    private static String getToFromGmail(String plain_text) {
        return getToCommon(plain_text);
    }

    private static String getToFromHotmail(String plain_text) {
        String aux = getToCommon(plain_text);
        return aux.substring(1, aux.length() - 1);
    }

    private static String getToFromYahooOrOutlook(String plain_text) {
        int index = plain_text.indexOf("To: ");
        int i = plain_text.indexOf("<", index);
        int e = plain_text.indexOf(">", i);
        return plain_text.substring(i + 1, e);
    }

    private static String getToCommon(String plain_text) {
        String aux = "To: ";
        int index_begin = plain_text.indexOf(aux) + aux.length();
        int index_end = plain_text.indexOf("\n", index_begin);
        return plain_text.substring(index_begin, index_end);
    }

    private static String getMensaje(String plain_text) {
        String search = "Subject: ";
        int i = plain_text.indexOf(search) + search.length();
        String end_string = "";
        try {
            if (plain_text.contains(GMAIL)) {
                search = "charset=\"UTF-8\"";
                end_string = "--00000000";
            } else if (plain_text.contains(HOTMAIL) || plain_text.contains(OUTLOOK)) {
                search = "Content-Transfer-Encoding: quoted-printable";
                end_string = "--_";
            } else if (plain_text.contains(YAHOO)) {
                end_string = "MIME-Version:";
            } else if (plain_text.contains(FICCT_UAGRM)) {
                end_string = "From: ";
            } else if (plain_text.contains(TECNO_WEB)) {
                String subString = "Content-Transfer-Encoding:";
                i = plain_text.indexOf(subString);
                int index = plain_text.indexOf('\n', i);
                search = plain_text.substring(i, index);               
                end_string = plain_text;
            } else if (plain_text.contains(KREATIVADO)) {
                search = "Content-Type: text/plain; charset=\"UTF-8\"";
                end_string = "--00000";
            }
            i = plain_text.indexOf(search) + search.length();
            int index = plain_text.indexOf('\n', i);
            int e = plain_text.indexOf(end_string, index);
            return plain_text.substring(index, e).replace("\n", "");
        } catch (Exception exception) {
            if (plain_text.contains(FICCT_UAGRM)) {
                i = plain_text.indexOf(search) + search.length();
                end_string = "Message-ID: ";
                int e = plain_text.indexOf(end_string, i);
                return plain_text.substring(i, e);
            }
            if (plain_text.contains(TECNO_WEB)) {
                i = plain_text.indexOf(search) + search.length();
                int e = plain_text.length();
                return plain_text.substring(i, e).replace("\n", "");
            }
            System.out.println("Error in getSubject: " + exception);
            return "NO SUBJECT";
        }
    }
}
