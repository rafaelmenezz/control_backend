package com.tcscontrol.control_backend.utilitarios;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilData {

    public static final String FORMATO_DDMMAA = "dd/MM/yyyy";
    public static final String FORMATO_DATAHORA_SEM_CARACTERE = "ddMMyyyyHHmm";

    public static String toString(Date data, String formato) {
        if (data == null) {
            return null;
        }
        Date dt = data;
        SimpleDateFormat f = new SimpleDateFormat(formato);
        return f.format(dt);
    }

    public static Date toDate(String data, String formato) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat f = new SimpleDateFormat(formato);
            Date dt = f.parse(data);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
