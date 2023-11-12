package com.tcscontrol.control_backend.utilitarios;

import java.util.Collection;
import java.util.Map;

public class UtilString {
    
    public static final String EMPTY = org.apache.commons.lang.StringUtils.EMPTY;
    public static final String ZERO_INT = "0";
    public static final String ZERO_DOUBLE = "0.0";
    public static final String NULL = "null";
    public static final String VIRGULA = ", ";
    public static final String PONTO = ". ";
    public static final String ESPACO = " ";
    public static final String TRACO = "-";


    public static boolean isNuloOuBrancoOuZero(Object objeto) {
        if (objeto instanceof Object[]) {
            Object[] array = (Object[]) objeto;
            return array.length == 0;
        }
        return isZero(objeto);
    }

    public static boolean isEmpty(Object objeto) {
        return isNuloOuBranco(objeto);
    }

    public static boolean isNotEmpty(Object objeto) {
        return !isNuloOuBranco(objeto);
    }


    public static boolean isZero(Object objeto) {
        return isNuloOuBranco(objeto) || objeto.toString().trim().equals(ZERO_INT) || objeto.toString().trim().equals(ZERO_DOUBLE);
    }

    public static boolean isNuloOuBranco(Object objeto) {
        if (objeto == null) {
            return Boolean.TRUE;
        }
        if (objeto instanceof String || objeto instanceof StringBuilder || objeto instanceof StringBuffer) {
            String trim = objeto.toString().trim();
            return trim.equals(EMPTY) || trim.equals(NULL);
        }
        if (objeto instanceof Collection && ((Collection<?>) objeto).isEmpty()) {
            return Boolean.TRUE;
        }
        if (objeto instanceof Map && ((Map<?, ?>) objeto).isEmpty()) {
            return Boolean.TRUE;
        }
        if (objeto instanceof Object[]) {
            if (((Object[]) objeto).length == 0) {
                return Boolean.TRUE;
            }
            if (((Object[]) objeto).length == 1 && isNuloOuBranco(((Object[]) objeto)[0])) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }




}
