package com.tcscontrol.control_backend.enuns;

public enum TypeContacts {
    TELEFONE("Telefone"),  
    CELULAR("Celular"), 
    EMAIL("E-mail"),
    WHATSAPP("WhatsApp"),
    INSTAGRAN("Instagran");

    private String value;

    private TypeContacts(String value){
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
