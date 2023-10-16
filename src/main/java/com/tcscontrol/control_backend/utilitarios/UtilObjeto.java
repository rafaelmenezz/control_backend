package com.tcscontrol.control_backend.utilitarios;

public class UtilObjeto {
    
    public static boolean isEmpty(Object obj){
        return obj == null;
    }

    public static boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }

    public static Boolean isNumero(Object objeto) {
        try {
            UtilCast.toLong(objeto.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
