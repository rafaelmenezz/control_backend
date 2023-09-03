package com.tcscontrol.control_backend.utilitarios;

public class UtilCast {

    public static String toString(Object objeto) {
        if (objeto == null) {
            return null;
        }
        return objeto.toString();
    }

    public static String toStringBrancoSeNull(Object objeto) {
        if (objeto == null) {
            return UtilString.EMPTY;
        }
        return objeto.toString();
    }

    public static Integer toInteger(Object objeto) {
        if (UtilString.isNuloOuBranco(objeto)) {
            return null;
        }
        if (objeto instanceof Integer) {
            return (Integer) objeto;
        }
        if (objeto instanceof Number) {
            return ((Number) objeto).intValue();
        }
         return Integer.parseInt(objeto.toString().trim());
    }


    
}
