package com.tcscontrol.control_backend.enuns;

public enum TypeWarranty {
    LEGAL("Legal"),
    CONTRATUAL("Contratual"),
    ESTENDIDA("Estendida");

    private String value;

    private TypeWarranty(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
    
}
