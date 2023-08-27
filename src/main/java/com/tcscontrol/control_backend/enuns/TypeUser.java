package com.tcscontrol.control_backend.enuns;

public enum TypeUser {
    ADMIN("Admin"),
    GESTOR("Gestor"),
    PEAO("Peão");

    private String value;

    private TypeUser(String value){
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
