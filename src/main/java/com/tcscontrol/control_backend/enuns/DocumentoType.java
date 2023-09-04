package com.tcscontrol.control_backend.enuns;

public enum DocumentoType {
    CPF("CPF"), CNPJ("CNPJ");

    private String value;

    private DocumentoType(String value){
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
