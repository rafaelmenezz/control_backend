package com.tcscontrol.control_backend.utilitarios;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilData {

    public static final String FORMATO_DDMMAA = "dd/MM/yyyy";

    public static String toString(Date data, String formato){
        Date dt = data;
        SimpleDateFormat f = new SimpleDateFormat(formato);
        return f.format(dt);
    }

    public static Date toDate(String data, String formato){

        SimpleDateFormat f = new SimpleDateFormat(formato);
        try {
            Date dt = f.parse(data);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

}
